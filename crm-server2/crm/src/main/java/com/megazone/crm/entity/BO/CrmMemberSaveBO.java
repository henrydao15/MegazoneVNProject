package com.megazone.crm.entity.BO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@Data
@ToString
@ApiModel
public class CrmMemberSaveBO {

	@ApiModelProperty
	private List<Integer> ids;

	@ApiModelProperty
	private List<Long> memberIds;

	@ApiModelProperty
	private Integer power;

	@ApiModelProperty
	private List<Integer> changeType;

	@ApiModelProperty
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date expiresTime;

	public CrmMemberSaveBO() {
	}

	public CrmMemberSaveBO(List<Integer> ids, CrmMemberSaveBO crmMemberSaveBO) {
		this.ids = ids;
		this.memberIds = crmMemberSaveBO.getMemberIds();
		this.power = crmMemberSaveBO.getPower();
		this.expiresTime = crmMemberSaveBO.getExpiresTime();
	}

	public CrmMemberSaveBO(Integer id, Long member) {
		this.ids = Collections.singletonList(id);
		this.memberIds = Collections.singletonList(member);
	}
}
