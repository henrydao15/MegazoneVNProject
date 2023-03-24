package com.megazone.crm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.crm.entity.BO.CrmProductCategoryBO;
import com.megazone.crm.entity.PO.CrmProductCategory;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
public interface ICrmProductCategoryService extends BaseService<CrmProductCategory> {
	/**
	 * @param id id
	 * @return data
	 */
	public CrmProductCategory queryById(Integer id);

	/**
	 * @param productCategory data
	 */
	public void saveAndUpdate(CrmProductCategory productCategory);

	/**
	 * @param id id
	 */
	public void deleteById(Integer id);

	/**
	 * @param type pid
	 * @return data
	 */
	public List<CrmProductCategoryBO> queryList(String type);

	/**
	 * @return data
	 */
	public List<Object> queryListName();

	/**
	 * @param name name
	 * @return data
	 */
	public CrmProductCategory queryFirstCategoryByName(String name);

	String getProductCategoryName(int categoryId);

	public List<Integer> queryId(List<Integer> list, Integer categoryId);
}
