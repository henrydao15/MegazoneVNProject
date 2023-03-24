package com.megazone.crm.common.log;

import cn.hutool.core.bean.BeanUtil;
import com.megazone.core.common.log.Content;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.PO.CrmMarketing;
import com.megazone.crm.service.ICrmMarketingService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

public class CrmMarketingLog {
	private SysLogUtil sysLogUtil = ApplicationContextHolder.getBean(SysLogUtil.class);

	private ICrmMarketingService crmMarketingService = ApplicationContextHolder.getBean(ICrmMarketingService.class);

	public Content update(CrmMarketing crmMarketing) {
		return sysLogUtil.updateRecord(BeanUtil.beanToMap(crmMarketingService.getById(crmMarketing.getMarketingId())), BeanUtil.beanToMap(crmMarketing), CrmEnum.MARKETING, crmMarketing.getMarketingName());
	}

	public List<Content> updateStatus(@RequestParam("marketingIds") String marketingIds, @RequestParam("status") Integer status) {
		List<Content> contentList = new ArrayList<>();
		for (String id : marketingIds.split(",")) {
			CrmMarketing marketing = crmMarketingService.getById(id);
			String detail;
			if (status == 1) {
				detail = "Activate:" + marketing.getMarketingName() + "enable";
			} else {
				detail = "Activate:" + marketing.getMarketingName() + "deactivate";
			}
			contentList.add(new Content(marketing.getMarketingName(), detail));
		}
		return contentList;
	}
}
