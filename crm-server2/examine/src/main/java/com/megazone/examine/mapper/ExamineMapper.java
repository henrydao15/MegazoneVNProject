package com.megazone.examine.mapper;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.examine.entity.PO.Examine;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-13
 */
public interface ExamineMapper extends BaseMapper<Examine> {

	BasePage<Examine> selectPartExaminePage(BasePage<Object> parse, @Param("label") Integer label, @Param("isAdmin") boolean isAdmin,
											@Param("isPart") boolean isPart, @Param("userId") Long userId, @Param("deptId") Integer deptId);

}
