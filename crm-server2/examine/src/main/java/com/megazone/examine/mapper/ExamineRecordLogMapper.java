package com.megazone.examine.mapper;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.examine.entity.BO.ExaminePageBO;
import com.megazone.examine.entity.PO.ExamineRecord;
import com.megazone.examine.entity.PO.ExamineRecordLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-19
 */
public interface ExamineRecordLogMapper extends BaseMapper<ExamineRecordLog> {

	BasePage<ExamineRecord> selectRecordLogListByUser(BasePage<Object> parse, @Param("data") ExaminePageBO examinePageBO,
													  @Param("userId") Long userId, @Param("roleIds") List<Integer> roleIds);


	List<Integer> selectRecordTypeIdListByUser(@Param("data") ExaminePageBO examinePageBO,
											   @Param("userId") Long userId, @Param("roleIds") List<Integer> roleIds);
}
