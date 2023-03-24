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
 * @since 2020-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_crm_print_record")
@ApiModel(value = "CrmPrintRecord", description = "")
public class CrmPrintRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	@TableId(value = "record_id", type = IdType.AUTO)
	private Integer recordId;

	private Integer crmType;

	private Integer typeId;

	@ApiModelProperty(value = "id")
	private Integer templateId;

	@ApiModelProperty(value = "")
	@TableField(exist = false)
	private String templateName;

	@ApiModelProperty(value = "")
	private String recordContent;

	@ApiModelProperty(value = "id")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "")
	@TableField(exist = false)
	private String createUserName;


}
