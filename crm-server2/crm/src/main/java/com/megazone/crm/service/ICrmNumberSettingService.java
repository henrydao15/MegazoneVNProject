package com.megazone.crm.service;

import com.megazone.core.feign.admin.entity.AdminConfig;
import com.megazone.core.servlet.BaseService;
import com.megazone.crm.entity.PO.CrmNumberSetting;
import com.megazone.crm.entity.VO.CrmNumberSettingVO;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-28
 */
public interface ICrmNumberSettingService extends BaseService<CrmNumberSetting> {
	/**
	 * @param pid pid
	 * @return data
	 */
	public List<CrmNumberSetting> queryListByPid(Integer pid);

	/**
	 * @param pid pid
	 */
	public void deleteByPid(Integer pid);

	/**
	 * @param config
	 * @param date
	 * @return num
	 */
	public String generateNumber(AdminConfig config, Date date);

	/**
	 * @return data
	 */
	public List<CrmNumberSettingVO> queryNumberSetting();

	/**
	 * @return data
	 */
	public AdminConfig queryInvoiceNumberSetting();


	/**
	 * @param numberSettingList
	 */
	public void setNumberSetting(List<CrmNumberSettingVO> numberSettingList);
}
