package com.megazone.hrm.service;

import com.megazone.core.common.FieldEnum;
import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.constant.LabelGroupEnum;
import com.megazone.hrm.entity.BO.*;
import com.megazone.hrm.entity.PO.HrmEmployeeContactsData;
import com.megazone.hrm.entity.PO.HrmEmployeeData;
import com.megazone.hrm.entity.PO.HrmEmployeeField;
import com.megazone.hrm.entity.VO.EmployeeArchivesFieldVO;
import com.megazone.hrm.entity.VO.EmployeeHeadFieldVO;
import com.megazone.hrm.entity.VO.FiledListVO;
import com.megazone.hrm.entity.VO.HrmModelFiledVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface IHrmEmployeeFieldService extends BaseService<HrmEmployeeField> {

	/**
	 * label_group
	 *
	 * @return
	 */
	List<HrmEmployeeField> queryInformationFieldByLabelGroup(LabelGroupEnum labelGroup);

	/**
	 * @return
	 */
	List<EmployeeHeadFieldVO> queryListHeads();

	/**
	 * @param updateFieldConfigBOList
	 */
	void updateFieldConfig(List<UpdateFieldConfigBO> updateFieldConfigBOList);


	/**
	 * @return
	 */
	List<FiledListVO> queryFields();

	/**
	 * @param label
	 * @return
	 */
	List<List<HrmEmployeeField>> queryFieldByLabel(Integer label);

	/**
	 * @param addEmployeeFieldBO
	 */
	void saveField(AddEmployeeFieldBO addEmployeeFieldBO);

	/**
	 * @param fieldList
	 * @param labelGroupEnum
	 * @param employeeId
	 */
	void saveEmployeeField(List<HrmEmployeeData> fieldList, LabelGroupEnum labelGroupEnum, Integer employeeId);

	/**
	 * @param fieldList
	 * @param contactPerson
	 * @param contactsId
	 */
	void saveEmployeeContactsField(List<HrmEmployeeContactsData> fieldList, LabelGroupEnum contactPerson, Integer contactsId);

	/**
	 * @param verifyUniqueBO
	 */
	VerifyUniqueBO verifyUnique(VerifyUniqueBO verifyUniqueBO);

	/**
	 * @param updateFieldWidthBO
	 */
	void updateFieldWidth(UpdateFieldWidthBO updateFieldWidthBO);

	/**
	 * @return
	 */
	List<EmployeeArchivesFieldVO> queryEmployeeArchivesField();

	/**
	 * @param archivesFields
	 */
	void setEmployeeArchivesField(List<EmployeeArchivesFieldVO> archivesFields);

	/**
	 * @param writeArchivesBO
	 */
	void sendWriteArchives(SendWriteArchivesBO writeArchivesBO);

	/**
	 * @param entryStatus
	 * @return
	 */
	List<HrmModelFiledVO> queryField(Integer entryStatus);

	/**
	 * @param record   data
	 * @param typeEnum type
	 */
	public void recordToFormType(HrmModelFiledVO record, FieldEnum typeEnum);

	/**
	 * @param label
	 * @param fieldType
	 * @param existNameList
	 * @return fieldName
	 */
	public String getNextFieldName(Integer label, Integer fieldType, List<String> existNameList, Integer depth);

	/**
	 *
	 */
	String convertObjectValueToString(Integer type, Object value, String defaultValue);

	/**
	 *
	 */
	boolean equalsByType(Object type);

	/**
	 *
	 */
	boolean equalsByType(Object type, FieldEnum... fieldEnums);

	/**
	 *
	 */
	Object convertValueByFormType(Object value, FieldEnum typeEnum);
}
