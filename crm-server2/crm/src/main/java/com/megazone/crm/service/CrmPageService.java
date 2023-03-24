package com.megazone.crm.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.megazone.core.common.*;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.UserInfo;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.core.feign.admin.service.AdminFileService;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.feign.examine.entity.ExamineRecordSaveBO;
import com.megazone.core.field.FieldService;
import com.megazone.core.redis.Redis;
import com.megazone.core.servlet.BaseService;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.core.utils.BaseUtil;
import com.megazone.core.utils.UserCacheUtil;
import com.megazone.core.utils.UserUtil;
import com.megazone.crm.common.AuthUtil;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.common.ElasticUtil;
import com.megazone.crm.constant.CrmAuthEnum;
import com.megazone.crm.constant.CrmCodeEnum;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.constant.CrmSceneEnum;
import com.megazone.crm.entity.BO.CrmCustomerPoolBO;
import com.megazone.crm.entity.BO.CrmFieldVerifyBO;
import com.megazone.crm.entity.BO.CrmModelSaveBO;
import com.megazone.crm.entity.BO.CrmSearchBO;
import com.megazone.crm.entity.PO.CrmField;
import com.megazone.crm.entity.PO.CrmRoleField;
import com.megazone.crm.entity.PO.CrmScene;
import com.megazone.crm.entity.PO.CrmTeamMembers;
import com.megazone.crm.entity.VO.CrmFieldSortVO;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import org.apache.commons.lang3.ArrayUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.megazone.core.servlet.ApplicationContextHolder.getBean;

public interface CrmPageService {

	Logger log = LoggerFactory.getLogger(CrmPageService.class);

	default BasePage<Map<String, Object>> queryList(CrmSearchBO crmSearchBO, boolean isExcel) {
		SearchRequest searchRequest = new SearchRequest(getIndex());
		searchRequest.types(getDocType());
		searchRequest.source(createSourceBuilder(crmSearchBO));
		try {
			SearchResponse searchResponse = getRestTemplate().getClient().search(searchRequest, RequestOptions.DEFAULT);
			List<Map<String, Object>> mapList = new ArrayList<>();
			List<CrmModelFiledVO> voList = queryDefaultField();
			SearchHit[] hits = searchResponse.getHits().getHits();
			if (crmSearchBO.getPage() >= 100) {
				if (hits.length > 0) {

					SearchHit searchHit = hits[hits.length - 1];
					Redis redis = BaseUtil.getRedis();
					String searchAfterKey = "es:search:" + UserUtil.getUserId().toString();
					if (crmSearchBO.getPage() == 100) {
						redis.del(searchAfterKey);
					}
					int page = redis.getLength(searchAfterKey).intValue();
					if (crmSearchBO.getPage() - 100 >= page) {
						redis.rpush(searchAfterKey, searchHit.getSortValues());
					}

					redis.expire(searchAfterKey, 3600);
				}
			}
			if (isExcel) {
				while (hits.length != 0 && hits.length % 10000 == 0) {
					hits = getAllData(searchRequest, hits);
				}
			}
			for (SearchHit hit : hits) {
				Map<String, Object> sourceAsMap = hit.getSourceAsMap();
				sourceAsMap.put(getLabel().getPrimaryKey(), Integer.valueOf(hit.getId()));
				mapList.add(parseMap(sourceAsMap, voList));
			}
			BasePage<Map<String, Object>> basePage = new BasePage<>();
			getBean(ICrmRoleFieldService.class).replaceMaskFieldValue(getLabel(), mapList, 1);
			basePage.setSize(crmSearchBO.getLimit());
			basePage.setList(mapList);
			basePage.setTotal(searchResponse.getHits().getTotalHits());
			basePage.setCurrent(crmSearchBO.getPage());
			return basePage;
		} catch (IOException e) {
			throw new CrmException(SystemCodeEnum.SYSTEM_ERROR);
		}
	}

	default SearchHit[] getAllData(SearchRequest searchRequest, SearchHit[] hits) {
		try {

			SearchHit searchHit = hits[hits.length - 1];
			searchRequest.source().searchAfter(searchHit.getSortValues());
			SearchResponse afterResult = getRestTemplate().getClient().search(searchRequest, RequestOptions.DEFAULT);
			SearchHit[] afterHits = afterResult.getHits().getHits();
			hits = ArrayUtils.addAll(hits, afterHits);
		} catch (IOException e) {
			throw new CrmException(SystemCodeEnum.SYSTEM_ERROR);
		}
		return hits;
	}

