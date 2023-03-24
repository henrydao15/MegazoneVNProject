package com.megazone.hrm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.BO.AddFileBO;
import com.megazone.hrm.entity.BO.QueryFileBySubTypeBO;
import com.megazone.hrm.entity.PO.HrmEmployeeFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface IHrmEmployeeFileService extends BaseService<HrmEmployeeFile> {

	/**
	 * @param employeeId
	 * @return
	 */
	Map<String, Object> queryFileNum(Integer employeeId);

	/**
	 * @param queryFileBySubTypeBO
	 * @return
	 */
	List<HrmEmployeeFile> queryFileBySubType(QueryFileBySubTypeBO queryFileBySubTypeBO);

	/**
	 * @param addFileBO
	 */
	void addFile(AddFileBO addFileBO);

	/**
	 * @param employeeFileId
	 */
	void deleteFile(Integer employeeFileId);
}
