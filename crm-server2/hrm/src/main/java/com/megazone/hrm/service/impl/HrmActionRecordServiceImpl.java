package com.megazone.hrm.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Dict;
import com.megazone.core.common.JSON;
import com.megazone.core.common.Result;
import com.megazone.core.entity.UserInfo;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.utils.TransferUtil;
import com.megazone.hrm.constant.HrmActionBehaviorEnum;
import com.megazone.hrm.constant.HrmActionTypeEnum;
import com.megazone.hrm.entity.BO.QueryRecordListBO;
import com.megazone.hrm.entity.PO.HrmActionRecord;
import com.megazone.hrm.entity.VO.HrmModelFiledVO;
import com.megazone.hrm.entity.VO.QueryRecordListVO;
import com.megazone.hrm.mapper.HrmActionRecordMapper;
import com.megazone.hrm.service.IHrmActionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * hrm
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
@Service
public class HrmActionRecordServiceImpl extends BaseServiceImpl<HrmActionRecordMapper, HrmActionRecord> implements IHrmActionRecordService {


	@Autowired
	private AdminService adminService;

	@Override
	public boolean saveRecord(HrmActionTypeEnum actionTypeEnum, HrmActionBehaviorEnum behaviorEnum, List<String> content, Integer typeId) {
		if (CollUtil.isEmpty(content)) {
			return true;
		}
		HrmActionRecord hrmActionRecord = new HrmActionRecord();
		hrmActionRecord.setIpAddress("127.0.0.1");
		hrmActionRecord.setType(actionTypeEnum.getValue());
		hrmActionRecord.setBehavior(behaviorEnum.getValue());
		hrmActionRecord.setTypeId(typeId);
		hrmActionRecord.setContent(JSON.toJSONString(content));
		return save(hrmActionRecord);
	}

	@Override
	public List<QueryRecordListVO> queryRecordList(QueryRecordListBO queryRecordListBO) {
		List<HrmActionRecord> list = lambdaQuery().eq(HrmActionRecord::getType, queryRecordListBO.getType())
				.eq(HrmActionRecord::getTypeId, queryRecordListBO.getTypeId())
				.orderByDesc(HrmActionRecord::getCreateTime).list();
		List<QueryRecordListVO> recordListVOS = TransferUtil.transferList(list, QueryRecordListVO.class);
		recordListVOS.forEach(record -> {
			Result<UserInfo> userInfoResult = adminService.getUserInfo(record.getCreateUserId());
			UserInfo userInfo = userInfoResult.getData();
			record.setRealname(userInfo.getRealname());
			record.setImg(userInfo.getImg());
		});
		return recordListVOS;
	}

	@Override
	public List<HrmModelFiledVO> queryFieldValue(Dict kv) {
		return baseMapper.queryFieldValue(kv);
	}
}
