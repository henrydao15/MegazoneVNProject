package com.megazone.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.megazone.core.exception.CrmException;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.utils.RecursionUtil;
import com.megazone.crm.common.ElasticUtil;
import com.megazone.crm.constant.CrmCodeEnum;
import com.megazone.crm.entity.BO.CrmProductCategoryBO;
import com.megazone.crm.entity.PO.CrmProduct;
import com.megazone.crm.entity.PO.CrmProductCategory;
import com.megazone.crm.mapper.CrmProductCategoryMapper;
import com.megazone.crm.service.ICrmProductCategoryService;
import com.megazone.crm.service.ICrmProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CrmProductCategoryServiceImpl extends BaseServiceImpl<CrmProductCategoryMapper, CrmProductCategory> implements ICrmProductCategoryService {

	@Autowired
	private ICrmProductService crmProductService;
	@Autowired
	private ElasticsearchRestTemplate elasticsearchRestTemplate;

	@Override
	public CrmProductCategory queryById(Integer id) {
		return getById(id);
	}

	@Override
	public void saveAndUpdate(CrmProductCategory productCategory) {
		if (productCategory.getCategoryId() != null) {
			updateById(productCategory);
			ElasticUtil.batchUpdateEsData(elasticsearchRestTemplate.getClient(), "product", productCategory.getCategoryId().toString(), productCategory.getName());
		} else {
			save(productCategory);
		}
	}

	@Override
	public void deleteById(Integer id) {
		Integer num = crmProductService.lambdaQuery().eq(CrmProduct::getCategoryId, id).ne(CrmProduct::getStatus, 3).count();
		if (num > 0) {
			throw new CrmException(CrmCodeEnum.CRM_PRODUCT_CATEGORY_ERROR);
		}
		num = lambdaQuery().eq(CrmProductCategory::getPid, id).count();
		if (num > 0) {
			throw new CrmException(CrmCodeEnum.CRM_PRODUCT_CATEGORY_CHILD_ERROR);
		}
		removeById(id);
	}

	@Override
	public List<CrmProductCategoryBO> queryList(String type) {
		List<CrmProductCategory> categoryList = list();
		if (StrUtil.isEmpty(type)) {
			return categoryList.stream().map(obj -> BeanUtil.copyProperties(obj, CrmProductCategoryBO.class)).collect(Collectors.toList());
		}
		return RecursionUtil.getChildListTree(categoryList, "pid", 0, "categoryId", "children", CrmProductCategoryBO.class);
	}

	@Override
	public List<Object> queryListName() {
		QueryWrapper<CrmProductCategory> wrapper = new QueryWrapper<>();
		wrapper.select("name");
		return listObjs(wrapper);
	}

	@Override
	public String getProductCategoryName(int categoryId) {
		return lambdaQuery().select(CrmProductCategory::getName).eq(CrmProductCategory::getCategoryId, categoryId).oneOpt()
				.map(CrmProductCategory::getName).orElse("");
	}

	@Override
	public CrmProductCategory queryFirstCategoryByName(String name) {
		QueryWrapper<CrmProductCategory> wrapper = new QueryWrapper<>();
		wrapper.select("category_id");
		wrapper.eq("name", name);
		return getOne(wrapper);
	}

	@Override
	public List<Integer> queryId(List<Integer> list, Integer categoryId) {
		if (categoryId == null) {
			return new ArrayList<>();
		}
		if (list == null) {
			list = new ArrayList<>();
		}
		list.add(categoryId);
		CrmProductCategory productCategory = getById(categoryId);
		if (productCategory != null && productCategory.getPid() != 0) {
			queryId(list, productCategory.getPid());
		} else {
			Collections.reverse(list);
		}

		return list;
	}
}
