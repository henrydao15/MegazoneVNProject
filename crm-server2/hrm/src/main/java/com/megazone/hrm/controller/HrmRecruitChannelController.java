package com.megazone.hrm.controller;


import com.megazone.core.common.Result;
import com.megazone.hrm.entity.PO.HrmRecruitChannel;
import com.megazone.hrm.service.IHrmRecruitChannelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-06-02
 */
@RestController
@RequestMapping("/hrmRecruitChannel")
@Api(tags = "-")
public class HrmRecruitChannelController {

	@Autowired
	private IHrmRecruitChannelService recruitChannelService;


	@PostMapping("/queryRecruitChannelList")
	@ApiOperation("")
	public Result<List<HrmRecruitChannel>> queryRecruitChannelList() {
		List<HrmRecruitChannel> list = recruitChannelService.lambdaQuery()
				.eq(HrmRecruitChannel::getStatus, 1).list();
		return Result.ok(list);
	}
}

