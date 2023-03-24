package com.megazone.crm.mapper;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.crm.entity.BO.CrmFieldDataBO;
import com.megazone.crm.entity.PO.CrmField;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-19
 */
public interface CrmFieldMapper extends BaseMapper<CrmField> {

	/**
	 * @param tableName
	 * @param fieldName
	 * @param value
	 * @param batchId   batchId
	 * @param label
	 * @return num
	 */
	public Integer verifyFixedField(@Param("tableName") String tableName, @Param("fieldName") String fieldName,
									@Param("value") String value, @Param("batchId") String batchId, @Param("label") Integer label);

	/**
	 * @param tableName
	 * @param fieldId   Id
	 * @param value
	 * @param batchId   batchId
	 * @return num
	 */
	public Integer verifyField(@Param("tableName") String tableName, @Param("fieldId") Integer fieldId,
							   @Param("value") String value, @Param("batchId") String batchId);

	/**
	 * @return
	 */
	@SqlParser(filter = true)
	public Integer dataCheck(@Param("name") String name, @Param("label") Integer label, @Param("type") Integer type);

	@SqlParser(filter = true)
	public List<Map<String, Object>> initData(Map<String, Object> map);

	@SqlParser(filter = true)
	public List<CrmFieldDataBO> initFieldData(@Param("lastId") Integer lastId, @Param("primaryKey") String primaryKey, @Param("tableName") String tableName);

	Integer queryCustomerFieldDuplicateByFixed(@Param("name") String name, @Param("value") Object value);

}
