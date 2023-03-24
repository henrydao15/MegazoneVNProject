package com.megazone.hrm.mapper;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.hrm.entity.BO.QueryRecruitPostPageListBO;
import com.megazone.hrm.entity.PO.HrmRecruitPost;
import com.megazone.hrm.entity.VO.RecruitPostVO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface HrmRecruitPostMapper extends BaseMapper<HrmRecruitPost> {

	RecruitPostVO queryById(Integer postId);


	BasePage<RecruitPostVO> queryRecruitPostPageList(BasePage<RecruitPostVO> parse, @Param("data") QueryRecruitPostPageListBO queryRecruitPostPageListBO,
													 @Param("deptIds") Collection<Integer> deptIds);

	List<Integer> queryPostStatusList(@Param("deptIds") Collection<Integer> deptIds);

	void setRecruitPost(HrmRecruitPost recruitPost);
}
