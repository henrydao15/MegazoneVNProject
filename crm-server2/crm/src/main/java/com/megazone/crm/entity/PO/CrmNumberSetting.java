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
 * @since 2020-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_crm_number_setting")
@ApiModel(value = "CrmNumberSetting", description = "")
public class CrmNumberSetting implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	@TableId(value = "setting_id", type = IdType.AUTO)
	private Integer settingId;

	@ApiModelProperty(value = "id")
	private Integer pid;

	@ApiModelProperty(value = "")
	private Integer sort;

	@ApiModelProperty(value = " 1 2 3")
	private Integer type;

	@ApiModelProperty(value = "")
	private String value;

	@ApiModelProperty(value = "")
	private Integer increaseNumber;

	@ApiModelProperty(value = " 1 2 3 4")
	private Integer resetType;

	@ApiModelProperty(value = "")
	private Integer lastNumber;

	@ApiModelProperty(value = "")
	private Date lastDate;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "id")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;


}
