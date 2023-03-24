package com.megazone.oa.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import com.megazone.core.feign.admin.entity.SimpleDept;
import com.megazone.core.feign.admin.entity.SimpleUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_oa_examine_category")
@ApiModel(value = "OaExamineCategory object", description = "Approval type table")
public class OaExamineCategory implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "category_id", type = IdType.AUTO)
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
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "1 to enable, 0 to disable")
	private Integer status;

	@ApiModelProperty(value = "1 is the system type and cannot be deleted")
	private Integer isSys;

	@ApiModelProperty(value = "1 fixed 2 optional")
	private Integer examineType;

	@ApiModelProperty(value = "Visible range (employee)")
	private String userIds;

	@ApiModelProperty(value = "Visible Range (Department)")
	private String deptIds;

	@ApiModelProperty(value = "creation time")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "update time")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;

	@ApiModelProperty(value = "1 deleted")
	private Integer isDeleted;

	@ApiModelProperty(value = "Delete time")
	private Date deleteTime;

	@ApiModelProperty(value = "Delete Person ID")
	private Long deleteUserId;


	@TableField(exist = false)
	private List<OaExamineStep> stepList;

	@TableField(exist = false)
	private List<SimpleUser> userList;

	@TableField(exist = false)
	private List<SimpleDept> deptList;

}
