package com.megazone.crm.mapper;

import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.crm.entity.PO.CrmMarketing;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-08-12
 */
public interface CrmMarketingMapper extends BaseMapper<CrmMarketing> {

	BasePage<CrmMarketing> queryPageList(BasePage<Object> parse, @Param("userIds") List<Long> userIds,
										 @Param("deptIds") List<Integer> deptIds,
										 @Param("crmType") Integer crmType,
										 @Param("search") String search, @Param("isAdmin") boolean isAdmin,
										 @Param("timeType") Integer timeType, @Param("status") Integer status);

	BasePage<JSONObject> census(BasePage<Object> parse, @Param("marketingId") Integer marketingId, @Param("userIds") List<Long> userIds, @Param("status") Integer status);
}
