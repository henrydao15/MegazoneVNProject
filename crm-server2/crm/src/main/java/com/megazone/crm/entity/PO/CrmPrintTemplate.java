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
 * @since 2020-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_crm_print_template")
@ApiModel(value = "CrmPrintTemplate", description = "")
public class CrmPrintTemplate implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	@TableId(value = "template_id ", type = IdType.AUTO)
	private Integer templateId;

	@ApiModelProperty(value = "")
	private String templateName;

	@ApiModelProperty(value = "")
	private Integer type;

	@ApiModelProperty(value = "")
	private String content;

	@ApiModelProperty(value = "id")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty
	@TableField(exist = false)
	private String createUserName;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;


}
