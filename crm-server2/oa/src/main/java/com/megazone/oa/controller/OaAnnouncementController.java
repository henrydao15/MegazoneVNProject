package com.megazone.oa.controller;


import com.megazone.core.common.R;
import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.oa.entity.PO.OaAnnouncement;
import com.megazone.oa.service.IOaAnnouncementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/oaAnnouncement")
@Api(tags = "oa announcement")
public class OaAnnouncementController {

	@Autowired
	private IOaAnnouncementService oaAnnouncementService;

	@PostMapping("/addAnnouncement")
	@ApiOperation("Add Announcement")
	public Result addAnnouncement(@RequestBody OaAnnouncement oaAnnouncement) {
		oaAnnouncementService.addOaAnnouncement(oaAnnouncement);
		return R.ok();
	}

	@PostMapping("/setAnnouncement")
	@ApiOperation("Edit Announcement")
	public Result setAnnouncement(@RequestBody OaAnnouncement oaAnnouncement) {
		oaAnnouncementService.setOaAnnouncement(oaAnnouncement);
		return R.ok();
	}

	@PostMapping("/delete/{announcementId}")
	@ApiOperation("Delete Announcement")
	public Result delete(@PathVariable @NotNull Integer announcementId) {
		oaAnnouncementService.delete(announcementId);
		return R.ok();
	}

	@PostMapping("/queryById/{announcementId}")
	@ApiOperation("Query Announcement")
	public Result queryById(@PathVariable @NotNull Integer announcementId) {
		return R.ok(oaAnnouncementService.queryById(announcementId));
	}

	@PostMapping("/queryList")
	@ApiOperation("Query announcement list")
	public Result<BasePage<OaAnnouncement>> queryList(PageEntity entity, Integer type) {
		BasePage<OaAnnouncement> oaAnnouncementBasePage = oaAnnouncementService.queryList(entity, type);
		return R.ok(oaAnnouncementBasePage);
	}
}
