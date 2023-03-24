package com.megazone.crm.mapper;

import com.megazone.core.servlet.BaseMapper;
import com.megazone.crm.entity.PO.CrmMarketingField;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author
 * @date 2020/12/2
 */
public interface CrmMarketingFieldMapper extends BaseMapper<CrmMarketingField> {

	void deleteByChooseId(@Param("ids") List<Integer> arr, @Param("formId") Integer formId);
}
