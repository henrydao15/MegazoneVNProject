package com.megazone.crm.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.service.AdminFileService;
import com.megazone.core.feign.admin.service.AdminMessageService;
import com.megazone.core.feign.examine.service.ExamineService;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.servlet.BaseService;
import com.megazone.core.utils.BaseUtil;
import com.megazone.core.utils.UserUtil;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.PO.*;
import com.megazone.crm.service.*;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CrmCommonServiceImpl implements ICrmCommonService {

	private static final String INIT_AUTH_URL = "/adminConfig/moduleInitData";
	@Autowired
	private ICrmBusinessService crmBusinessService;
	@Autowired
	private ICrmContactsService crmContactsService;
	@Autowired
	private ICrmContractService crmContractService;
	@Autowired
	private ICrmCustomerService crmCustomerService;
	@Autowired
	private ICrmFieldSortService crmFieldSortService;
	@Autowired
	private ICrmInvoiceService crmInvoiceService;
	@Autowired
	private ICrmLeadsService crmLeadsService;
	@Autowired
	private ICrmOwnerRecordService crmOwnerRecordService;
	@Autowired
	private ICrmPrintRecordService crmPrintRecordService;
	@Autowired
	private ICrmProductService crmProductService;
	@Autowired
	private ICrmReceivablesService crmReceivablesService;
	@Autowired
	private ICrmReturnVisitService crmReturnVisitService;
	@Autowired
	private ElasticsearchRestTemplate elasticsearchRestTemplate;
	@Autowired
	private AdminFileService adminFileService;
	@Autowired
	private AdminMessageService adminMessageService;
	@Autowired
	private ExamineService examineService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean initCrmData() {
		if (!UserUtil.isAdmin()) {
			if (this.verifyInitAuth()) {
				throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
			}
		}
		log.info("！");
		ApplicationContextHolder.getBean(ICrmActivityService.class).lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmActivityRelationService.class).lambdaUpdate().remove();
		this.deleteFile(crmBusinessService, CrmBusiness::getBatchId, CrmBusiness::getBatchId);
		crmBusinessService.lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmBusinessChangeService.class).lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmBusinessDataService.class).lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmBusinessProductService.class).lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmBusinessUserStarService.class).lambdaUpdate().remove();

		this.deleteFile(crmContactsService, CrmContacts::getBatchId, CrmContacts::getBatchId);
		crmContactsService.lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmContactsDataService.class).lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmContactsBusinessService.class).lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmContactsUserStarService.class).lambdaUpdate().remove();

		this.deleteFile(crmContractService, CrmContract::getBatchId, CrmContract::getBatchId);
		crmContractService.lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmContractDataService.class).lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmContractProductService.class).lambdaUpdate().remove();

		this.deleteFile(crmCustomerService, CrmCustomer::getBatchId, CrmCustomer::getBatchId);
		crmCustomerService.lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmCustomerDataService.class).lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmCustomerSettingService.class).lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmCustomerSettingUserService.class).lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmCustomerUserStarService.class).lambdaUpdate().remove();

		crmFieldSortService.lambdaUpdate().remove();

		crmInvoiceService.lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmInvoiceDataService.class).lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmInvoiceInfoService.class).lambdaUpdate().remove();

		this.deleteFile(crmLeadsService, CrmLeads::getBatchId, CrmLeads::getBatchId);
		crmLeadsService.lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmLeadsDataService.class).lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmLeadsUserStarService.class).lambdaUpdate().remove();


		crmOwnerRecordService.lambdaUpdate().remove();
		crmPrintRecordService.lambdaUpdate().remove();

		this.deleteFile(crmProductService, CrmProduct::getBatchId, CrmProduct::getBatchId);
		crmProductService.lambdaUpdate().remove();

		ApplicationContextHolder.getBean(ICrmProductDataService.class).lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmProductDetailImgService.class).lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmProductUserService.class).lambdaUpdate().remove();

		crmReceivablesService.lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmReceivablesDataService.class).lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmReceivablesPlanService.class).lambdaUpdate().remove();

		crmReturnVisitService.lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmReturnVisitDataService.class).lambdaUpdate().remove();

		ApplicationContextHolder.getBean(ICrmCustomerPoolRelationService.class).lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmCustomerPoolFieldSettingService.class).lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmCustomerPoolFieldSortService.class).lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmCustomerPoolFieldStyleService.class).lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmCustomerPoolRuleService.class).lambdaUpdate().remove();

		ApplicationContextHolder.getBean(ICrmMarketingService.class).lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmMarketingInfoService.class).lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmMarketingFieldService.class).lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmMarketingFormService.class).lambdaUpdate().remove();

		adminMessageService.deleteByLabel(6);

		ApplicationContextHolder.getBean(ICrmActionRecordService.class).lambdaUpdate().remove();
		ApplicationContextHolder.getBean(ICrmBackLogDealService.class).lambdaUpdate().remove();

		examineService.deleteExamineRecordAndLog(1);
		examineService.deleteExamineRecordAndLog(2);
		examineService.deleteExamineRecordAndLog(3);
		log.info("。es！");
		return this.deleteByQuery(elasticsearchRestTemplate.getClient()) >= 0;
	}

	/**
	 * @param baseService
	 * @param resultColumn
	 * @param queryColumn
	 * @param mapper
	 * @return void
	 * @date 2020/11/20 15:41
	 **/
	private <T> void deleteFile(BaseService<T> baseService, SFunction<T, String> resultColumn, Function<T, String> mapper) {
		LambdaQueryWrapper<T> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.select(resultColumn);
		List<T> list = baseService.list(lambdaQueryWrapper);
		if (CollUtil.isNotEmpty(list)) {
			List<String> batchIds = list.stream().map(mapper).collect(Collectors.toList());
			batchIds = batchIds.stream().distinct().collect(Collectors.toList());
			adminFileService.delete(batchIds);
		}
	}

	/**
	 * @param client
	 * @return long
	 * @date 2020/11/13 13:35
	 **/
	private long deleteByQuery(RestHighLevelClient client) {
		List<String> indexList = new ArrayList<>();
		for (CrmEnum value : CrmEnum.values()) {
			if (!value.equals(CrmEnum.NULL) && !value.equals(CrmEnum.CUSTOMER_POOL) && !value.equals(CrmEnum.MARKETING)) {
				indexList.add(value.getIndex());
			}
		}

		DeleteByQueryRequest request = new DeleteByQueryRequest(indexList.toArray(new String[0]));
		//
		request.setConflicts("proceed");
		request.setSize(1000);
		// ，，
		request.setQuery(QueryBuilders.matchAllQuery());
		//
		request.setRefresh(true);
		try {
			long size = 1000, updateNum = 0;
			while (size > 0) {
				BulkByScrollResponse response = client.deleteByQuery(request, RequestOptions.DEFAULT);
				size = response.getStatus().getUpdated();
				updateNum += size;
			}
			log.info("es。{}！", updateNum);
			return updateNum;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * @param
	 * @return boolean
	 * @date 2020/11/23 10:35
	 **/
	private boolean verifyInitAuth() {
		boolean isNoAuth = false;
		Long userId = UserUtil.getUserId();
		String key = userId.toString();
		List<String> noAuthMenuUrls = BaseUtil.getRedis().get(key);
		if (noAuthMenuUrls != null && noAuthMenuUrls.contains(INIT_AUTH_URL)) {
			isNoAuth = true;
		}
		return isNoAuth;
	}


}
