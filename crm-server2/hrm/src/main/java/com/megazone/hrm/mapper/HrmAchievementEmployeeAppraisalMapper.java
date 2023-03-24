package com.megazone.hrm.mapper;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.hrm.entity.BO.EvaluatoListBO;
import com.megazone.hrm.entity.PO.HrmAchievementEmployeeAppraisal;
import com.megazone.hrm.entity.VO.EvaluatoListVO;
import com.megazone.hrm.entity.VO.QueryMyAppraisalVO;
import com.megazone.hrm.entity.VO.ResultConfirmListVO;
import com.megazone.hrm.entity.VO.TargetConfirmListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface HrmAchievementEmployeeAppraisalMapper extends BaseMapper<HrmAchievementEmployeeAppraisal> {

	BasePage<QueryMyAppraisalVO> queryMyAppraisal(BasePage<QueryMyAppraisalVO> parse, @Param("employeeId") Integer employeeId, @Param("status") Integer status);

	BasePage<EvaluatoListVO> evaluatoList(BasePage<Object> parse,
										  @Param("employeeId") Integer employeeId,
										  @Param("data") EvaluatoListBO evaluatoListBO);

	BasePage<TargetConfirmListVO> queryTodoAppraisalByStatus(BasePage<TargetConfirmListVO> parse,
															 @Param("employeeId") Integer employeeId,
															 @Param("status") Integer status,
															 @Param("search") String search,
															 @Param("appraisalId") Integer appraisalId);

	BasePage<ResultConfirmListVO> queryResultConfirmList(BasePage<Object> parse, @Param("employeeId") Integer employeeId, @Param("search") String search);

	List<Map<String, Object>> queryScoreLevels(Integer appraisalId);

	Map<String, Object> queryAppraisalIdInfo(String appraisalId);

	List<Map<String, Object>> queryEmployeeByLevelId(@Param("levelId") Integer levelId,
													 @Param("appraisalId") Integer appraisalId);

	Integer queryWaitingNum(String appraisalId);

	Integer queryTotalNum(String appraisalId);

	List<TargetConfirmListVO> queryTargetConfirmScreen(@Param("employeeId") Integer employeeId,
													   @Param("status") Integer status);

	List<EvaluatoListVO> queryEvaluatoScreen(@Param("employeeId") Integer employeeId, @Param("status") Integer status);
}
