package com.megazone.hrm.service;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.BO.QueryRecruitPostPageListBO;
import com.megazone.hrm.entity.BO.UpdateRecruitPostStatusBO;
import com.megazone.hrm.entity.PO.HrmRecruitPost;
import com.megazone.hrm.entity.VO.RecruitPostVO;

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
public interface IHrmRecruitPostService extends BaseService<HrmRecruitPost> {

	/**
	 * @param recruitPost
	 */
	void addRecruitPost(HrmRecruitPost recruitPost);

	/**
	 * @param recruitPost
	 */
	void setRecruitPost(HrmRecruitPost recruitPost);

	/**
	 * @param postId
	 * @return
	 */
	RecruitPostVO queryById(Integer postId);

	/**
	 * @param queryRecruitPostPageListBO
	 * @return
	 */
	BasePage<RecruitPostVO> queryRecruitPostPageList(QueryRecruitPostPageListBO queryRecruitPostPageListBO);

	/**
	 * @param updateRecruitPostStatusBO
	 */
	void updateRecruitPostStatus(UpdateRecruitPostStatusBO updateRecruitPostStatusBO);

	/**
	 * @return
	 */
	Map<Integer, Long> queryPostStatusNum();


	/**
	 * @return
	 */
	List<HrmRecruitPost> queryAllRecruitPostList();

}
