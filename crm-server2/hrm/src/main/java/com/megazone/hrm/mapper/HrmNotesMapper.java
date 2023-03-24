package com.megazone.hrm.mapper;

import com.megazone.core.servlet.BaseMapper;
import com.megazone.hrm.entity.BO.QueryNotesStatusBO;
import com.megazone.hrm.entity.PO.HrmNotes;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-07-13
 */
public interface HrmNotesMapper extends BaseMapper<HrmNotes> {

	List<HrmNotes> queryNoteListByTime(@Param("time") Date time, @Param("employeeIds") Collection<Integer> employeeIds);

	Set<String> queryNoteStatusList(@Param("data") QueryNotesStatusBO queryNotesStatusBO, @Param("employeeIds") Collection<Integer> employeeIds);
}
