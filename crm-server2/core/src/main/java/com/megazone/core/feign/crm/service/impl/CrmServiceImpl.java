package com.megazone.core.feign.crm.service.impl;

import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.crm.entity.CrmSearchBO;
import com.megazone.core.feign.crm.entity.ExamineField;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.feign.crm.service.CrmService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
public class CrmServiceImpl implements CrmService {
	/**
	 * @param ids ids
	 * @return entity
	 */
	@Override
	public Result<List<SimpleCrmEntity>> queryCustomerInfo(Collection ids) {
		return Result.ok(new ArrayList<>());
	}

	@Override
	public Result<String> queryCustomerName(Integer customerId) {
		return Result.ok(new String());
	}

	@Override
	public Result<List<SimpleCrmEntity>> queryLeadsInfo(Collection ids) {
		return Result.ok(new ArrayList<>());
	}

	@Override
	public Result<List<SimpleCrmEntity>> queryInvoiceInfo(Collection ids) {
		return Result.ok(new ArrayList<>());
	}

	@Override
	public Result<List<SimpleCrmEntity>> queryReceivablesInfo(Collection ids) {
		return Result.ok(new ArrayList<>());
	}

	@Override
	public Result<List<SimpleCrmEntity>> queryReturnVisitInfo(Collection ids) {
		return Result.ok(new ArrayList<>());
	}

	/**
	 * @param name
	 * @return entity
	 */
	@Override
	public Result<List<SimpleCrmEntity>> queryByNameCustomerInfo(String name) {
		return Result.ok(new ArrayList<>());
	}

	/**
	 * @param name
	 * @return entity
	 */
	@Override
	public Result<List<SimpleCrmEntity>> queryNameCustomerInfo(String name) {
		return Result.ok(new ArrayList<>());
	}

	/**
	 * @param ids ids
	 * @return entity
	 */
	@Override
	public Result<List<SimpleCrmEntity>> queryContactsInfo(Collection ids) {
		return Result.ok(new ArrayList<>());
	}

	/**
	 * @param ids ids
	 * @return entity
	 */
	@Override
	public Result<List<SimpleCrmEntity>> queryBusinessInfo(Collection ids) {
		return Result.ok(new ArrayList<>());
	}

	/**
	 * @param ids ids
	 * @return entity
	 */
	@Override
	public Result<List<SimpleCrmEntity>> queryContractInfo(Collection ids) {
		return Result.ok(new ArrayList<>());
	}

	@Override
	public Result<List<SimpleCrmEntity>> queryProductInfo() {
		return Result.ok(new ArrayList<>());
	}

	@Override
	public Result addActivity(Integer type, Integer activityType, Integer activityTypeId) {
		return Result.ok();
	}

	@Override
	public Result batchUpdateEsData(String id, String name) {
		return Result.ok();
	}

	@Override
	public Result updateReceivablesPlan() {
		return Result.ok();
	}

	@Override
	public Result putInInternational() {
		return Result.ok();
	}

	@Override
	public Result<List> queryPoolNameListByAuth() {
		return Result.ok(new ArrayList());
	}

	@Override
	public Result<List<ExamineField>> queryExamineField(Integer label) {
		return Result.ok(new ArrayList<>());
	}

	@Override
	public Result<BasePage<Map<String, Object>>> queryCustomerPageList(CrmSearchBO search) {
		return Result.ok(new BasePage<>());
	}
}