	default Map<String, Object> parseMap(Map<String, Object> objectMap, List<CrmModelFiledVO> fieldList) {
		fieldList.forEach(field -> {
			if (!objectMap.containsKey(field.getFieldName())) {
				objectMap.put(field.getFieldName(), "");
			}
			if (field.getFieldType() == 0 && field.getType().equals(FieldEnum.USER.getType())) {
				if (ObjectUtil.isNotEmpty(objectMap.get(field.getFieldName()))) {
					List<Long> ids = Convert.toList(Long.class, objectMap.get(field.getFieldName()));
					objectMap.put(field.getFieldName(), ids.stream().map(UserCacheUtil::getUserName).collect(Collectors.joining(Const.SEPARATOR)));
				} else {
					objectMap.put(field.getFieldName(), "");
				}
			}
			if (field.getFieldType() == 0 && field.getType().equals(FieldEnum.STRUCTURE.getType())) {
				if (ObjectUtil.isNotEmpty(objectMap.get(field.getFieldName()))) {
					List<Integer> ids = Convert.toList(Integer.class, objectMap.get(field.getFieldName()));
					objectMap.put(field.getFieldName(), ids.stream().map(UserCacheUtil::getDeptName).collect(Collectors.joining(",")));
				} else {
					objectMap.put(field.getFieldName(), "");
				}
			}
			if (field.getFieldType() == 0 && Arrays.asList(3, 8, 9, 11).contains(field.getType())) {
				Object value = objectMap.get(field.getFieldName());
				if (ObjectUtil.isNotEmpty(value)) {
					objectMap.put(field.getFieldName(), CollUtil.join(Convert.toList(String.class, value), ","));
				} else {
					objectMap.put(field.getFieldName(), "");
				}
			}
			if (getBean(FieldService.class).equalsByType(field.getType())) {
				Object value = objectMap.get(field.getFieldName());
				if (ObjectUtil.isNotEmpty(value)) {
					// TODO: 2021/1/29
					try {
						objectMap.put(field.getFieldName(), JSON.parse((String) value));
					} catch (Exception e) {
						objectMap.put(field.getFieldName(), value.toString());
					}
				} else {
					objectMap.put(field.getFieldName(), "");
				}
			}
		});
		return objectMap;
	}

	/**
	 * @return restTemplate
	 */
	default ElasticsearchRestTemplate getRestTemplate() {
		return getBean(ElasticsearchRestTemplate.class);
	}

	/**
	 * @return doc
	 */
	default String getDocType() {
		return "_doc";
	}


	/**
	 * @return fields
	 */
	public String[] appendSearch();

	/**
	 * @param crmSearchBO data
	 * @return data
	 */
	default SearchSourceBuilder createSourceBuilder(CrmSearchBO crmSearchBO) {
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

		sort(crmSearchBO, sourceBuilder);
		sourceBuilder.query(createQueryBuilder(crmSearchBO));
		return sourceBuilder;
	}

	/**
	 * @param crmSearchBO
	 * @return
	 */
	default BoolQueryBuilder createQueryBuilder(CrmSearchBO crmSearchBO) {
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		if (StrUtil.isNotEmpty(crmSearchBO.getSearch())) {
			BoolQueryBuilder searchBoolQuery = QueryBuilders.boolQuery();
			for (String search : appendSearch()) {
				searchBoolQuery.should(QueryBuilders.wildcardQuery(search, "*" + crmSearchBO.getSearch().trim() + "*"));
			}
			queryBuilder.filter(searchBoolQuery);
		}

		if (crmSearchBO.getSceneId() != null) {
			sceneQuery(crmSearchBO, queryBuilder);
		} else {
			if (getLabel().equals(CrmEnum.LEADS)) {
				queryBuilder.filter(QueryBuilders.termQuery("isTransform", 0));
			}
		}

		crmSearchBO.getSearchList().forEach(search -> {
			Dict searchTransferMap = getSearchTransferMap();
			if (searchTransferMap.containsKey(search.getName())) {
				search.setName(searchTransferMap.getStr(search.getName()));
			}
			if ("business_type".equals(search.getFormType())) {
				List<String> values = search.getValues();
				if (values.size() > 1) {
					Integer status = Integer.valueOf(values.get(1));
					if (status < 0) {
						queryBuilder.filter(QueryBuilders.termQuery("isEnd", Math.abs(status)));
					} else {
						queryBuilder.filter(QueryBuilders.termQuery(search.getName(), status));
						queryBuilder.filter(QueryBuilders.termQuery("isEnd", 0));
					}
				}
				queryBuilder.filter(QueryBuilders.termQuery("typeId", values.get(0)));
				return;
			}
			search(search, queryBuilder);
		});
		if (crmSearchBO.getPoolId() != null) {
			queryBuilder.filter(QueryBuilders.termQuery("poolId", crmSearchBO.getPoolId()));
		} else {
			queryBuilder.filter(QueryBuilders.existsQuery("ownerUserId"));
			setCrmDataAuth(queryBuilder);
		}
		if (queryBuilder.should().size() > 0) {
			queryBuilder.minimumShouldMatch(1);
		}
		return queryBuilder;
	}

