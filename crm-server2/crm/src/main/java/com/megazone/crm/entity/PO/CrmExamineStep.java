package com.megazone.crm.entity.PO;

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

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_crm_examine_step")
@ApiModel(value = "CrmExamineStep", description = "")
public class CrmExamineStep implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "step_id", type = IdType.AUTO)
	private Long stepId;

	@ApiModelProperty(value = "1、，2、（），3、（）,4、")
	private Integer stepType;

	@ApiModelProperty(value = "ID")
	private Integer examineId;

	@ApiModelProperty(value = "ID () ,1,2,")
	private String checkUserId;

	@ApiModelProperty(value = "")
	private Integer stepNum;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "")
	private String remarks;


	@TableField(exist = false)
	@ApiModelProperty
	private List<SimpleUser> userList;


}
