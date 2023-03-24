package com.megazone.crm.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author
 * @date 2020/12/2
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_crm_marketing_field")
@ApiModel(value = "CrmMarketingField", description = "")
public class CrmMarketingField implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	@TableId(value = "field_id", type = IdType.AUTO)
	private Integer fieldId;

	@ApiModelProperty(value = "")
	private String fieldName;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = " 1  2  3  4 5  6  7   8  9  10  11  12  13  14  15 16  17  18  19  20  21 ")
	private Integer type;

	@ApiModelProperty(value = "")
	private String remark;

	@ApiModelProperty(value = "")
	private String inputTips;

	@ApiModelProperty(value = "")
	private Integer maxLength;

	@ApiModelProperty(value = "")
	private Object defaultValue;

	@ApiModelProperty(value = " 1  0 ")
	private Integer isUnique;

	@ApiModelProperty(value = " 1  0 ")
	private Integer isNull;

	@ApiModelProperty(value = " ")
	private Integer sorting;

	@ApiModelProperty(value = "，，，")
	private String options;

	@ApiModelProperty(value = " 0  1  2  3 ")
	private Integer operating;

	@ApiModelProperty(value = "  0 1")
	private Integer isHidden;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;

	@ApiModelProperty(value = "ID")
	private Integer formId;

	@ApiModelProperty(value = "  0. 1. 2")
	private Integer fieldType;

	@TableField(exist = false)
	private String formType;

	@TableField(exist = false)
	private List<String> setting;

	@TableField(exist = false)
	private Object value;
}
