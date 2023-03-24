package com.megazone.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.crm.entity.PO.CrmContactsBusiness;
import com.megazone.crm.mapper.CrmContactsBusinessMapper;
import com.megazone.crm.service.ICrmContactsBusinessService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
@Service
public class CrmContactsBusinessServiceImpl extends BaseServiceImpl<CrmContactsBusinessMapper, CrmContactsBusiness> implements ICrmContactsBusinessService {

	/**
	 * @param business   ID
	 * @param contactsId ID
	 */
	@Override
	public void save(Integer business, Integer contactsId) {
		lambdaUpdate().eq(CrmContactsBusiness::getBusinessId, business).eq(CrmContactsBusiness::getContactsId, contactsId).remove();
//        if (count == 0){
		CrmContactsBusiness contactsBusiness = new CrmContactsBusiness();
		contactsBusiness.setBusinessId(business);
		contactsBusiness.setContactsId(contactsId);
		save(contactsBusiness);
//        }

	}

	/**
	 * @param contactsId ID
	 */
	@Override
	public void removeByContactsId(Integer contactsId) {
		LambdaQueryWrapper<CrmContactsBusiness> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CrmContactsBusiness::getContactsId, contactsId);
		remove(wrapper);
	}

	/**
	 * @param businessId ID
	 */
	@Override
	public void removeByBusinessId(Integer... businessId) {
		LambdaQueryWrapper<CrmContactsBusiness> wrapper = new LambdaQueryWrapper<>();
		wrapper.in(CrmContactsBusiness::getBusinessId, Arrays.asList(businessId));
		remove(wrapper);
	}
}
