package com.megazone.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.megazone.core.common.FieldEnum;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.entity.BasePage;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.service.AdminFileService;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.field.FieldService;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.core.utils.*;
import com.megazone.crm.common.ActionRecordUtil;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.constant.CrmCodeEnum;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.BO.CrmModelSaveBO;
import com.megazone.crm.entity.BO.CrmProductStatusBO;
import com.megazone.crm.entity.BO.CrmSearchBO;
import com.megazone.crm.entity.BO.CrmUpdateInformationBO;
import com.megazone.crm.entity.PO.*;
import com.megazone.crm.entity.VO.CrmFieldSortVO;
import com.megazone.crm.entity.VO.CrmInfoNumVO;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.mapper.CrmProductMapper;
import com.megazone.crm.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CrmProductServiceImpl extends BaseServiceImpl<CrmProductMapper, CrmProduct> implements ICrmProductService, CrmPageService {

	private static final String PRODUCT_STATUS_URL = "/crmProduct/updateStatus";

	@Autowired
	private ICrmProductDataService crmProductDataService;

	@Autowired
	private ICrmFieldService crmFieldService;

	@Autowired
	private ICrmActivityService crmActivityService;

	@Autowired
	private ICrmActionRecordService crmActionRecordService;

	@Autowired
	private ICrmProductCategoryService crmProductCategoryService;

	@Autowired
	private ActionRecordUtil actionRecordUtil;

	@Autowired
	private AdminFileService adminFileService;


	@Autowired
	private ICrmProductDetailImgService productDetailImgService;

	@Autowired
	private FieldService fieldService;

	@Override
	public List<CrmModelFiledVO> queryField(Integer id) {
		return queryField(id, false);
	}

	private List<CrmModelFiledVO> queryField(Integer id, boolean appendInformation) {
		CrmModel crmModel = queryById(id);
		crmModel.setLabel(getLabel().getType());
		List<CrmModelFiledVO> crmModelFiledVoS = crmFieldService.queryField(crmModel);
		for (CrmModelFiledVO crmModelFiledVO : crmModelFiledVoS) {
			if ("categoryId".equals(crmModelFiledVO.getFieldName())) {
				List<Integer> list = crmProductCategoryService.queryId(null, (Integer) crmModelFiledVO.getValue());
				if (CollUtil.isNotEmpty(list)) {
					crmModelFiledVO.setValue(list);
				} else {
					crmModelFiledVO.setValue(null);
				}
			}
		}

		int authLevel = 3;
		Long userId = UserUtil.getUserId();
		String key = userId.toString();
		List<String> noAuthMenuUrls = BaseUtil.getRedis().get(key);
		if (noAuthMenuUrls != null && noAuthMenuUrls.contains(PRODUCT_STATUS_URL)) {
			authLevel = 2;
		}
		List<Object> statusList = new ArrayList<>();
		statusList.add(new JSONObject().fluentPut("name", "Listed").fluentPut("value", 1));
		statusList.add(new JSONObject().fluentPut("name", "Unavailable").fluentPut("value", 0));
		crmModelFiledVoS.add(new CrmModelFiledVO("status", FieldEnum.SELECT, "Availability", 1).setIsNull(1).setSetting(statusList).setValue(crmModel.get("status")).setAuthLevel(authLevel));
		if (appendInformation) {
			List<CrmModelFiledVO> modelFiledVOS = appendInformation(crmModel);
			crmModelFiledVoS.addAll(modelFiledVOS);
		}
		return crmModelFiledVoS;
	}

	@Override
	public List<List<CrmModelFiledVO>> queryFormPositionField(Integer id) {
		CrmModel crmModel = queryById(id);
		crmModel.setLabel(getLabel().getType());
		List<List<CrmModelFiledVO>> crmModelFiledVoS = crmFieldService.queryFormPositionFieldVO(crmModel);
		for (List<CrmModelFiledVO> filedVOList : crmModelFiledVoS) {
			for (CrmModelFiledVO crmModelFiledVO : filedVOList) {
				if ("categoryId".equals(crmModelFiledVO.getFieldName())) {
					List<Integer> list = crmProductCategoryService.queryId(null, (Integer) crmModelFiledVO.getValue());
					if (CollUtil.isNotEmpty(list)) {
						crmModelFiledVO.setValue(list);
					} else {
						crmModelFiledVO.setValue(null);
					}
				}
			}
		}

		int authLevel = 3;
		Long userId = UserUtil.getUserId();
		String key = userId.toString();
		List<String> noAuthMenuUrls = BaseUtil.getRedis().get(key);
		if (noAuthMenuUrls != null && noAuthMenuUrls.contains(PRODUCT_STATUS_URL)) {
			authLevel = 2;
		}
		List<Object> statusList = new ArrayList<>();
		statusList.add(new JSONObject().fluentPut("name", "Listed").fluentPut("value", 1));
		statusList.add(new JSONObject().fluentPut("name", "Unavailable").fluentPut("value", 0));
		CrmModelFiledVO crmModelFiledVO = new CrmModelFiledVO("status", FieldEnum.SELECT, "Availability", 1).setIsNull(1).setSetting(statusList).setValue(crmModel.get("status")).setAuthLevel(authLevel);
		crmModelFiledVO.setStylePercent(50);
		crmModelFiledVoS.add(ListUtil.toList(crmModelFiledVO));
		return crmModelFiledVoS;
	}

	/**
	 * Paging query
	 *
	 * @param search search added
	 * @return data
	 */
	@Override
	public BasePage<Map<String, Object>> queryPageList(CrmSearchBO search) {
		BasePage<Map<String, Object>> basePage = queryList(search, false);
		basePage.getList().forEach(map -> {
			String status = map.get("status").toString();
			map.put("status", Objects.equals("1", status) ? "Listed" : "Unlisted");
		});
		return basePage;
	}

	@Override
	public List<SimpleCrmEntity> querySimpleEntity(List<Integer> ids) {
		if (ids.size() == 0) {
			return new ArrayList<>();
		}
		List<CrmProduct> list = lambdaQuery().select(CrmProduct::getProductId, CrmProduct::getName).in(CrmProduct::getProductId, ids).list();
		return list.stream().map(crmProduct -> {
			SimpleCrmEntity simpleCrmEntity = new SimpleCrmEntity();
			simpleCrmEntity.setId(crmProduct.getProductId());
			simpleCrmEntity.setName(crmProduct.getName());
			return simpleCrmEntity;
		}).collect(Collectors.toList());
	}

	/**
	 * Query field configuration
	 *
	 * @param id primary key ID
	 * @return data
	 */
	@Override
	public CrmModel queryById(Integer id) {
		CrmModel crmModel;
		if (id != null) {
			Integer count = lambdaQuery().eq(CrmProduct::getProductId, id).ne(CrmProduct::getStatus, 3).count();
			if (count == 0) {
				throw new CrmException(CrmCodeEnum.CRM_DATE_REMOVE_ERROR);
			}
			crmModel = getBaseMapper().queryById(id, UserUtil.getUserId());
			crmModel.setLabel(CrmEnum.PRODUCT.getType());
			crmModel.setOwnerUserName(UserCacheUtil.getUserName(crmModel.getOwnerUserId()));
			crmProductDataService.setDataByBatchId(crmModel);
			List<String> stringList = ApplicationContextHolder.getBean(ICrmRoleFieldService.class).queryNoAuthField(crmModel.getLabel());
			stringList.forEach(crmModel::remove);
			Optional<CrmProductDetailImg> detailImgOpt = productDetailImgService.lambdaQuery().eq(CrmProductDetailImg::getProductId, id).oneOpt();
			if (detailImgOpt.isPresent()) {
				CrmProductDetailImg detailImg = detailImgOpt.get();
				if (detailImg.getMainFileIds() != null) {
					List<FileEntity> mainFileList = adminFileService.queryByIds(TagUtil.toLongSet(detailImg.getMainFileIds())).getData();
					crmModel.put("mainFileList", mainFileList);
				} else {
					crmModel.put("mainFileList", new ArrayList<>());
				}
				if (detailImg.getDetailFileIds() != null) {
					List<FileEntity> detailFileList = adminFileService.queryByIds(TagUtil.toLongSet(detailImg.getDetailFileIds())).getData();
					crmModel.put("detailFileList", detailFileList);
				} else {
					crmModel.put("detailFileList", new ArrayList<>());
				}
			} else {
				crmModel.put("mainFileList", new ArrayList<>());
				crmModel.put("detailFileList", new ArrayList<>());
			}
		} else {
			crmModel = new CrmModel(CrmEnum.PRODUCT.getType());
		}
		return crmModel;
	}

	/**
	 * Save or add information
	 *
	 * @param crmModel model
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addOrUpdate(CrmModelSaveBO crmModel, boolean isExcel) {
		CrmProduct crmProduct = BeanUtil.copyProperties(crmModel.getEntity(), CrmProduct.class);
		String batchId = StrUtil.isNotEmpty(crmProduct.getBatchId()) ? crmProduct.getBatchId() : IdUtil.simpleUUID();
		actionRecordUtil.updateRecord(crmModel.getField(), Dict.create().set("batchId", batchId).set("dataTableName", "wk_crm_product_data"));
		crmProductDataService.saveData(crmModel.getField(), batchId);
		if (crmProduct.getProductId() == null) {
			crmProduct.setCreateUserId(UserUtil.getUserId());
			crmProduct.setCreateTime(DateUtil.date());
			crmProduct.setUpdateTime(DateUtil.date());
			if (crmProduct.getOwnerUserId() == null) {
				crmProduct.setOwnerUserId(UserUtil.getUserId());
			}
			crmProduct.setBatchId(batchId);
			save(crmProduct);
			actionRecordUtil.addRecord(crmProduct.getProductId(), CrmEnum.PRODUCT, crmProduct.getName());
		} else {
			actionRecordUtil.updateRecord(BeanUtil.beanToMap(getById(crmProduct.getProductId())), BeanUtil.beanToMap(crmProduct), CrmEnum.PRODUCT, crmProduct.getName(), crmProduct.getProductId());
			crmProduct.setUpdateTime(DateUtil.date());
			updateById(crmProduct);
			crmProduct = getById(crmProduct.getProductId());
		}
		Optional<CrmProductDetailImg> detailImgOpt = productDetailImgService.lambdaQuery().eq(CrmProductDetailImg::getProductId, crmProduct.getProductId()).oneOpt();
		if (detailImgOpt.isPresent()) {
			CrmProductDetailImg crmProductDetailImg = detailImgOpt.get();
			crmProductDetailImg.setDetailFileIds((String) crmModel.getEntity().get("detailFileIds"));
			crmProductDetailImg.setMainFileIds((String) crmModel.getEntity().get("mainFileIds"));
			productDetailImgService.updateById(crmProductDetailImg);
		} else {
			CrmProductDetailImg crmProductDetailImg = new CrmProductDetailImg();
			crmProductDetailImg.setProductId(crmProduct.getProductId());
			crmProductDetailImg.setDetailFileIds((String) crmModel.getEntity().get("detailFileIds"));
			crmProductDetailImg.setMainFileIds((String) crmModel.getEntity().get("mainFileIds"));
			productDetailImgService.save(crmProductDetailImg);
		}
		crmModel.setEntity(BeanUtil.beanToMap(crmProduct));
		savePage(crmModel, crmProduct.getProductId(), isExcel);
	}

	@Override
	public void setOtherField(Map<String, Object> map) {
		String createUserName = UserCacheUtil.getUserName((Long) map.get("createUserId"));
		map.put("createUserName", createUserName);
		CrmProductCategory productCategory = crmProductCategoryService.getById((Serializable) map.get("categoryId"));
		if (productCategory != null) {
			map.put("categoryName", productCategory.getName());
		} else {
			map.put("categoryName", "");
		}
		String ownerUserName = UserCacheUtil.getUserName((Long) map.get("ownerUserId"));
		map.put("ownerUserName", ownerUserName);
	}


	/**
	 * delete data
	 *
	 * @param ids ids
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteByIds(List<Integer> ids) {
		LambdaQueryWrapper<CrmProduct> wrapper = new LambdaQueryWrapper<>();
		wrapper.select(CrmProduct::getBatchId);
		wrapper.in(CrmProduct::getProductId, ids);
		List<String> batchIdList = listObjs(wrapper, Object::toString);
		//delete field operation record
		crmActionRecordService.deleteActionRecord(CrmEnum.PRODUCT, ids);
		if (CollUtil.isNotEmpty(batchIdList)) {
			// delete custom fields
			//TODO is not deleted, the product unit is a custom field, and the associated product has no unit after deletion
// crmProductDataService.deleteByBatchId(batchIdList);
		}
		LambdaUpdateWrapper<CrmProduct> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.set(CrmProduct::getStatus, 3);
		updateWrapper.in(CrmProduct::getProductId, ids);
		update(updateWrapper);
		//todo deletes the file and does not process it for now
		// delete es data
		deletePage(ids);
	}

	/**
	 * Modify the person in charge
	 *
	 * @param ids            list of ids
	 * @param newOwnerUserId new owner ID
	 */
	@Override
	public void changeOwnerUser(List<Integer> ids, Long newOwnerUserId) {
		LambdaUpdateWrapper<CrmProduct> wrapper = new LambdaUpdateWrapper<>();
		wrapper.in(CrmProduct::getProductId, ids);
		wrapper.set(CrmProduct::getOwnerUserId, newOwnerUserId);
		update(wrapper);
		for (Integer id : ids) {
			actionRecordUtil.addConversionRecord(id, CrmEnum.PRODUCT, newOwnerUserId, getById(id).getName());
		}
		//modify es
		String ownerUserName = UserCacheUtil.getUserName(newOwnerUserId);
		Map<String, Object> map = new HashMap<>();
		map.put("ownerUserId", newOwnerUserId);
		map.put("ownerUserName", ownerUserName);
		updateField(map, ids);
	}

	/**
	 * Download import template
	 *
	 * @param response product id
	 * @throws IOException exceptionon
	 */
	@Override
	public void downloadExcel(HttpServletResponse response) throws IOException {
		List<CrmModelFiledVO> crmModelFiledList = queryField(null);
		int k = 0;
		for (int i = 0; i < crmModelFiledList.size(); i++) {
			CrmModelFiledVO modelFiledVO = crmModelFiledList.get(i);
			if (modelFiledVO.getFieldName().equals("name")) {
				k = i;
				continue;
			}
			if ("categoryId".equals(modelFiledVO.getFieldName())) {
				modelFiledVO.setSetting(crmProductCategoryService.queryListName());
			}
		}
		crmModelFiledList.add(k + 1, new CrmModelFiledVO("ownerUserId", FieldEnum.TEXT, "responsible person", 1).setIsNull(1));
		ExcelParseUtil.importExcel(new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "Product";
			}
		}, crmModelFiledList, response, "crm");
	}

	/**
	 * export all
	 *
	 * @param response resp
	 * @param search   search object
	 */
	@Override
	public void exportExcel(HttpServletResponse response, CrmSearchBO search) {
		List<Map<String, Object>> dataList = queryList(search, true).getList();
		List<CrmFieldSortVO> headList = crmFieldService.queryListHead(getLabel().getType());
		ExcelParseUtil.exportExcel(dataList, new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {
				for (String fieldName : headMap.keySet()) {
					record.put(fieldName, ActionRecordUtil.parseValue(record.get(fieldName), headMap.get(fieldName), false));
				}
			}

			@Override
			public String getExcelName() {
				return "Product";
			}
		}, headList, response);
	}

	/**
	 * Modify product status
	 *
	 * @param productStatus status
	 */
	@Override
	public void updateStatus(CrmProductStatusBO productStatus) {
		Integer status = Objects.equals(0, productStatus.getStatus()) ? 0 : 1;
		LambdaUpdateWrapper<CrmProduct> wrapper = new LambdaUpdateWrapper<>();
		wrapper.set(CrmProduct::getStatus, status);
		wrapper.in(CrmProduct::getProductId, productStatus.getIds());
		update(wrapper);
		updateField("status", status, productStatus.getIds());
	}

	@Override
	public List<CrmModelFiledVO> information(Integer productId) {
		return queryField(productId, true);
	}

	/**
	 * Query the number of files
	 *
	 * @param productId id
	 * @return data
	 */
	@Override
	public CrmInfoNumVO num(Integer productId) {
		CrmProduct crmProduct = getById(productId);
		AdminFileService fileService = ApplicationContextHolder.getBean(AdminFileService.class);
		List<CrmField> crmFields = crmFieldService.queryFileField();
		List<String> batchIdList = new ArrayList<>();
		if (crmFields.size() > 0) {
			LambdaQueryWrapper<CrmProductData> wrapper = new LambdaQueryWrapper<>();
			wrapper.select(CrmProductData::getValue);
			wrapper.eq(CrmProductData::getBatchId, crmProduct.getBatchId());
			wrapper.in(CrmProductData::getFieldId, crmFields.stream().map(CrmField::getFieldId).collect(Collectors.toList()));
			batchIdList.addAll(crmProductDataService.listObjs(wrapper, Object::toString));
		}
		batchIdList.add(crmProduct.getBatchId());
		batchIdList.addAll(crmActivityService.queryFileBatchId(crmProduct.getProductId(), getLabel().getType()));
		CrmInfoNumVO infoNumVO = new CrmInfoNumVO();
		infoNumVO.setFileCount(fileService.queryNum(batchIdList).getData());
		return infoNumVO;
	}

	/**
	 * Query file list
	 *
	 * @param productId id
	 * @return file
	 */
	@Override
	public List<FileEntity> queryFileList(Integer productId) {
		List<FileEntity> fileEntityList = new ArrayList<>();
		CrmProduct crmProduct = getById(productId);
		AdminFileService fileService = ApplicationContextHolder.getBean(AdminFileService.class);
		fileService.queryFileList(crmProduct.getBatchId()).getData().forEach(fileEntity -> {
			fileEntity.setSource("Attachment upload");
			fileEntity.setReadOnly(0);
			fileEntityList.add(fileEntity);
		});
		List<CrmField> crmFields = crmFieldService.queryFileField();
		if (crmFields.size() > 0) {
			LambdaQueryWrapper<CrmProductData> wrapper = new LambdaQueryWrapper<>();
			wrapper.select(CrmProductData::getValue);
			wrapper.eq(CrmProductData::getBatchId, crmProduct.getBatchId());
			wrapper.in(CrmProductData::getFieldId, crmFields.stream().map(CrmField::getFieldId).collect(Collectors.toList()));
			List<FileEntity> data = fileService.queryFileList(crmProductDataService.listObjs(wrapper, Object::toString)).getData();
			data.forEach(fileEntity -> {
				fileEntity.setSource("Product Details");
				fileEntity.setReadOnly(1);
				fileEntityList.add(fileEntity);
			});
		}
		return fileEntityList;
	}


	/**
	 * Query product object
	 *
	 * @return list
	 */
	@Override
	public List<SimpleCrmEntity> querySimpleEntity() {
		List<CrmProduct> list = lambdaQuery().ne(CrmProduct::getStatus, 3).list();
		return list.stream().map(crmProduct -> {
			SimpleCrmEntity simpleCrmEntity = new SimpleCrmEntity();
			simpleCrmEntity.setId(crmProduct.getProductId());
			simpleCrmEntity.setName(crmProduct.getName());
			return simpleCrmEntity;
		}).collect(Collectors.toList());
	}

	/**
	 * Search field with large search box
	 *
	 * @return fields
	 */
	@Override
	public String[] appendSearch() {
		return new String[]{"name"};
	}

	/**
	 * Get crm list type
	 *
	 * @return data
	 */
	@Override
	public CrmEnum getLabel() {
		return CrmEnum.PRODUCT;
	}

	/**
	 * Query all fields
	 *
	 * @return data
	 */
	@Override
	public List<CrmModelFiledVO> queryDefaultField() {
		List<CrmModelFiledVO> filedList = crmFieldService.queryField(getLabel().getType());
		filedList.add(new CrmModelFiledVO("updateTime", FieldEnum.DATETIME, 1));
		filedList.add(new CrmModelFiledVO("createTime", FieldEnum.DATETIME, 1));
		filedList.add(new CrmModelFiledVO("createUserId", FieldEnum.USER, 1));
		filedList.add(new CrmModelFiledVO("status", FieldEnum.TEXT, 1));
		return filedList;
	}


	@Override
	public void updateInformation(CrmUpdateInformationBO updateInformationBO) {
		String batchId = updateInformationBO.getBatchId();
		Integer productId = updateInformationBO.getId();
		updateInformationBO.getList().forEach(record -> {
			CrmProduct oldProduct = getById(updateInformationBO.getId());
			uniqueFieldIsAbnormal(record.getString("name"), record.getInteger("fieldId"), record.getString("value"), batchId);
			Map<String, Object> oldProductMap = BeanUtil.beanToMap(oldProduct);
			if (record.getInteger("fieldType") == 1) {
				Map<String, Object> crmProductMap = new HashMap<>(oldProductMap);
				crmProductMap.put(record.getString("fieldName"), record.get("value"));
				CrmProduct crmProduct = BeanUtil.mapToBean(crmProductMap, CrmProduct.class, true);
				actionRecordUtil.updateRecord(oldProductMap, crmProductMap, CrmEnum.PRODUCT, crmProduct.getName(), crmProduct.getProductId());
				update().set(StrUtil.toUnderlineCase(record.getString("fieldName")), record.get("value")).eq("product_id", updateInformationBO.getId()).update();
			} else if (record.getInteger("fieldType") == 0 || record.getInteger("fieldType") == 2) {
				CrmProductData productData = crmProductDataService.lambdaQuery().select(CrmProductData::getValue, CrmProductData::getId).eq(CrmProductData::getFieldId, record.getInteger("fieldId"))
						.eq(CrmProductData::getBatchId, batchId).one();
				String value = productData != null ? productData.getValue() : null;
				actionRecordUtil.publicContentRecord(CrmEnum.PRODUCT, BehaviorEnum.UPDATE, productId, oldProduct.getName(), record, value);
				String newValue = fieldService.convertObjectValueToString(record.getInteger("type"), record.get("value"), record.getString("value"));
				CrmProductData crmProductData = new CrmProductData();
				crmProductData.setId(productData != null ? productData.getId() : null);
				crmProductData.setFieldId(record.getInteger("fieldId"));
				crmProductData.setName(record.getString("fieldName"));
				crmProductData.setValue(newValue);
				crmProductData.setCreateTime(new Date());
				crmProductData.setBatchId(batchId);
				crmProductDataService.saveOrUpdate(crmProductData);
			}
			updateField(record, productId);
		});
		this.lambdaUpdate().set(CrmProduct::getUpdateTime, new Date()).eq(CrmProduct::getProductId, productId).update();
	}
}
