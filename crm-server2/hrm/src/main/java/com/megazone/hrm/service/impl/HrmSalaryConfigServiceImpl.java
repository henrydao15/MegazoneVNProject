package com.megazone.hrm.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.megazone.core.exception.CrmException;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.hrm.common.HrmCodeEnum;
import com.megazone.hrm.constant.ConfigType;
import com.megazone.hrm.entity.PO.*;
import com.megazone.hrm.entity.VO.QueryInItConfigVO;
import com.megazone.hrm.mapper.HrmSalaryConfigMapper;
import com.megazone.hrm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
@Service
public class HrmSalaryConfigServiceImpl extends BaseServiceImpl<HrmSalaryConfigMapper, HrmSalaryConfig> implements IHrmSalaryConfigService {


	@Autowired
	private IHrmConfigService configService;

	@Autowired
	private IHrmInsuranceMonthRecordService insuranceMonthRecordService;

	@Autowired
	private IHrmSalaryGroupService salaryGroupService;

	@Autowired
	private IHrmInsuranceSchemeService insuranceSchemeService;

	@Autowired
	private IHrmSalarySlipRecordService slipRecordService;

	@Override
	public QueryInItConfigVO queryInItConfig() {
		QueryInItConfigVO data = new QueryInItConfigVO();
		Map<Integer, Integer> map = new HashMap<>();
		configService.lambdaQuery().in(HrmConfig::getType, 2, 3, 4, 5).list()
				.forEach(config -> {
					map.put(config.getType(), Integer.valueOf(config.getValue()));
				});
		data.setStatusInitConfig(map);
		HrmSalaryConfig salaryConfig = getOne(Wrappers.emptyWrapper());
		if (salaryConfig != null) {
			Optional<HrmInsuranceMonthRecord> insuranceMonthRecordOpt = insuranceMonthRecordService.lambdaQuery().orderByDesc(HrmInsuranceMonthRecord::getCreateTime).last("limit 1").oneOpt();
			insuranceMonthRecordOpt.ifPresent(hrmInsuranceMonthRecord -> salaryConfig.setLastSocialSecurityYear(hrmInsuranceMonthRecord.getYear()));
			Optional<HrmSalaryMonthRecord> salaryMonthRecordOpt = ApplicationContextHolder.getBean(IHrmSalaryMonthRecordService.class).lambdaQuery().orderByDesc(HrmSalaryMonthRecord::getCreateTime).last("limit 1").oneOpt();
			salaryMonthRecordOpt.ifPresent(hrmSalaryMonthRecord -> {
				salaryConfig.setLastSalaryYear(hrmSalaryMonthRecord.getYear());
				salaryConfig.setLastSalaryMonth(hrmSalaryMonthRecord.getMonth());
			});
			HrmSalarySlipRecord slipRecord = slipRecordService.lambdaQuery().orderByDesc(HrmSalarySlipRecord::getYear, HrmSalarySlipRecord::getMonth).last("limit 1").one();
			if (slipRecord == null) {
				salaryConfig.setLastSalarySlipMonth(DateUtil.format(new Date(), "yyyy-MM"));
			} else {
				salaryConfig.setLastSalarySlipMonth(slipRecord.getYear() + "-" + slipRecord.getMonth());
			}
			data.setOtherInitConfig(salaryConfig);
		}
		return data;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveInitConfig(HrmSalaryConfig salaryConfig) {
		HrmSalaryConfig one = getOne(Wrappers.emptyWrapper());
		if (salaryConfig.getSalaryStartMonth() != null) {

			String salaryStartMonth = salaryConfig.getSalaryStartMonth();
			DateTime date = DateUtil.parse(salaryStartMonth, "yyyy-MM");
			int month = date.month() + 1;
			int year = date.year();
			DateTime startTime = DateUtil.parse(year + "-" + month + "-" + one.getSalaryCycleStartDay(), "yyyy-MM-dd");
			DateTime endTime;
			if (one.getSalaryCycleStartDay() > 1) {
				DateTime dateTime = DateUtil.offsetMonth(startTime, 1);
				int nextMonth = dateTime.month() + 1;
				endTime = DateUtil.parse(year + "-" + nextMonth + "-" + one.getSalaryCycleEndDay(), "yyyy-MM-dd");
			} else {
				endTime = DateUtil.parseDate(DateUtil.formatDate(DateUtil.endOfMonth(startTime)));
			}
			HrmSalaryMonthRecord salaryMonthRecord = new HrmSalaryMonthRecord();
			salaryMonthRecord.setTitle(month + "monthly salary report");
			salaryMonthRecord.setYear(year);
			salaryMonthRecord.setMonth(month);
			salaryMonthRecord.setStartTime(startTime);
			salaryMonthRecord.setEndTime(endTime);
			ApplicationContextHolder.getBean(IHrmSalaryMonthRecordService.class).save(salaryMonthRecord);
		}
		if (one == null) {
			save(salaryConfig);
		} else {
			salaryConfig.setConfigId(one.getConfigId());
			updateById(salaryConfig);
		}
		HrmSalaryConfig salaryConfig1 = getOne(Wrappers.emptyWrapper());

		if (StrUtil.isNotEmpty(salaryConfig1.getSocialSecurityStartMonth())) {
			updateInitStatus(ConfigType.INSURANCE_INIT_CONFIG2.getValue());
			insuranceMonthRecordService.computeInsuranceData();
		}
		if (salaryConfig.getSalaryStartMonth() != null && salaryConfig1.getSalaryCycleStartDay() != null && salaryConfig1.getSocialSecurityMonthType() != null) {
			updateInitStatus(ConfigType.SALARY_INIT_CONFIG2.getValue());
		}
	}

	@Override
	public void updateInitStatus(Integer type) {
		if (type.equals(ConfigType.SALARY_INIT_CONFIG1.getValue())) {
			int count = salaryGroupService.count();
			if (count == 0) {
				throw new CrmException(HrmCodeEnum.SALARY_GROUP_NOT_CONFIG);
			}
		}
		if (type.equals(ConfigType.INSURANCE_INIT_CONFIG1.getValue())) {
			int count = insuranceSchemeService.count();
			if (count == 0) {
				throw new CrmException(HrmCodeEnum.INSURANCE_NOT_CONFIG);
			}
		}
		configService.lambdaUpdate().set(HrmConfig::getValue, 1).eq(HrmConfig::getType, type).update();
	}
}
