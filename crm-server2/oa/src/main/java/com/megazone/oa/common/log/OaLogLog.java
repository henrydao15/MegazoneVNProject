package com.megazone.oa.common.log;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.Content;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.oa.entity.PO.OaLog;
import com.megazone.oa.service.IOaLogService;

import java.util.List;

public class OaLogLog {

	private IOaLogService oaLogService = ApplicationContextHolder.getBean(IOaLogService.class);
	private SysLogUtil sysLogUtil = ApplicationContextHolder.getBean(SysLogUtil.class);


	public Content addOrUpdate(JSONObject jsonObject) {
		OaLog oaLog = jsonObject.toJavaObject(OaLog.class);
		if (oaLog.getLogId() != null) {
			OaLog oldLog = oaLogService.getById(oaLog.getLogId());
			List<String> list = sysLogUtil.searchChange(BeanUtil.beanToMap(oldLog), BeanUtil.beanToMap(oaLog), "log");
			return new Content("", "Modified log:" + StrUtil.join("", list), BehaviorEnum.SAVE);
		} else {
			return new Content("", "Added log", BehaviorEnum.SAVE);
		}
	}
}
