package com.megazone.hrm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.megazone.core.common.Result;
import com.megazone.core.feign.admin.service.AdminFileService;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.core.utils.TransferUtil;
import com.megazone.hrm.constant.HrmActionBehaviorEnum;
import com.megazone.hrm.constant.LabelGroupEnum;
import com.megazone.hrm.entity.PO.HrmEmployeeContract;
import com.megazone.hrm.entity.VO.ContractInformationVO;
import com.megazone.hrm.mapper.HrmEmployeeContractMapper;
import com.megazone.hrm.service.IHrmEmployeeContractService;
import com.megazone.hrm.service.actionrecord.impl.EmployeeActionRecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
@Service
public class HrmEmployeeContractServiceImpl extends BaseServiceImpl<HrmEmployeeContractMapper, HrmEmployeeContract> implements IHrmEmployeeContractService {

	@Autowired
	private AdminFileService adminFileService;

	@Resource
	private EmployeeActionRecordServiceImpl employeeActionRecordService;

	@Override
	public List<ContractInformationVO> contractInformation(Integer employeeId) {
		List<HrmEmployeeContract> contractList = lambdaQuery().eq(HrmEmployeeContract::getEmployeeId, employeeId).orderByAsc(HrmEmployeeContract::getSort).list();
		List<ContractInformationVO> contractInformationVOList = TransferUtil.transferList(contractList, ContractInformationVO.class);
		contractInformationVOList.forEach(contractInformationVO -> {
			Result<List<FileEntity>> listResult = adminFileService.queryFileList(contractInformationVO.getBatchId());
			contractInformationVO.setFileList(listResult.getData());
		});
		return contractInformationVOList;
	}

	@Override
	public void addOrUpdateContract(HrmEmployeeContract employeeContract) {
		if (employeeContract.getContractId() == null) {
			employeeActionRecordService.addOrDeleteRecord(HrmActionBehaviorEnum.ADD, LabelGroupEnum.CONTRACT, employeeContract.getEmployeeId());
		} else {
			HrmEmployeeContract old = getById(employeeContract.getContractId());
			employeeActionRecordService.entityUpdateRecord(LabelGroupEnum.CONTRACT, BeanUtil.beanToMap(old), BeanUtil.beanToMap(employeeContract), employeeContract.getEmployeeId());
		}
		saveOrUpdate(employeeContract);
	}

	@Override
	public void deleteContract(Integer contractId) {
		HrmEmployeeContract contract = getById(contractId);
		employeeActionRecordService.addOrDeleteRecord(HrmActionBehaviorEnum.DELETE, LabelGroupEnum.CONTRACT, contract.getEmployeeId());
		removeById(contractId);
	}

	@Override
	public List<Integer> queryToExpireContractCount() {
		return getBaseMapper().queryToExpireContractCount();
	}
}
