package com.megazone.hrm.mapper;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.hrm.entity.BO.QueryCandidatePageListBO;
import com.megazone.hrm.entity.BO.QueryNotesStatusBO;
import com.megazone.hrm.entity.PO.HrmRecruitCandidate;
import com.megazone.hrm.entity.VO.CandidatePageListVO;
import org.apache.ibatis.annotations.Param;

import java.util.*;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface HrmRecruitCandidateMapper extends BaseMapper<HrmRecruitCandidate> {


	BasePage<CandidatePageListVO> queryPageList(BasePage<CandidatePageListVO> parse, @Param("data") QueryCandidatePageListBO queryCandidatePageListBO,
												@Param("deptIds") Collection<Integer> deptIds);

	CandidatePageListVO getById(String candidateId);

	List<Map<String, Object>> queryRecruitListByTime(@Param("time") Date time, @Param("deptIds") Collection<Integer> deptIds);

	List<Map<String, Object>> queryDataAuthCandidateStatus(@Param("deptIds") Collection<Integer> deptIds);

	Set<String> queryRecruitStatusList(@Param("data") QueryNotesStatusBO queryNotesStatusBO, @Param("deptIds") Collection<Integer> deptIds);
}
