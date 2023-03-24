package com.megazone.crm.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.megazone.core.common.BaseStatusEnum;
import com.megazone.core.common.Const;
import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.entity.AdminConfig;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.crm.constant.CrmCodeEnum;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.PO.*;
import com.megazone.crm.entity.VO.CrmNumberSettingVO;
import com.megazone.crm.mapper.CrmNumberSettingMapper;
import com.megazone.crm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class CrmNumberSettingServiceImpl extends BaseServiceImpl<CrmNumberSettingMapper, CrmNumberSetting> implements ICrmNumberSettingService {

	@Autowired
	private AdminService adminService;

	private static String generateNumber(CrmNumberSetting setting, String num, Integer index) {
		Integer maxNum;
		if (StrUtil.isNotEmpty(num)) {
			String[] split = num.split("-");
			if (split.length > index) {
				try {
					maxNum = Integer.valueOf(split[index]);
				} catch (NumberFormatException e) {
					maxNum = 0;
				}
			} else {
				maxNum = 0;
			}
		} else {
			maxNum = 0;
		}
		String numberSb;
		boolean b = setting.getLastNumber() != null && setting.getLastDate() != null;
		if (setting.getLastNumber() != null && setting.getLastDate() != null) {
			if (setting.getResetType() == 1 && !DateUtil.isSameDay(setting.getLastDate(), new Date())) {
				b = false;
			} else if (setting.getResetType() == 2 && !DateUtil.format(setting.getLastDate(), "yyyyMM").equals(DateUtil.format(new Date(), "yyyyMM"))) {
				b = false;
			} else if (setting.getResetType() == 3 && DateUtil.year(setting.getLastDate()) != DateUtil.year(new Date())) {
				b = false;
			}
		}
		if (b) {
			Integer number = maxNum + setting.getIncreaseNumber();

			numberSb = String.format("%" + setting.getValue().length() + "s", number).replaceAll(" ", "0");
			setting.setLastNumber(number).setLastDate(new Date());
		} else {
			numberSb = setting.getValue();
			setting.setLastNumber(Integer.parseInt(setting.getValue())).setLastDate(new Date());
		}
		return numberSb;
	}

	@Override
	public List<CrmNumberSetting> queryListByPid(Integer pid) {
		QueryWrapper<CrmNumberSetting> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("pid", pid);
		return list(queryWrapper);
	}

	@Override
	public void deleteByPid(Integer pid) {
		QueryWrapper<CrmNumberSetting> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("pid", pid);
		remove(queryWrapper);
	}

	@Override
	public String generateNumber(AdminConfig config, Date date) {
		CrmEnum crmEnum = CrmEnum.parse(Integer.valueOf(config.getValue()));
		List<String> numberList = new ArrayList<>();
		String maxNum;
		switch (crmEnum) {
			case CONTRACT:
				LambdaQueryWrapper<CrmContract> contractLambdaQueryWrapper = new LambdaQueryWrapper<>();
				contractLambdaQueryWrapper.select(CrmContract::getNum).ne(CrmContract::getCheckStatus, 7).orderByDesc(CrmContract::getCreateTime).last("limit 1");
				maxNum = ApplicationContextHolder.getBean(ICrmContractService.class).getObj(contractLambdaQueryWrapper, Object::toString);
				LambdaQueryWrapper<CrmContract> contractWrapper = new LambdaQueryWrapper<>();
				contractWrapper.select(CrmContract::getNum).ne(CrmContract::getCheckStatus, 7);
				numberList.addAll(ApplicationContextHolder.getBean(ICrmContractService.class).listObjs(contractWrapper, Object::toString));
				break;
			case RECEIVABLES:
				LambdaQueryWrapper<CrmReceivables> receivablesLambdaQueryWrapper = new LambdaQueryWrapper<>();
				receivablesLambdaQueryWrapper.select(CrmReceivables::getNumber).ne(CrmReceivables::getCheckStatus, 7).orderByDesc(CrmReceivables::getCreateTime).last("limit 1");
				maxNum = ApplicationContextHolder.getBean(ICrmReceivablesService.class).getObj(receivablesLambdaQueryWrapper, Object::toString);
				LambdaQueryWrapper<CrmReceivables> receivablesWrapper = new LambdaQueryWrapper<>();
				receivablesWrapper.select(CrmReceivables::getNumber).ne(CrmReceivables::getCheckStatus, 7);
				numberList.addAll(ApplicationContextHolder.getBean(ICrmReceivablesService.class).listObjs(receivablesWrapper, Object::toString));
				break;
			case RETURN_VISIT:
				LambdaQueryWrapper<CrmReturnVisit> returnVisitLambdaQueryWrapper = new LambdaQueryWrapper<>();
				returnVisitLambdaQueryWrapper.select(CrmReturnVisit::getVisitNumber).orderByDesc(CrmReturnVisit::getCreateTime).last("limit 1");
				maxNum = ApplicationContextHolder.getBean(ICrmReturnVisitService.class).getObj(returnVisitLambdaQueryWrapper, Object::toString);
				LambdaQueryWrapper<CrmReturnVisit> returnVisitWrapper = new LambdaQueryWrapper<>();
				returnVisitWrapper.select(CrmReturnVisit::getVisitNumber);
				numberList.addAll(ApplicationContextHolder.getBean(ICrmReturnVisitService.class).listObjs(returnVisitWrapper, Object::toString));
				break;
			case INVOICE:
				LambdaQueryWrapper<CrmInvoice> invoiceLambdaQueryWrapper = new LambdaQueryWrapper<>();
				invoiceLambdaQueryWrapper.select(CrmInvoice::getInvoiceApplyNumber).ne(CrmInvoice::getCheckStatus, 7).orderByDesc(CrmInvoice::getCreateTime).last("limit 1");
				maxNum = ApplicationContextHolder.getBean(ICrmInvoiceService.class).getObj(invoiceLambdaQueryWrapper, Object::toString);
				LambdaQueryWrapper<CrmInvoice> invoiceWrapper = new LambdaQueryWrapper<>();
				invoiceWrapper.select(CrmInvoice::getInvoiceApplyNumber).ne(CrmInvoice::getCheckStatus, 7);
				numberList.addAll(ApplicationContextHolder.getBean(ICrmInvoiceService.class).listObjs(invoiceWrapper, Object::toString));
				break;
			default:
				throw new CrmException(SystemCodeEnum.SYSTEM_NO_SUCH_PARAMENT_ERROR);
		}
		String result;
		int i = 0;
		List<CrmNumberSetting> settingList = lambdaQuery().eq(CrmNumberSetting::getPid, config.getSettingId()).orderByAsc(CrmNumberSetting::getSort).list();
		do {
			StringBuilder numberSb = new StringBuilder();
			for (int j = 0; j < settingList.size(); j++) {
				CrmNumberSetting setting = settingList.get(j);
				if (j > 0) {
					numberSb.append("-");
				}
				if (setting.getType() == 1) {
					numberSb.append(setting.getValue());
				} else if (setting.getType() == 2) {
					if (ObjectUtil.isEmpty(date)) {
						numberSb.append(DateUtil.format(new Date(), setting.getValue()));
					} else {
						numberSb.append(DateUtil.format(date, setting.getValue()));
					}
				} else if (setting.getType() == 3) {
					numberSb.append(generateNumber(setting, maxNum, j));
				}
			}
			result = numberSb.toString();
			if (i > numberList.size()) {
				updateBatchById(settingList, Const.BATCH_SAVE_SIZE);
				return result;
			}
			i++;
			maxNum = result;
		} while (numberList.contains(result));
		updateBatchById(settingList, Const.BATCH_SAVE_SIZE);
		return result;
	}

	/**
	 * @return data
	 */

	@Override
	public List<CrmNumberSettingVO> queryNumberSetting() {
		String name = "numberSetting";
		List<AdminConfig> adminConfigList = adminService.queryConfigByName(name).getData();
		List<CrmNumberSettingVO> numberSettingList = new ArrayList<>();
		adminConfigList.forEach(adminConfig -> {
			CrmNumberSettingVO numberSetting = new CrmNumberSettingVO();
			numberSetting.setSettingId(adminConfig.getSettingId());
			numberSetting.setLabel(adminConfig.getValue());
			numberSetting.setStatus(adminConfig.getStatus());
			numberSetting.setSetting(queryListByPid(adminConfig.getSettingId()));
			numberSettingList.add(numberSetting);
		});
		return numberSettingList;
	}

	/**
	 * @return data
	 */
	@Override
	public AdminConfig queryInvoiceNumberSetting() {
		String name = "numberSetting";
		QueryWrapper<AdminConfig> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("name", name);
		List<AdminConfig> adminConfigList = adminService.queryConfigByName(name).getData();
		for (AdminConfig adminConfig : adminConfigList) {
			if (Objects.equals("18", adminConfig.getValue())) {
				return adminConfig;
			}
		}
		return null;
	}

	/**
	 * @param numberSettingList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void setNumberSetting(List<CrmNumberSettingVO> numberSettingList) {
		String name = "numberSetting";

		int numberSettingSize = 2;
		numberSettingList.forEach(numberSetting -> {
			if (BaseStatusEnum.OPEN.getStatus().equals(numberSetting.getStatus()) && numberSetting.getSetting().size() < numberSettingSize) {
				throw new CrmException(CrmCodeEnum.CRM_NUMBER_SETTING_LENGTH_ERROR);
			}
			AdminConfig config = null;
			List<AdminConfig> adminConfigList = adminService.queryConfigByName(name).getData();
			for (AdminConfig adminConfig : adminConfigList) {
				if (Objects.equals(numberSetting.getLabel(), adminConfig.getValue())) {
					config = adminConfig;
					break;
				}
			}
			if (config == null) {
				config = new AdminConfig();
				config.setName(name);
				config.setValue(numberSetting.getLabel());
				config.setDescription("Auto number setting");
			}
			config.setStatus(numberSetting.getStatus());
			adminService.updateAdminConfig(config);
			deleteByPid(numberSetting.getSettingId());
			for (int i = 0; i < numberSetting.getSetting().size(); i++) {
				CrmNumberSetting setting = numberSetting.getSetting().get(i);
				setting.setPid(config.getSettingId()).setSort(i);
				save(setting);
			}
		});
	}
}
