package com.megazone.hrm.service;

import com.megazone.core.common.FieldEnum;
import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.constant.LabelGroupEnum;
import com.megazone.hrm.entity.BO.*;
import com.megazone.hrm.entity.PO.*;
import com.megazone.hrm.entity.VO.*;

import java.util.*;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface IHrmEmployeeService extends BaseService<HrmEmployee> {

	/**
	 * @param employeeVO
	 */
	void add(AddEmployeeBO employeeVO);

	/**
	 * @return
	 */
	List<SimpleHrmEmployeeVO> queryAllEmployeeList();

	/**
	 * @param employee
	 * @return
	 */
	SimpleHrmEmployeeVO transferSimpleEmp(HrmEmployee employee);

	/**
	 * @param employeeId
	 * @return
	 */
	PersonalInformationVO personalInformation(Integer employeeId);

	/**
	 * @return
	 */
	PersonalInformationVO personalArchives();

	/**
	 * @param employeeId
	 * @return
	 */
	HrmEmployee queryById(Integer employeeId);

	/**
	 *
	 */
	List<InformationFieldVO> transferInformation(JSONObject model, LabelGroupEnum labelGroupEnum, List<HrmEmployeeData> fieldValueList);

	/**
	 *
	 */
	List<InformationFieldVO> transferInformation(JSONObject model, List<HrmEmployeeField> employeeFields, List<HrmEmployeeData> fieldValueList);

	/**
	 * @param updateInformationBO
	 */
	void updateInformation(UpdateInformationBO updateInformationBO);

	/**
	 * @param updateInformationBO
	 */
	void updateCommunication(UpdateInformationBO updateInformationBO);

	/**
	 * @param educationExperience
	 */
	void addOrUpdateEduExperience(HrmEmployeeEducationExperience educationExperience);

	/**
	 * @param educationId
	 */
	void deleteEduExperience(Integer educationId);

	/**
	 * @param workExperience
	 */
	void addOrUpdateWorkExperience(HrmEmployeeWorkExperience workExperience);

	/**
	 * @param workExpId
	 */
	void deleteWorkExperience(Integer workExpId);

	/**
	 * @param certificate
	 */
	void addOrUpdateCertificate(HrmEmployeeCertificate certificate);

	/**
	 * @param certificateId
	 */
	void deleteCertificate(Integer certificateId);

	/**
	 * @param trainingExperience
	 */
	void addOrUpdateTrainingExperience(HrmEmployeeTrainingExperience trainingExperience);

	/**
	 * @param trainingId
	 */
	void deleteTrainingExperience(Integer trainingId);

	/**
	 * @return
	 */
	List<HrmEmployeeField> queryContactsAddField();

	/**
	 * @param updateInformationBO
	 */
	void addOrUpdateContacts(UpdateInformationBO updateInformationBO);

	/**
	 * @param contractsId
	 */
	void deleteContacts(Integer contractsId);

	/**
	 * @param employeeIds
	 */
	void deleteByIds(List<Integer> employeeIds);

	/**
	 * @param hrmEmployeeChangeRecord
	 */
	void change(HrmEmployeeChangeRecord hrmEmployeeChangeRecord);

	/**
	 * @param updateInsuranceSchemeBO
	 */
	void updateInsuranceScheme(UpdateInsuranceSchemeBO updateInsuranceSchemeBO);

	/**
	 * @param employeePageListBO
	 * @return
	 */
	BasePage<Map<String, Object>> queryPageList(QueryEmployeePageListBO employeePageListBO);

	/**
	 * @param employeeIds
	 * @return
	 */
	List<SimpleHrmEmployeeVO> querySimpleEmployeeList(Collection<Integer> employeeIds);

	/**
	 * @return
	 */
	Map<Integer, Long> queryEmployeeStatusNum();


	/**
	 * @param employeeBO
	 */
	void againOnboarding(AddEmployeeFieldManageBO employeeBO);

	/**
	 * @param mobile
	 * @return
	 */
	EmployeeInfo queryEmployeeInfoByMobile(String mobile);

	/**
	 * @param employeeBO
	 */
	void confirmEntry(AddEmployeeFieldManageBO employeeBO);

	/**
	 * @return
	 */
	List<HrmEmployeeField> downloadExcelFiled();


	/**
	 * @param employeePageListBO
	 * @return
	 */
	List<Map<String, Object>> export(QueryEmployeePageListBO employeePageListBO);

	/**
	 * @param uniqueList
	 * @return
	 */
	Integer queryFieldValueNoDelete(List<HrmEmployeeField> uniqueList);

	/**
	 * @param year
	 * @param month
	 * @return
	 */
	List<Integer> queryToInByMonth(int year, int month);

	/**
	 * @param year
	 * @param month
	 * @return
	 */
	List<Integer> queryToLeaveByMonth(int year, int month);

	/**
	 * @return
	 */
	List<Integer> queryToCorrectCount();


	/**
	 * @param time
	 * @param employeeIds
	 * @return
	 */
	List<HrmEmployee> queryBirthdayListByTime(Date time, Collection<Integer> employeeIds);

	/**
	 * @param time
	 * @param employeeIds
	 * @return
	 */
	List<HrmEmployee> queryEntryEmpListByTime(Date time, Collection<Integer> employeeIds);

	/**
	 * @param time
	 * @param employeeIds
	 * @return
	 */
	List<HrmEmployee> queryBecomeEmpListByTime(Date time, Collection<Integer> employeeIds);

	/**
	 * @param time
	 * @param employeeIds
	 * @return
	 */
	List<HrmEmployee> queryLeaveEmpListByTime(Date time, Collection<Integer> employeeIds);


	/**
	 * @return
	 */
	List<Integer> queryBirthdayEmp();

	/**
	 * @return
	 */
	List<SimpleHrmEmployeeVO> queryInEmployeeList();


	/**
	 * @param deptId
	 * @return
	 */
	DeptEmployeeListVO queryDeptEmployeeList(Integer deptId);


	/**
	 * @param employeeBOS
	 */
	void adminAddEmployee(List<AddEmployeeBO> employeeBOS);

	/**
	 * @param deptUserListByUserBO
	 * @return
	 */
	Set<SimpleHrmEmployeeVO> queryDeptUserListByUser(DeptUserListByUserBO deptUserListByUserBO);

	/**
	 * @param employeeIds
	 * @return
	 */
	Set<Integer> filterDeleteEmployeeIds(Set<Integer> employeeIds);

	List<SimpleHrmEmployeeVO> queryAllSimpleEmployeeList(Collection<Integer> employeeIds);

	/**
	 * @param employeeIds
	 * @return
	 */
	Set<Integer> queryChildEmployeeId(List<Integer> employeeIds);

	/**
	 * @param queryNotesStatusBO
	 * @param employeeIds
	 * @return
	 */
	Set<String> queryEntryStatusList(QueryNotesStatusBO queryNotesStatusBO, Collection<Integer> employeeIds);

	/**
	 * @param queryNotesStatusBO
	 * @param employeeIds
	 * @return
	 */
	Set<String> queryBecomeStatusList(QueryNotesStatusBO queryNotesStatusBO, Collection<Integer> employeeIds);

	/**
	 * @param queryNotesStatusBO
	 * @param employeeIds
	 * @return
	 */
	Set<String> queryLeaveStatusList(QueryNotesStatusBO queryNotesStatusBO, Collection<Integer> employeeIds);

	/**
	 * @param entryStatus
	 * @return
	 */
	List<HrmModelFiledVO> queryEmployeeField(Integer entryStatus);

	/**
	 * @param addEmployeeFieldManageBO
	 */
	void addEmployeeField(AddEmployeeFieldManageBO addEmployeeFieldManageBO);

	/**
	 * @param record   data
	 * @param typeEnum type
	 */
	public void recordToFormType(InformationFieldVO record, FieldEnum typeEnum);

	/**
	 * @param employeeIds
	 * @return
	 */
	SimpleHrmEmployeeVO querySimpleEmployee(Integer employeeIds);
}
