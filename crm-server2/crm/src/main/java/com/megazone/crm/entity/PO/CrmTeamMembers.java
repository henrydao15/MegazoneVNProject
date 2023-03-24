package com.megazone.crm.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * crm
 *
 * </p>
 *
 * @author
 * @since 2021-04-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_crm_team_members")
@ApiModel(value = "CrmTeamMembers", description = "crm")
public class CrmTeamMembers implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty(value = "，crm")
	private Integer type;

	@ApiModelProperty(value = "ID")
	private Integer typeId;

	@ApiModelProperty(value = "ID")
	private Long userId;

	@ApiModelProperty(value = " 1  2  3 ")
	private Integer power;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "")
	private Date expiresTime;


}
