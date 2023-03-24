package com.megazone.oa.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
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
@TableName("wk_oa_examine_step")
@ApiModel(value = "OaExamineStep object", description = "Approval step table")
public class OaExamineStep implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "step_id", type = IdType.AUTO)
	private Integer stepId;

	@ApiModelProperty(value = "Step type 1, the person in charge, 2, the designated user (any one), 3, the designated user (multiple countersigned), 4, the supervisor of the upper-level approver")
	private Integer stepType;

	@ApiModelProperty(value = "Approval ID")
	private Integer categoryId;

	@ApiModelProperty(value = "Approver IDs (separated by commas) ,1,2,")
	private String checkUserId;

	@ApiModelProperty(value = "sort")
	private Integer stepNum;

	@ApiModelProperty(value = "creation time")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;


	@TableField(exist = false)
	private List<SimpleUser> userList;


}
