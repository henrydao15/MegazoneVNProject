package com.megazone.oa.entity.VO;

import com.megazone.core.feign.admin.entity.SimpleDept;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.oa.entity.PO.OaExamineStep;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OaExamineCategoryVO {

	private Integer categoryId;

	@ApiModelProperty(value = "name")
	private String title;

	@ApiModelProperty(value = "description")
	private String remarks;

	@ApiModelProperty(value = "icon")
	private String icon;

	@ApiModelProperty(value = "1 Ordinary Approval 2 Leave Approval 3 Travel Approval 4 Overtime Approval 5 Travel Reimbursement 6 Loan Application 0 Custom Approval")
	private Integer type;

	@ApiModelProperty(value = "Creator ID")
	private Long createUserId;

	@ApiModelProperty(value = "1 to enable, 0 to disable")
	private Integer status;

	@ApiModelProperty(value = "1 is the system type and cannot be deleted")
	private Integer isSys;

	@ApiModelProperty(value = "1 fixed 2 optional")
	private Integer examineType;

	private List<SimpleUser> userList;

	private List<SimpleDept> deptList;

	@ApiModelProperty(value = "creation time")
	private Date createTime;

	@ApiModelProperty(value = "update time")
	private Date updateTime;

	@ApiModelProperty(value = "1 deleted")
	private Integer isDeleted;

	@ApiModelProperty(value = "Delete time")
	private Date deleteTime;

	@ApiModelProperty(value = "Delete Person ID")
	private Long deleteUserId;

	private List<OaExamineStep> stepList;


}
