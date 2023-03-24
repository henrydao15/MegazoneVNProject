package com.megazone.hrm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.BO.DeleteLeaveInformationBO;
import com.megazone.hrm.entity.BO.UpdateInformationBO;
import com.megazone.hrm.entity.PO.HrmEmployeeCertificate;
import com.megazone.hrm.entity.PO.HrmEmployeeQuitInfo;
import com.megazone.hrm.entity.VO.PostInformationVO;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface IHrmEmployeePostService extends BaseService<HrmEmployeeCertificate> {

	/**
	 * @param employeeId
	 * @return
	 */
	PostInformationVO postInformation(Integer employeeId);

	/**
	 * @param updateInformationBO
	 */
	void updatePostInformation(UpdateInformationBO updateInformationBO);

	/**
	 * @param quitInfo
	 */
	void addOrUpdateLeaveInformation(HrmEmployeeQuitInfo quitInfo);

	/**
	 * @param deleteLeaveInformationBO
	 */
	void deleteLeaveInformation(DeleteLeaveInformationBO deleteLeaveInformationBO);

	/**
	 * @return
	 */
	PostInformationVO postArchives();

}
