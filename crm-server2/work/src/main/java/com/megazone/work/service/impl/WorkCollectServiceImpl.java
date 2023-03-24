package com.megazone.work.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.utils.UserUtil;
import com.megazone.work.entity.PO.WorkCollect;
import com.megazone.work.mapper.WorkCollectMapper;
import com.megazone.work.service.IWorkCollectService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author wyq
 * @since 2020-05-15
 */
@Service
public class WorkCollectServiceImpl extends BaseServiceImpl<WorkCollectMapper, WorkCollect> implements IWorkCollectService {

	@Override
	public void collect(Integer workId) {
		Long userId = UserUtil.getUserId();
		Integer count = count(new QueryWrapper<WorkCollect>().eq("user_id", userId).eq("work_id", workId));
		if (count == 0) {
			WorkCollect workCollect = new WorkCollect();
			workCollect.setWorkId(workId);
			workCollect.setUserId(userId);
			save(workCollect);
		} else {
			remove(new QueryWrapper<WorkCollect>().eq("user_id", userId).eq("work_id", workId));
		}
	}
}
