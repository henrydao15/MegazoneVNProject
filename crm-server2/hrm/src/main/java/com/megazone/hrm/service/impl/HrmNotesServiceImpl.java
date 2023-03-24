package com.megazone.hrm.service.impl;

import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.hrm.common.EmployeeHolder;
import com.megazone.hrm.entity.BO.QueryNotesStatusBO;
import com.megazone.hrm.entity.PO.HrmNotes;
import com.megazone.hrm.mapper.HrmNotesMapper;
import com.megazone.hrm.service.IHrmNotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class HrmNotesServiceImpl extends BaseServiceImpl<HrmNotesMapper, HrmNotes> implements IHrmNotesService {

	@Autowired
	private HrmNotesMapper notesMapper;

	@Override
	public void addNotes(HrmNotes notes) {
		notes.setEmployeeId(EmployeeHolder.getEmployeeId());
		save(notes);
	}

	@Override
	public List<HrmNotes> queryNoteListByTime(Date time, Collection<Integer> employeeIds) {
		return notesMapper.queryNoteListByTime(time, employeeIds);
	}

	@Override
	public Set<String> queryNoteStatusList(QueryNotesStatusBO queryNotesStatusBO, Collection<Integer> employeeIds) {
		return notesMapper.queryNoteStatusList(queryNotesStatusBO, employeeIds);
	}
}
