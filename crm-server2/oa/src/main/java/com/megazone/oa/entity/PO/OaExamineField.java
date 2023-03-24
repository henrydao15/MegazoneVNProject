package com.megazone.oa.entity.PO;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.megazone.core.common.Const;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_oa_examine_field")
@ApiModel(value = "OaExamineField object", description = "custom field table")
public class OaExamineField implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Primary Key ID")
	@TableId(value = "field_id", type = IdType.AUTO)
	private Integer fieldId;

	@ApiModelProperty(value = "Custom Field English ID")
	private String fieldName;

	@ApiModelProperty(value = "field name")
	private String name;

	@ApiModelProperty(value = "Field Type 1 Single-line text 2 Multi-line text 3 Radio 4 Date 5 Number 6 Decimal 7 Mobile 8 File 9 Multiple choice 10 Person 11 Attachment 12 Department 13 Date and time 14 Email 15 Customer 16 Business opportunity 17 Contact 18 Map 19 Product Type 20 Contract 21 Payment Plan")
	private Integer type;

	@ApiModelProperty(value = "Field Description Special Purpose - Detail Form: Add Field Description | Single Choice/Multiple Choice: Identify the Open Logic Form")
	private String remark;

	@ApiModelProperty(value = "input prompt")
	private String inputTips;

	@ApiModelProperty(value = "maximum length")
	private Integer maxLength;

	@ApiModelProperty(value = "default value")
	@TableField(fill = FieldFill.UPDATE)
	private Object defaultValue;

	@ApiModelProperty(value = "is unique 1 yes 0 no")
	private Integer isUnique;

	@ApiModelProperty(value = "Required 1 Yes 0 No")
	private Integer isNull;

	@ApiModelProperty(value = "sort from small to large")
	private Integer sorting;

	@ApiModelProperty(value = "If the type is an option, it cannot be empty here, multiple options are separated by ,")
	private String options;

	@ApiModelProperty(value = "Can delete and modify 0 modify and delete 1 modify 2 delete 3 none")
	private Integer operating;

	@ApiModelProperty(value = "whether hidden 0 not hidden 1 hidden")
	private Integer isHidden;

	@ApiModelProperty(value = "Last Modified Time")
	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;

	@ApiModelProperty(value = "Approval ID label is 10 required")
	private Integer examineCategoryId;

	@ApiModelProperty(value = "Field source 0. Custom 1. Original fixed 2 Original field but the value exists in the extension table")
	private Integer fieldType;

	@ApiModelProperty(value = "Style percentage 1 100% 2 75% 3 50% 4 25%")
	private Integer stylePercent;

	@ApiModelProperty(value = "Precision, the maximum allowed decimal places/map precision/detailed form, logical form display mode")
	private Integer precisions;

	@ApiModelProperty(value = "Form positioning coordinate format: 1,1")
	private String formPosition;

	@ApiModelProperty(value = "limited maximum value")
	private String maxNumRestrict;

	@ApiModelProperty(value = "Limited minimum value")
	private String minNumRestrict;

	@ApiModelProperty(value = "Form auxiliary id, front-end generated")
	private Integer formAssistId;

	@TableField(exist = false)
	private String formType;

	@TableField(exist = false)
	private List<String> setting;

	@TableField(exist = false)
	private Object value;


	/**
	 * coordinates
	 */
	@TableField(exist = false)
	@ApiModelProperty(value = "x-axis")
	@JsonIgnore
	private Integer xAxis;

	@TableField(exist = false)
	@ApiModelProperty(value = "y-axis")
	@JsonIgnore
	private Integer yAxis;

	@TableField(exist = false)
	@ApiModelProperty(value = "Logical form data")
	private Map<String, Object> optionsData;

	@TableField(exist = false)
	@ApiModelProperty(value = "Extension Field")
	private List<OaExamineFieldExtend> fieldExtendList;


	public void setFormPosition(String formPosition) {
		this.formPosition = formPosition;
		if (StrUtil.isNotEmpty(formPosition)) {
			if (formPosition.contains(Const.SEPARATOR)) {
				String[] axisArr = formPosition.split(Const.SEPARATOR);
				if (axisArr.length == 2) {
					if (axisArr[0].matches("[0-9]+") && axisArr[1].matches("[0-9]+")) {
						this.xAxis = Integer.valueOf(axisArr[0]);
						this.yAxis = Integer.valueOf(axisArr[1]);
						return;
					}
				}
			}
		}
		this.xAxis = -1;
		this.yAxis = -1;
	}
}
