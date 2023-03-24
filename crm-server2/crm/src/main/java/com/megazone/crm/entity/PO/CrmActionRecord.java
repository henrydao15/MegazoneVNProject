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
 *
 * </p>
 *
 * @author
 * @since 2020-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_crm_action_record")
@ApiModel(value = "CrmActionRecord", description = "")
public class CrmActionRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty(value = "ID")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "ip")
	private String ipAddress;

	@ApiModelProperty(value = "")
	private Integer types;

	@ApiModelProperty(value = "ID")
	private Integer actionId;

	@ApiModelProperty(value = "")
	private String object;

	@ApiModelProperty(value = "")
	private Integer behavior;

	@ApiModelProperty(value = "")
	private String content;

	@ApiModelProperty(value = "")
	private String detail;


}