	/**
	 * @param crmSearchBO  BO
	 * @param queryBuilder
	 */
	@SuppressWarnings("unchecked")
	default void sceneQuery(CrmSearchBO crmSearchBO, BoolQueryBuilder queryBuilder) {
		Long userId = UserUtil.getUserId();
		CrmScene crmScene = getBean(ICrmSceneService.class).getById(crmSearchBO.getSceneId());
		if (crmScene != null) {
			if (StrUtil.isNotEmpty(crmScene.getBydata())) {
				if (CrmSceneEnum.CHILD.getName().equals(crmScene.getBydata())) {
					List<Long> longList = getBean(AdminService.class).queryChildUserId(userId).getData();
					queryBuilder.filter(QueryBuilders.termsQuery("ownerUserId", longList));
				} else if (CrmSceneEnum.SELF.getName().equals(crmScene.getBydata())) {
					queryBuilder.filter(QueryBuilders.termQuery("ownerUserId", userId));
				} else if (CrmSceneEnum.STAR.getName().equals(crmScene.getBydata())) {
					BaseService baseService;
					switch (getLabel()) {
						case LEADS: {
							baseService = getBean(ICrmLeadsUserStarService.class);
							break;
						}
						case CUSTOMER: {
							baseService = getBean(ICrmCustomerUserStarService.class);
							break;
						}
						case CONTACTS: {
							baseService = getBean(ICrmContactsUserStarService.class);
							break;
						}
						case BUSINESS: {
							baseService = getBean(ICrmBusinessUserStarService.class);
							break;
						}
						default:
							return;
					}
					QueryWrapper queryWrapper = new QueryWrapper();
					queryWrapper.select(getLabel().getPrimaryKey(false));
					queryWrapper.eq("user_id", userId);
					List<Map<String, Object>> listMaps = baseService.listMaps(queryWrapper);
					if (listMaps.size() > 0) {
						queryBuilder.filter(QueryBuilders.idsQuery().addIds(listMaps.stream().map(map -> map.get(getLabel().getPrimaryKey()).toString()).toArray(String[]::new)));
					} else {
						queryBuilder.filter(QueryBuilders.idsQuery().addIds("0"));
					}
				}
				if (getLabel().equals(CrmEnum.LEADS)) {
					if (CrmSceneEnum.TRANSFORM.getName().equals(crmScene.getBydata())) {
						if (getLabel().equals(CrmEnum.LEADS)) {
							queryBuilder.filter(QueryBuilders.termQuery("isTransform", 1));
						}
					} else {
						queryBuilder.filter(QueryBuilders.termQuery("isTransform", 0));
					}
				}
			} else {
				try {
					ObjectMapper mapper = new ObjectMapper();
					crmSearchBO.getSearchList().addAll(mapper.readValue(crmScene.getData(), new TypeReference<List<CrmSearchBO.Search>>() {
					}));
					if (getLabel().equals(CrmEnum.LEADS)) {
						boolean isIdSearch = crmSearchBO.getSearchList().stream().anyMatch(search -> search.getSearchEnum().equals(CrmSearchBO.FieldSearchEnum.ID));
						if (!isIdSearch) {
							queryBuilder.filter(QueryBuilders.termQuery("isTransform", 0));
						}
					}
				} catch (Exception e) {
					log.error("json{}", crmScene.getData());
					getBean(ICrmSceneService.class).removeById(crmScene.getSceneId());
				}
			}
		}
	}

