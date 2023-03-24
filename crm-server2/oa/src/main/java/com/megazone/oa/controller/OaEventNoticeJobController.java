package com.megazone.oa.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.megazone.core.common.Result;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.redis.Redis;
import com.megazone.oa.entity.BO.QueryEventListBO;
import com.megazone.oa.entity.PO.OaEvent;
import com.megazone.oa.mapper.OaEventMapper;
import com.megazone.oa.mapper.OaEventNoticeMapper;
import com.megazone.oa.service.IOaEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/oaEventJob")
public class OaEventNoticeJobController {

	@Autowired
	private OaEventNoticeMapper oaEventNoticeMapper;

	@Autowired
	private AdminService adminService;

	@Autowired
	private IOaEventService oaEventService;

	@Autowired
	private OaEventMapper eventMapper;

	@Autowired
	private Redis redis;

	@PostMapping("/eventNoticeCron")
	public Result eventNoticeCron() {
		DateTime nowDate = DateUtil.date();
		long startTime = DateUtil.beginOfDay(nowDate).getTime();
		long endTime = DateUtil.endOfDay(nowDate).getTime();
		QueryEventListBO queryEventListBO = new QueryEventListBO();
		queryEventListBO.setStartTime(startTime);
		queryEventListBO.setEndTime(endTime);
		List<OaEvent> oaEventList = eventMapper.queryList(queryEventListBO);
		oaEventService.eventNotice(oaEventList);
		return Result.ok();
	}
}
