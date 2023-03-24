package com.megazone.examine.service.impl;

import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.examine.entity.PO.ExamineRecordOptional;
import com.megazone.examine.mapper.ExamineRecordOptionalMapper;
import com.megazone.examine.service.IExamineRecordOptionalService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-12-02
 */
@Service
public class ExamineRecordOptionalServiceImpl extends BaseServiceImpl<ExamineRecordOptionalMapper, ExamineRecordOptional> implements IExamineRecordOptionalService {

	/**
	 * @param flowId   ID
	 * @param recordId ID
	 * @param userList
	 */
	@Override
	public void saveRecordOptionalInfo(Integer flowId, Integer recordId, List<Long> userList) {
		List<ExamineRecordOptional> recordOptionalList = new ArrayList<>();
		for (int i = 0; i < userList.size(); i++) {
			ExamineRecordOptional examineRecordOptional = new ExamineRecordOptional();
			examineRecordOptional.setUserId(userList.get(i));
			examineRecordOptional.setSort(i);
			examineRecordOptional.setRecordId(recordId);
			examineRecordOptional.setFlowId(flowId);
			recordOptionalList.add(examineRecordOptional);
		}
		saveBatch(recordOptionalList);
	}
}