	default void search(CrmSearchBO.Search search, BoolQueryBuilder queryBuilder) {
		if (search.getSearchEnum() == CrmSearchBO.FieldSearchEnum.SCRIPT) {
			if (search.getScript() != null) {
				queryBuilder.filter(QueryBuilders.scriptQuery(search.getScript()));
			}
			return;
		}
		if (search.getSearchEnum() == CrmSearchBO.FieldSearchEnum.ID) {
			queryBuilder.filter(QueryBuilders.idsQuery().addIds(search.getValues().toArray(new String[0])));
			return;
		}
		String formType = search.getFormType();
		FieldEnum fieldEnum = FieldEnum.parse(formType);
		switch (fieldEnum) {
			case TEXTAREA:
				search.setName(search.getName() + ".keyword");
			case TEXT:
			case MOBILE:
			case EMAIL:
			case SELECT:
			case WEBSITE: {
				ElasticUtil.textSearch(search, queryBuilder);
				break;
			}
			case BOOLEAN_VALUE: {
				boolean value = Boolean.parseBoolean(search.getValues().get(0));
				value = (search.getSearchEnum() == CrmSearchBO.FieldSearchEnum.IS) == value;
				if (value) {
					queryBuilder.filter(QueryBuilders.termQuery(search.getName(), "1"));
				} else {
					BoolQueryBuilder builder = QueryBuilders.boolQuery();
					builder.should(QueryBuilders.termQuery(search.getName(), "0"));
					builder.should(QueryBuilders.termQuery(search.getName(), ""));
					builder.should(QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery(search.getName())));
					queryBuilder.filter(builder);
				}
				break;
			}
			case CHECKBOX: {
				search.setName(search.getName());
				ElasticUtil.checkboxSearch(search, queryBuilder);
				break;
			}
			case NUMBER:
			case FLOATNUMBER:
			case PERCENT:
				ElasticUtil.numberSearch(search, queryBuilder);
				break;
			case DATE_INTERVAL:
				break;
			case DATE:
			case DATETIME:
				ElasticUtil.dateSearch(search, queryBuilder, fieldEnum);
				break;
			case AREA_POSITION:
				PrefixQueryBuilder prefixQuery = QueryBuilders.prefixQuery(search.getName(), "[" + CollUtil.join(search.getValues(), Const.SEPARATOR));
				queryBuilder.filter(prefixQuery);
				break;
			case CURRENT_POSITION:
				if (search.getSearchEnum() == CrmSearchBO.FieldSearchEnum.IS) {
					search.setValues(Collections.singletonList("\"" + search.getValues().get(0) + "\""));
					search.setSearchEnum(CrmSearchBO.FieldSearchEnum.CONTAINS);
				}
				if (search.getSearchEnum() == CrmSearchBO.FieldSearchEnum.IS_NOT) {
					search.setValues(Collections.singletonList("\"" + search.getValues().get(0) + "\""));
					search.setSearchEnum(CrmSearchBO.FieldSearchEnum.NOT_CONTAINS);
				}
				ElasticUtil.textSearch(search, queryBuilder);
				break;
			case USER:
			case SINGLE_USER:
			case STRUCTURE:
				ElasticUtil.userSearch(search, queryBuilder);
				break;
			default:
				ElasticUtil.textSearch(search, queryBuilder);
				break;
		}
	}

	/**
	 *
	 */
	default void setCrmDataAuth(BoolQueryBuilder boolQueryBuilder) {
		UserInfo user = UserUtil.getUser();
		Long userId = user.getUserId();
		CrmEnum crmEnum = getLabel();
		if (UserUtil.isAdmin() || crmEnum.equals(CrmEnum.CUSTOMER_POOL)) {
			return;
		}
		BoolQueryBuilder authBoolQuery = QueryBuilders.boolQuery();
		List<Long> dataAuthUserIds = AuthUtil.queryAuthUserList(getLabel(), CrmAuthEnum.LIST);
		if (CollUtil.isNotEmpty(dataAuthUserIds)) {
			if (crmEnum.equals(CrmEnum.MARKETING)) {
				for (Long id : dataAuthUserIds) {
					authBoolQuery.should(QueryBuilders.termQuery("ownerUserId", id))
							.should(QueryBuilders.termQuery("relationUserId", id));
				}
			} else {
				authBoolQuery.should(QueryBuilders.termsQuery("ownerUserId", dataAuthUserIds));
				if (Arrays.asList(CrmEnum.CUSTOMER, CrmEnum.CONTACTS, CrmEnum.BUSINESS, CrmEnum.RECEIVABLES, CrmEnum.CONTRACT).contains(crmEnum)) {
					authBoolQuery.should(QueryBuilders.termQuery("teamMemberIds", userId));
				}
			}
		}
		boolQueryBuilder.must(authBoolQuery);
	}

	default void sort(CrmSearchBO crmSearchBO, SearchSourceBuilder sourceBuilder) {
		//todo does not consider the advanced query paging on the mobile phone for the time being
		String searchAfterKey = "es:search:" + UserUtil.getUserId().toString();
		List<CrmFieldSortVO> crmFieldSortList = getBean(ICrmFieldService.class).queryListHead(getLabel().getType());
		crmFieldSortList.add(new CrmFieldSortVO().setFieldName("receiveTime").setName("Received customer time").setType(FieldEnum.DATETIME.getType()));
		crmFieldSortList.add(new CrmFieldSortVO().setFieldName("preOwnerUserName").setName("Pre-OwnerUserName").setType(FieldEnum.TEXT.getType()));
		crmFieldSortList.add(new CrmFieldSortVO().setFieldName("createTime").setName("create time").setType(FieldEnum.DATETIME.getType()));
		crmFieldSortList.add(new CrmFieldSortVO().setFieldName("lastTime").setName("Last Contact Time").setType(FieldEnum.DATETIME.getType()));
		crmFieldSortList.add(new CrmFieldSortVO().setFieldName("poolTime").setName("Time to enter high seas").setType(FieldEnum.DATETIME.getType()));
		crmFieldSortList.add(new CrmFieldSortVO().setFieldName("contactsId").setName("contact ID").setType(FieldEnum.TEXT.getType()));
		if (CrmEnum.RECEIVABLES_PLAN == getLabel()) {
			crmFieldSortList.add(new CrmFieldSortVO().setFieldName("receivablesId").setName("Receipt ID").setType(FieldEnum.TEXT.getType()));
		}
		if (crmSearchBO.getPage() <= 100) {
			if (crmSearchBO.getPageType().equals(1)) {
				// set start and end
				sourceBuilder.from((crmSearchBO.getPage() - 1) * crmSearchBO.getLimit());
			}
		}

		sourceBuilder.size(crmSearchBO.getLimit());
		AtomicReference<Integer> fieldType = new AtomicReference<>(0);
		List<String> fieldList = new ArrayList<>();
		crmFieldSortList.forEach(crmField -> {
			if (crmField.getFieldName().equals(crmSearchBO.getSortField())) {
				fieldType.set(crmField.getType());
			}
			fieldList.add(crmField.getFieldName());
		});
		if (StrUtil.isEmpty(crmSearchBO.getSortField()) || crmSearchBO.getOrder() == null || fieldType.get().equals(0)) {
			crmSearchBO.setOrder(1).setSortField("updateTime");
		} else {
			FieldEnum fieldEnum = FieldEnum.parse(fieldType.get());
			switch (fieldEnum) {
				case TEXT:
				case TEXTAREA:
				case SELECT:
				case MOBILE:
				case FILE:
				case CHECKBOX:
				case USER:
				case STRUCTURE:
				case EMAIL:
					crmSearchBO.setSortField(crmSearchBO.getSortField() + ".sort");
					break;
				case DATE:
				case NUMBER:
				case FLOATNUMBER:
				case DATETIME:
					break;
				default:
					break;
			}
		}
		if (crmSearchBO.getPage() > 100) {
			Redis redis = BaseUtil.getRedis();
			Long length = redis.getLength(searchAfterKey);
			if ((crmSearchBO.getPage() - 100) > length.intValue()) {

				sourceBuilder.from(0);
				crmSearchBO.setPage(1);
			} else {
				Object[] keyIndex = redis.getKeyIndex(searchAfterKey, crmSearchBO.getPage() - 101);
				sourceBuilder.searchAfter(keyIndex);
			}
		}

		//
		sourceBuilder.sort(SortBuilders.fieldSort(crmSearchBO.getSortField()).order(Objects.equals(2, crmSearchBO.getOrder()) ? SortOrder.ASC : SortOrder.DESC));
		sourceBuilder.sort(SortBuilders.fieldSort("_id").order(SortOrder.DESC));
		List<String> fieldNameList = new ArrayList<>();

		for (String fieldName : fieldList) {
			fieldNameList.add(fieldName);
			if (fieldName.endsWith("Name")) {
				String name = fieldName.substring(0, fieldName.indexOf("Name"));
				fieldNameList.add(name + "Id");
			}
			if (fieldName.endsWith("Num")) {
				String name = fieldName.substring(0, fieldName.indexOf("Num"));
				fieldNameList.add(name + "Id");
			}
		}
		if (getLabel().equals(CrmEnum.CONTRACT)) {
			fieldNameList.add("receivedMoney");
		} else if (getLabel().equals(CrmEnum.BUSINESS)) {
			fieldNameList.add("isEnd");
		}
		sourceBuilder.fetchSource(fieldNameList.toArray(new String[0]), null);
	}

	/**
	 * @return
	 */
	default Dict getSearchTransferMap() {
		return Dict.create();
	}

	/**
	 * @param model obj
	 * @param id    ID
	 */
	default void savePage(CrmModelSaveBO model, Object id, boolean isExcel) {
		List<CrmModelFiledVO> crmModelFiledList = queryDefaultField();
		Map<String, Object> map = new HashMap<>(model.getEntity());
		model.getField().forEach(field -> {
			map.put(field.getFieldName(), field.getValue());
		});
		crmModelFiledList.forEach(modelField -> {
			if (map.get(modelField.getFieldName()) == null) {
				map.remove(modelField.getFieldName());
				return;
			}
			if (modelField.getFieldType() == 0 && Arrays.asList(3, 9, 10, 12).contains(modelField.getType())) {
				Object value = map.remove(modelField.getFieldName());
				if (value != null) {
					map.put(modelField.getFieldName(), StrUtil.splitTrim(value.toString(), ","));
				} else {
					map.put(modelField.getFieldName(), new ArrayList<>());
				}
			}
			if (FieldEnum.DATE.getType().equals(modelField.getType())) {
				Object value = map.remove(modelField.getFieldName());
				if (ObjectUtil.isNotEmpty(value)) {
					if (value instanceof Date) {
						map.put(modelField.getFieldName(), DateUtil.formatDate((Date) value));
					} else if (value instanceof String) {
						map.put(modelField.getFieldName(), value.toString());
					}
				}
			}

			if (FieldEnum.DATETIME.getType().equals(modelField.getType())) {
				Object value = map.remove(modelField.getFieldName());
				if (ObjectUtil.isNotEmpty(value)) {
					if (value instanceof Date) {
						map.put(modelField.getFieldName(), DateUtil.formatDateTime((Date) value));
					} else if (value instanceof String) {
						map.put(modelField.getFieldName(), value.toString());
					}
				}

			}
			if (FieldEnum.FILE.getType().equals(modelField.getType())) {
				Object value = map.remove(modelField.getFieldName());
				if (!ObjectUtil.isEmpty(value)) {
					List<FileEntity> data = getBean(AdminFileService.class).queryFileList((String) value).getData();
					map.put(modelField.getFieldName(), data.stream().map(FileEntity::getName).collect(Collectors.toList()));
				}
			}
			if (getBean(FieldService.class).equalsByType(modelField.getType())) {
				Object value = map.remove(modelField.getFieldName());
				if (!ObjectUtil.isEmpty(value)) {
					if (value instanceof String) {
						map.put(modelField.getFieldName(), value.toString());
					} else {
						map.put(modelField.getFieldName(), JSON.toJSONString(value));
					}
				}
			}
			if (FieldEnum.DATE_INTERVAL.getType().equals(modelField.getType())) {
				Object value = map.remove(modelField.getFieldName());
				if (!ObjectUtil.isEmpty(value)) {
					if (value instanceof String) {
						map.put(modelField.getFieldName(), StrUtil.splitTrim(value.toString(), ","));
					} else if (value instanceof Collection) {
						map.put(modelField.getFieldName(), value);
					}
				}
			}

		});
		setOtherField(map);

		if (map.containsKey("ownerUserId")) {
			SimpleUser simpleUser = UserCacheUtil.getSimpleUser(Long.valueOf(map.get("ownerUserId").toString()));
			map.put("ownerDeptId", simpleUser.getDeptId());
			map.put("ownerDeptName", simpleUser.getDeptName());
		}
		UpdateRequest request = new UpdateRequest(getIndex(), getDocType(), id.toString());
		request.doc(map);
		request.docAsUpsert(true);
		try {
			getRestTemplate().getClient().update(request, RequestOptions.DEFAULT);
		} catch (IOException e) {
			throw new CrmException(SystemCodeEnum.SYSTEM_ERROR);
		}
		if (!isExcel) {
			getRestTemplate().refresh(getIndex());
		}
	}

	/**
	 * @param map
	 */
	void setOtherField(Map<String, Object> map);

	/**
	 * @param ids ids
	 */
	default void deletePage(List<Integer> ids) {
		DeleteQuery query = new DeleteQuery();
		query.setQuery(QueryBuilders.idsQuery().addIds(ids.stream().map(Object::toString).toArray(String[]::new)));
		query.setIndex(getIndex());
		query.setType(getDocType());
		getRestTemplate().delete(query);
	}

	/**
	 * @param fieldName
	 * @param value
	 * @param ids       ids
	 */
	default void updateField(String fieldName, Object value, List<Integer> ids) {
		BulkRequest bulkRequest = new BulkRequest();
		Map<String, Object> map = new HashMap<>();
		if ("ownerUserId".equals(fieldName)) {
			map.put("ownerUserName", UserCacheUtil.getUserName((Long) value));
		}
		map.put(fieldName, value);
		ids.forEach(id -> {
			UpdateRequest request = new UpdateRequest(getIndex(), getDocType(), id.toString());
			request.doc(map);
			bulkRequest.add(request);
		});
		try {
			getRestTemplate().getClient().bulk(bulkRequest, RequestOptions.DEFAULT);
			getRestTemplate().refresh(getIndex());
		} catch (IOException e) {
			log.error("es", e);
		}
	}

	/**
	 * @param id id
	 */
	default void updateField(JSONObject jsonObject, Integer id) {
		Map<String, Object> map = new HashMap<>();
		String fieldName = jsonObject.getString("fieldName");
		if (jsonObject.get("value") != null) {
			if (FieldEnum.DATE.getType().equals(jsonObject.getInteger("type"))) {
				Object value = jsonObject.get("value");
				map.put(fieldName, value);
			} else if (FieldEnum.DATETIME.getType().equals(jsonObject.getInteger("type"))) {
				Object value = jsonObject.get("value");
				map.put(fieldName, value);
			} else if (FieldEnum.FILE.getType().equals(jsonObject.getInteger("type"))) {
				Object value = jsonObject.get("value");
				List<FileEntity> data = getBean(AdminFileService.class).queryFileList((String) value).getData();
				map.put(fieldName, data.stream().map(FileEntity::getName).collect(Collectors.joining(",")));
			} else if (getBean(FieldService.class).equalsByType(jsonObject.getInteger("type"))) {
				Object value = jsonObject.get("value");
				if (!ObjectUtil.isEmpty(value)) {
					map.put(fieldName, JSON.toJSONString(value));
				}
			} else if (jsonObject.getInteger("fieldType") == 0 && Arrays.asList(3, 8, 9, 10, 11, 12).contains(jsonObject.getInteger("type"))) {
				Object value = jsonObject.get("value");
				if (value != null) {
					map.put(fieldName, StrUtil.splitTrim(value.toString(), ","));
				} else {
					map.put(fieldName, new ArrayList<>());
				}
			} else {
				String value = jsonObject.getString("value");
				map.put(fieldName, value);
			}

		} else {
			map.put(fieldName, null);
		}

		map.put("updateTime", DateUtil.formatDateTime(new Date()));
		try {
			UpdateRequest request = new UpdateRequest(getIndex(), getDocType(), id.toString());
			request.doc(map);
			getRestTemplate().getClient().update(request, RequestOptions.DEFAULT);
			getRestTemplate().refresh(getIndex());
		} catch (IOException e) {
			log.error("es", e);
		}
	}

	/**
	 * @param map
	 * @param ids
	 */
	default void updateField(Map<String, Object> map, List<Integer> ids) {
		BulkRequest bulkRequest = new BulkRequest();
		ids.forEach(id -> {
			UpdateRequest request = new UpdateRequest(getIndex(), getDocType(), id.toString());

			if (map.containsKey("ownerUserId")) {
				SimpleUser simpleUser = UserCacheUtil.getSimpleUser(Long.valueOf(map.get("ownerUserId").toString()));
				map.put("ownerDeptId", simpleUser.getDeptId());
				map.put("ownerDeptName", simpleUser.getDeptName());
			}
			request.doc(map);
			bulkRequest.add(request);
		});
		try {
			getRestTemplate().getClient().bulk(bulkRequest, RequestOptions.DEFAULT);
			getRestTemplate().refresh(getIndex());
		} catch (IOException e) {
			log.error("es", e);
		}
	}

	/**
	 * @return data
	 */
	public CrmEnum getLabel();


	/**
	 * @return index
	 */
	default public String getIndex() {
		return getLabel().getIndex();
	}

	/**
	 * @return data
	 */
	List<CrmModelFiledVO> queryDefaultField();

	/**
	 * @param poolBO bo
	 */
	@SuppressWarnings("unchecked")
	default public void putInPool(CrmCustomerPoolBO poolBO) {
		SearchRequest searchRequest = new SearchRequest(getIndex());
		searchRequest.types(getDocType());
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.size(poolBO.getIds().size());
		searchRequest.source(sourceBuilder.fetchSource(new String[]{"poolId", "ownerUserId"}, null).query(QueryBuilders.idsQuery().addIds(poolBO.getIds().stream().map(Object::toString).toArray(String[]::new))));
		BulkRequest bulkRequest = new BulkRequest();
		try {
			SearchResponse searchResponse = getRestTemplate().getClient().search(searchRequest, RequestOptions.DEFAULT);
			for (SearchHit hit : searchResponse.getHits()) {
				UpdateRequest request = new UpdateRequest(getIndex(), getDocType(), hit.getId());
				Map<String, Object> map = new HashMap<>();
				if (hit.getSourceAsMap().containsKey("poolId")) {
					Object obj = hit.getSourceAsMap().get("poolId");
					if (obj instanceof Collection) {
						Set<Integer> set = new HashSet<>((List<Integer>) obj);
						set.add(poolBO.getPoolId());
						map.put("poolId", set);
					} else if (obj instanceof Integer) {
						Set<Integer> set = new HashSet<>();
						set.add((Integer) obj);
						set.add(poolBO.getPoolId());
						map.put("poolId", set);
					} else {
						map.put("poolId", Collections.singletonList(poolBO.getPoolId()));
					}
				} else {
					map.put("poolId", Collections.singletonList(poolBO.getPoolId()));
				}
				Object ownerUserId = hit.getSourceAsMap().get("ownerUserId");
				if (ownerUserId != null) {
					map.put("preOwnerUserName", UserCacheUtil.getUserName(Long.valueOf(ownerUserId.toString())));
					map.put("preOwnerUserId", Long.valueOf(ownerUserId.toString()));
				}
				map.put("ownerUserId", null);
				map.put("poolTime", DateUtil.formatDateTime(new Date()));
				request.doc(map);
				bulkRequest.add(request);
			}

			SearchRequest contactsRequest = new SearchRequest(CrmEnum.CONTACTS.getIndex());
			contactsRequest.types(getDocType());
			contactsRequest.source(SearchSourceBuilder.searchSource().fetchSource(new String[]{"contactsId"}, null).query(QueryBuilders.termsQuery("customerId", poolBO.getIds())));
			SearchResponse contactsResponse = getRestTemplate().getClient().search(contactsRequest, RequestOptions.DEFAULT);
			for (SearchHit hit : contactsResponse.getHits()) {
				UpdateRequest request = new UpdateRequest(CrmEnum.CONTACTS.getIndex(), getDocType(), hit.getId());
				Map<String, Object> map = new HashMap<>();
				map.put("ownerUserId", null);
				map.put("ownerUserName", null);
				request.doc(map);
				bulkRequest.add(request);
			}

			if (bulkRequest.requests() == null || bulkRequest.requests().size() == 0) {
				return;
			}
			getRestTemplate().getClient().bulk(bulkRequest, RequestOptions.DEFAULT);
		} catch (Exception e) {
			log.error("", e);
			throw new CrmException(SystemCodeEnum.SYSTEM_ERROR);
		}
		getRestTemplate().refresh(getIndex());
	}

	/**
	 *
	 */
	default public void receiveCustomer(CrmCustomerPoolBO poolBO, Integer isReceive, List<Integer> contactsIds) {
		BulkRequest bulkRequest = new BulkRequest();
		try {
			SimpleUser simpleUser = UserCacheUtil.getSimpleUser(poolBO.getUserId());
			for (Integer id : poolBO.getIds()) {
				UpdateRequest request = new UpdateRequest(getIndex(), getDocType(), id.toString());
				Map<String, Object> map = new HashMap<>();
				String date = DateUtil.formatDateTime(new Date());
				map.put("ownerUserId", poolBO.getUserId());
				map.put("ownerUserName", simpleUser.getRealname());
				map.put("ownerDeptId", simpleUser.getDeptId());
				map.put("ownerDeptName", simpleUser.getDeptName());
				map.put("followup", 0);
				map.put("receiveTime", date);
				map.put("updateTime", date);
				map.put("isReceive", isReceive);
				map.put("poolId", new ArrayList<>());
				request.doc(map);
				bulkRequest.add(request);
			}
			for (Integer contactsId : contactsIds) {
				UpdateRequest contactsRequest = new UpdateRequest(CrmEnum.CONTACTS.getIndex(), getDocType(), contactsId.toString());
				Map<String, Object> contactsMap = new HashMap<>();
				String date = DateUtil.formatDateTime(new Date());
				contactsMap.put("ownerUserId", poolBO.getUserId());
				contactsMap.put("ownerUserName", simpleUser.getRealname());
				contactsMap.put("ownerDeptId", simpleUser.getDeptId());
				contactsMap.put("ownerDeptName", simpleUser.getDeptName());
				contactsMap.put("updateTime", date);
				contactsRequest.doc(contactsMap);
				bulkRequest.add(contactsRequest);
			}
			if (bulkRequest.requests() == null || bulkRequest.requests().size() == 0) {
				return;
			}
			getRestTemplate().getClient().bulk(bulkRequest, RequestOptions.DEFAULT);
		} catch (Exception e) {
			log.error("", e);
			throw new CrmException(SystemCodeEnum.SYSTEM_ERROR);
		}
		getRestTemplate().refresh(getIndex());
	}

	default public List<CrmModelFiledVO> appendInformation(CrmModel crmModel) {
		List<CrmModelFiledVO> filedVOS = new ArrayList<>();
		if (!getLabel().equals(CrmEnum.RETURN_VISIT)) {
			CrmModelFiledVO filedVO = new CrmModelFiledVO("owner_user_name", FieldEnum.USER);
			filedVO.setName("Person in charge");
			if (crmModel.getOwnerUserId() != null) {
				List<SimpleUser> data = UserCacheUtil.getSimpleUsers(Collections.singleton(crmModel.getOwnerUserId()));
				filedVO.setValue(data);
			}
			filedVO.setFieldType(1);
			filedVOS.add(filedVO);
		}
		List<CrmRoleField> roleFieldList = getBean(ICrmRoleFieldService.class).queryUserFieldAuth(getLabel().getType(), 1);
		Map<String, CrmRoleField> levelMap = roleFieldList.stream().collect(Collectors.toMap(crmRoleField -> StrUtil.toCamelCase(crmRoleField.getFieldName()), Function.identity()));
		if (getLabel().equals(CrmEnum.CUSTOMER) || getLabel().equals(CrmEnum.LEADS)) {
			filedVOS.add(new CrmModelFiledVO("last_content", FieldEnum.TEXTAREA, "last follow-up record", 1).setValue(crmModel.get("lastContent")));
		}
		Object value = UserCacheUtil.getSimpleUsers(Collections.singletonList((Long) crmModel.get("createUserId")));
		filedVOS.add(new CrmModelFiledVO("create_user_name", FieldEnum.USER, "Creator", 1).setValue(value));
		filedVOS.add(new CrmModelFiledVO("create_time", FieldEnum.DATETIME, "create time", 1).setValue(crmModel.get("createTime")));
		filedVOS.add(new CrmModelFiledVO("update_time", FieldEnum.DATETIME, "update time", 1).setValue(crmModel.get("updateTime")));
		if (!getLabel().equals(CrmEnum.PRODUCT) &&
				!getLabel().equals(CrmEnum.RECEIVABLES) &&
				!getLabel().equals(CrmEnum.RETURN_VISIT) &&
				!getLabel().equals(CrmEnum.RECEIVABLES_PLAN) &&
				!getLabel().equals(CrmEnum.INVOICE)) {
			filedVOS.add(new CrmModelFiledVO("last_time", FieldEnum.DATETIME, "last follow-up time", 1).setValue(crmModel.get("lastTime")));
		}
		if (getLabel().equals(CrmEnum.RECEIVABLES_PLAN)) {
			filedVOS.add(new CrmModelFiledVO("num", FieldEnum.TEXT, "Number of Periods", 1).setValue(crmModel.get("num")));
			filedVOS.add(new CrmModelFiledVO("real_received_money", FieldEnum.TEXT, "Actual Receipt Amount", 1).setValue(crmModel.get("realReceivedMoney")));
			filedVOS.add(new CrmModelFiledVO("unreceived_money", FieldEnum.TEXT, "Unreceived Amount", 1).setValue(crmModel.get("unreceivedMoney")));
			filedVOS.add(new CrmModelFiledVO("real_return_date", FieldEnum.DATETIME, "Actual Payment Date", 1).setValue(crmModel.get("realReturnDate")));
		}
		if (Arrays.asList(CrmEnum.CUSTOMER, CrmEnum.CONTACTS, CrmEnum.BUSINESS, CrmEnum.RECEIVABLES, CrmEnum.CONTRACT).contains(getLabel())) {
			List<CrmTeamMembers> teamMembers = getBean(ICrmTeamMembersService.class)
					.lambdaQuery().select(CrmTeamMembers::getUserId)
					.eq(CrmTeamMembers::getType, getLabel().getType())
					.eq(CrmTeamMembers::getTypeId, crmModel.get(getLabel().getPrimaryKey()))
					.list();
			filedVOS.add(new CrmModelFiledVO("teamMemberIds", FieldEnum.TEXT, "Related Teams", 1).setValue(teamMembers.stream().map(teamMember -> UserCacheUtil.getUserName(teamMember.getUserId())).collect(Collectors.joining(Const.SEPARATOR))));
		}
		filedVOS.removeIf(field -> {
			String fieldName = StrUtil.toCamelCase(field.getFieldName());
			//Not the admin user, and the field authorization is not queryable
			return !UserUtil.isAdmin() && levelMap.containsKey(fieldName) && Objects.equals(1, levelMap.get(fieldName).getAuthLevel());
		});
		for (CrmModelFiledVO filedVO : filedVOS) {
			filedVO.setSysInformation(1);
		}
		return filedVOS;
	}


	default void supplementFieldInfo(Integer label, Integer typeId, Integer recordId, ExamineRecordSaveBO examineRecordSaveBO) {
		examineRecordSaveBO.setLabel(label);
		examineRecordSaveBO.setTypeId(typeId);
		examineRecordSaveBO.setRecordId(recordId);
		if (examineRecordSaveBO.getDataMap() != null) {
			examineRecordSaveBO.getDataMap().put("createUserId", UserUtil.getUserId());
		} else {
			Map<String, Object> entityMap = new HashMap<>(1);
			entityMap.put("createUserId", UserUtil.getUserId());
			examineRecordSaveBO.setDataMap(entityMap);
		}
	}

	default void removeFieldByType(List<CrmModelFiledVO> crmModelFiledList) {
		List<FieldEnum> fieldEnums = Arrays.asList(FieldEnum.FILE, FieldEnum.CHECKBOX, FieldEnum.USER, FieldEnum.STRUCTURE,
				FieldEnum.AREA, FieldEnum.AREA_POSITION, FieldEnum.CURRENT_POSITION, FieldEnum.DATE_INTERVAL, FieldEnum.BOOLEAN_VALUE,
				FieldEnum.HANDWRITING_SIGN, FieldEnum.DESC_TEXT, FieldEnum.DETAIL_TABLE, FieldEnum.CALCULATION_FUNCTION);
		crmModelFiledList.removeIf(model -> fieldEnums.contains(FieldEnum.parse(model.getType())));
	}

	default void uniqueFieldIsAbnormal(String name, Integer fieldId, String value, String batchId) {
		if (fieldId == null) {
			return;
		}
		CrmField field = getBean(ICrmFieldService.class).getById(fieldId);
		if (field == null || Objects.equals(field.getIsUnique(), 0)) {
			return;
		}
		CrmFieldVerifyBO crmFieldVerifyBO = new CrmFieldVerifyBO();
		crmFieldVerifyBO.setFieldId(fieldId);
		crmFieldVerifyBO.setValue(value);
		crmFieldVerifyBO.setBatchId(batchId);
		CrmFieldVerifyBO fieldVerifyBO = getBean(ICrmFieldService.class).verify(crmFieldVerifyBO);
		if (Objects.equals(fieldVerifyBO.getStatus(), 0)) {
			throw new CrmException(CrmCodeEnum.CRM_FIELD_ALREADY_EXISTS, name);
		}
	}
}
