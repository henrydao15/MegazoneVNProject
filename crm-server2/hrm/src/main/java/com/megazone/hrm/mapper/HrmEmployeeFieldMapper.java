package com.megazone.hrm.mapper;

import com.megazone.core.servlet.BaseMapper;
import com.megazone.hrm.entity.PO.HrmEmployeeField;
import com.megazone.hrm.entity.VO.EmployeeArchivesFieldVO;
import com.megazone.hrm.entity.VO.EmployeeHeadFieldVO;
import com.megazone.hrm.entity.VO.FiledListVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface HrmEmployeeFieldMapper extends BaseMapper<HrmEmployeeField> {

	/**
	 * @return
	 */
	@Select("select field_id from `wk_hrm_employee_field` where is_head_field = 1 order by label_group,sorting")
	List<Integer> queryHeadFieldId();

	List<EmployeeHeadFieldVO> queryListHeads(Long userId);

	List<FiledListVO> queryFields();

	List<EmployeeArchivesFieldVO> queryEmployeeArchivesField();

}
