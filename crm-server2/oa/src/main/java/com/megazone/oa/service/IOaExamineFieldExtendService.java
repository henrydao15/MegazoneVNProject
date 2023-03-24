package com.megazone.oa.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.oa.entity.PO.OaExamineFieldExtend;

import java.util.List;

public interface IOaExamineFieldExtendService extends BaseService<OaExamineFieldExtend> {
	List<OaExamineFieldExtend> queryOaExamineFieldExtend(Integer parentFieldId);

	boolean saveOrUpdateOaExamineFieldExtend(List<OaExamineFieldExtend> oaExamineFieldExtendList, Integer parentFieldId, boolean isUpdate);

	boolean deleteOaExamineFieldExtend(Integer parentFieldId);
}
