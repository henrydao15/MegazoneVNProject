package com.megazone.hrm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.BO.QueryNotesStatusBO;
import com.megazone.hrm.entity.PO.HrmNotes;

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
public interface IHrmNotesService extends BaseService<HrmNotes> {

	/**
	 * @param notes
	 */
	void addNotes(HrmNotes notes);

	/**
	 * @param time
	 * @param employeeIds
	 * @return
	 */
	List<HrmNotes> queryNoteListByTime(Date time, Collection<Integer> employeeIds);

	/**
	 * @param queryNotesStatusBO
	 * @param employeeIds
	 * @return
	 */
	Set<String> queryNoteStatusList(QueryNotesStatusBO queryNotesStatusBO, Collection<Integer> employeeIds);
}
