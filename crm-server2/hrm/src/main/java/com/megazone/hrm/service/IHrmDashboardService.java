package com.megazone.hrm.service;

import com.megazone.hrm.entity.BO.QueryNotesByTimeBO;
import com.megazone.hrm.entity.BO.QueryNotesStatusBO;
import com.megazone.hrm.entity.VO.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IHrmDashboardService {

	/**
	 * @return
	 */
	Map<Integer, Long> employeeSurvey();

	/**
	 * @return
	 */
	RecruitSurveyCountVO recruitSurvey();

	/**
	 * @return
	 */
	LastSalarySurveyVO lastSalarySurvey();

	/**
	 * @param status
	 * @return
	 */
	List<AppraisalSurveyVO> appraisalSurvey(String status);

	/**
	 * @return
	 */
	Map<Integer, Integer> appraisalCountSurvey();


	/**
	 * @return
	 */
	DoRemindVO toDoRemind();

	/**
	 * @param queryNotesByTimeBO
	 * @return
	 */
	List<NotesVO> queryNotesByTime(QueryNotesByTimeBO queryNotesByTimeBO);

	/**
	 * @param queryNotesByTimeBO
	 * @return
	 */
	Set<String> queryNotesStatus(QueryNotesStatusBO queryNotesByTimeBO);

	/**
	 * @return
	 */
	Map<Integer, Long> myTeam();

	/**
	 * @return
	 */
	TeamSurveyVO teamSurvey();

	/**
	 * @return
	 */
	MySurveyVO mySurvey();


	/**
	 * @param queryNotesByTimeBO
	 * @return
	 */
	Set<String> myNotesStatus(QueryNotesStatusBO queryNotesByTimeBO);

	/**
	 * @param queryNotesByTimeBO
	 * @return
	 */
	List<NotesVO> myNotesByTime(QueryNotesByTimeBO queryNotesByTimeBO);
}
