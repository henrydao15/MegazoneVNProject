package com.megazone.crm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.BO.CrmMemberSaveBO;
import com.megazone.crm.entity.PO.CrmTeamMembers;
import com.megazone.crm.entity.VO.CrmMembersSelectVO;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * crm
 *
 * </p>
 *
 * @author
 * @since 2021-04-07
 */
public interface ICrmTeamMembersService extends BaseService<CrmTeamMembers> {
	/**
	 * @param crmEnum
	 * @param typeId      ID
	 * @param ownerUserId ID
	 * @return data
	 */
	public List<CrmMembersSelectVO> getMembers(CrmEnum crmEnum, Integer typeId, Long ownerUserId);

	/**
	 * @param crmEnum
	 * @param crmMemberSaveBO data
	 */
	public void addMember(CrmEnum crmEnum, CrmMemberSaveBO crmMemberSaveBO);

	/**
	 * @param crmEnum
	 * @param crmMemberSaveBO data
	 */
	public void deleteMember(CrmEnum crmEnum, CrmMemberSaveBO crmMemberSaveBO);

	/**
	 * @param crmEnum
	 * @param typeId  ID
	 */
	public void exitTeam(CrmEnum crmEnum, Integer typeId);

	/**
	 * @param crmEnum
	 * @param typeId  ID
	 * @param userId  ID
	 * @param power
	 */
	public void addSingleMember(CrmEnum crmEnum, Integer typeId, Long userId, Integer power, Date expiresTime, String name);


	/**
	 * @param crmEnum
	 * @param typeId      ID
	 * @param ownerUserId ID
	 */
	public Integer queryMemberCount(CrmEnum crmEnum, Integer typeId, Long ownerUserId);

	/**
	 *
	 */
	public void removeOverdueTeamMembers();
}
