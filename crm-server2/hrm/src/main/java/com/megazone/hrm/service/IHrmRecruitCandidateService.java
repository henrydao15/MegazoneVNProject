package com.megazone.hrm.service;

import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseService;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.hrm.entity.BO.*;
import com.megazone.hrm.entity.PO.HrmRecruitCandidate;
import com.megazone.hrm.entity.VO.CandidatePageListVO;

import java.util.*;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface IHrmRecruitCandidateService extends BaseService<HrmRecruitCandidate> {

	/**
	 * @param queryCandidatePageListBO
	 * @return
	 */
	BasePage<CandidatePageListVO> queryCandidateList(QueryCandidatePageListBO queryCandidatePageListBO);

	/**
	 * @param candidateId
	 * @return
	 */
	CandidatePageListVO queryById(String candidateId);

	/**
	 * @param hrmRecruitCandidate
	 */
	void addCandidate(HrmRecruitCandidate hrmRecruitCandidate);

	/**
	 * @param hrmRecruitCandidate
	 */
	void setCandidate(HrmRecruitCandidate hrmRecruitCandidate);

	/**
	 * @param candidateId
	 */
	void deleteById(Integer candidateId);

	/**
	 * @param updateCandidateStatusBO
	 */
	void updateCandidateStatus(UpdateCandidateStatusBO updateCandidateStatusBO);

	/**
	 * @param updateCandidatePostBO
	 */
	void updateCandidatePost(UpdateCandidatePostBO updateCandidatePostBO);

	/**
	 * @param updateCandidateRecruitChannelBO
	 */
	void updateCandidateRecruitChannel(UpdateCandidateRecruitChannelBO updateCandidateRecruitChannelBO);

	/**
	 * @param queryCleanCandidateBO
	 * @return
	 */
	List<Integer> queryCleanCandidateIds(QueryCleanCandidateBO queryCleanCandidateBO);

	/**
	 * @param candidateId
	 * @return
	 */
	Result<List<FileEntity>> queryFile(Integer candidateId);

	/**
	 * @return
	 */
	Map<Integer, Long> queryCandidateStatusNum();


	/**
	 * @param eliminateCandidateBO
	 */
	void eliminateCandidate(EliminateCandidateBO eliminateCandidateBO);

	/**
	 * @param candidateIds
	 */
	void deleteByIds(List<Integer> candidateIds);

	/**
	 * @return
	 */
	List<Map<String, Object>> queryRecruitListByTime(Date time, Collection<Integer> deptIds);

	/**
	 *
	 */
	Set<String> queryRecruitStatusList(QueryNotesStatusBO queryNotesStatusBO, Collection<Integer> deptIds);
}
