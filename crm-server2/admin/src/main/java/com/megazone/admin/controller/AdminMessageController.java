package com.megazone.admin.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.megazone.admin.entity.BO.AdminMessageQueryBO;
import com.megazone.admin.entity.PO.AdminMessage;
import com.megazone.admin.entity.VO.AdminMessageVO;
import com.megazone.admin.service.IAdminMessageService;
import com.megazone.core.common.ApiExplain;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.Result;
import com.megazone.core.common.cache.AdminCacheKey;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.admin.entity.AdminMessageBO;
import com.megazone.core.redis.Redis;
import com.megazone.core.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/adminMessage")
@Api(tags = "System messages")
@Slf4j
public class AdminMessageController {

	@Autowired
	private IAdminMessageService messageService;

	@Autowired
	private Redis redis;

	@PostMapping("/save")
	public Result<AdminMessage> save(@RequestBody com.megazone.core.feign.admin.entity.AdminMessage adminMessage) {
		AdminMessage adminMessage1 = BeanUtil.copyProperties(adminMessage, AdminMessage.class);
		if (adminMessage.getCreateTime() != null) {
			log.info("saveMessage:{}", adminMessage.getCreateTime());
			adminMessage1.setCreateTime(DateUtil.parseDateTime(adminMessage.getCreateTime()));
		}
		messageService.save(adminMessage1);
		return Result.ok(adminMessage1);
	}

	@PostMapping("/update")
	public Result<AdminMessage> update(@RequestBody com.megazone.core.feign.admin.entity.AdminMessage adminMessage) {
		AdminMessage adminMessage1 = BeanUtil.copyProperties(adminMessage, AdminMessage.class);
		if (adminMessage.getCreateTime() != null) {
			adminMessage1.setCreateTime(DateUtil.parseDateTime(adminMessage.getCreateTime()));
		}
		messageService.updateById(adminMessage1);
		return Result.ok(adminMessage1);
	}

	@PostMapping("/saveOrUpdateMessage")
	public Result<Long> saveOrUpdateMessage(@RequestBody com.megazone.core.feign.admin.entity.AdminMessage message) {
		Long messageId = messageService.saveOrUpdateMessage(message);
		return Result.ok(messageId);
	}

	@PostMapping("/queryList")
	@ApiOperation("Query message list")
	public Result<BasePage<AdminMessage>> queryList(@RequestBody AdminMessageQueryBO adminMessageBO) {
		BasePage<AdminMessage> adminMessageBasePage = messageService.queryList(adminMessageBO);
		return Result.ok(adminMessageBasePage);
	}

	@PostMapping("/readMessage")
	@ApiOperation("Single marked as read")
	public Result readMessage(@RequestParam("messageId") Long messageId) {
		AdminMessage byId = messageService.getById(messageId);
		if (byId != null) {
			byId.setIsRead(1);
			messageService.updateById(byId);
		}
		return Result.ok();
	}

	@PostMapping("/readAllMessage")
	@ApiOperation("Mark all as read")
	public Result readAllMessage(Integer label) {
		LambdaUpdateChainWrapper<AdminMessage> wrapper = messageService.lambdaUpdate();
		wrapper.set(AdminMessage::getIsRead, 1);
		wrapper.eq(AdminMessage::getRecipientUser, UserUtil.getUserId());
		if (label != null) {
			wrapper.eq(AdminMessage::getLabel, label);
		}
		wrapper.update();
		return Result.ok();
	}

	@PostMapping("/clear")
	@ApiOperation("Delete read messages")
	public Result clear(Integer label) {
		LambdaUpdateChainWrapper<AdminMessage> wrapper = messageService.lambdaUpdate();
		wrapper.eq(AdminMessage::getIsRead, 1);
		wrapper.eq(AdminMessage::getRecipientUser, UserUtil.getUserId());
		if (label != null) {
			wrapper.eq(AdminMessage::getLabel, label);
		}
		wrapper.remove();
		return Result.ok();
	}

	@PostMapping("/getById/{messageId}")
	public Result<AdminMessage> getById(@PathVariable Long messageId) {
		AdminMessage adminMessage = messageService.getById(messageId);
		return Result.ok(adminMessage);
	}

	@PostMapping("/queryUnreadCount")
	@ApiOperation("Query unread messages")
	public Result<AdminMessageVO> queryUnreadCount() {
		AdminMessageVO messageVO = messageService.queryUnreadCount();
		return Result.ok(messageVO);
	}

	@PostMapping("/queryImportNum")
	@ApiOperation("Query import quantity")
	public Result<Integer> queryImportNum(Long messageId) {
		boolean exists = redis.exists(AdminCacheKey.UPLOAD_EXCEL_MESSAGE_PREFIX + messageId.toString());
		Integer num = null;
		if (exists) {
			num = redis.get(AdminCacheKey.UPLOAD_EXCEL_MESSAGE_PREFIX + messageId.toString());
		}
		return Result.ok(num);
	}

	@PostMapping("/queryImportInfo")
	@ApiOperation("Query import information")
	public Result<JsonNode> queryImportInfo(@RequestParam("messageId") Long messageId) {
		AdminMessage adminMessage = messageService.getById(messageId);
		if (adminMessage != null && adminMessage.getContent() != null) {
			String[] content = adminMessage.getContent().split(",");
			JSONObject r = new JSONObject().fluentPut("totalSize", adminMessage.getTitle()).fluentPut("errSize", content[0]);
			r.put("updateSize", content.length > 1 ? content[1] : 0);
			return Result.ok(r.node);
		} else {
			return Result.ok(new JSONObject().fluentPut("totalSize", 0).fluentPut("errSize", 0).fluentPut("updateSize", 0).node);
		}
	}

	@PostMapping("/downImportError")
	@ApiOperation("Download error template")
	public void downImportError(@RequestParam("messageId") Long messageId, HttpServletResponse response) {
		String str = redis.get(AdminCacheKey.UPLOAD_EXCEL_MESSAGE_PREFIX + "file:" + messageId.toString());
		final boolean exist = FileUtil.exist(str);
		if (exist) {
			ServletUtil.write(response, FileUtil.file(str));
		}
	}

	@PostMapping("/sendMessage")
	@ApiExplain("Send message")
	public Result sendMessage(@RequestBody AdminMessageBO adminMessageBO) {
		messageService.addMessage(adminMessageBO);
		return Result.ok();
	}

	@PostMapping("/deleteEventMessage")
	@ApiExplain("Delete schedule message")
	public Result deleteEventMessage(@RequestParam("eventId") Integer eventId) {
		messageService.deleteEventMessage(eventId);
		return Result.ok();
	}

	@PostMapping("/deleteById/{messageId}")
	@ApiOperation("Delete Notification")
	public Result deleteById(@PathVariable("messageId") Integer messageId) {
		messageService.deleteById(messageId);
		return Result.ok();
	}

	@PostMapping("/deleteByLabel")
	public Result deleteByLabel(@RequestParam("label") Integer label) {
		messageService.deleteByLabel(label);
		return Result.ok();
	}
}
