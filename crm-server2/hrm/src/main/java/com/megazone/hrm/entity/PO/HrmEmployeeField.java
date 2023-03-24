package com.megazone.hrm.entity.PO;

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

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_hrm_employee_field")
@ApiModel(value = "HrmEmployeeField", description = "")
public class HrmEmployeeField implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	@TableId(value = "field_id", type = IdType.AUTO)
	private Integer fieldId;

	@ApiModelProperty(value = "")
	private String fieldName;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = " 1  2  3  4 5  6  7   8  9    10  11  12 ")
	private Integer type;

	@ApiModelProperty(value = " 0  1 hrm 2 hrm 3 hrm 4  5  6 ")
	private Integer componentType;

	@ApiModelProperty(value = " 1  2  3  4 ")
	private Integer label;

	@ApiModelProperty(value = " * 1  2  3  4  5 / 6  7  11  12  21  31  32 ")
	private Integer labelGroup;

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

	@ApiModelProperty(value = "，，kv")
	private String options;

	@ApiModelProperty(value = " 0  1 ")
	private Integer isFixed;

	@ApiModelProperty(value = "000000 (1:,2:,3:,4:,5:,6:)")
	private Integer operating;

	@ApiModelProperty(value = "  0 1")
	private Integer isHidden;

	@ApiModelProperty(value = " 0  1 ")
	private Integer isUpdateValue;

	@ApiModelProperty(value = " 0  1 ")
	private Integer isHeadField;

	@ApiModelProperty(value = " 0  1 ")
	private Integer isImportField;

	@ApiModelProperty(value = " 0  1 ")
	private Integer isEmployeeVisible;

	@ApiModelProperty(value = " 0  1 ")
	private Integer isEmployeeUpdate;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;

	@TableField(exist = false)
	private Object value;

	@ApiModelProperty(value = "  1 100%  2 75%  3 50%  4 25%")
	private Integer stylePercent;

	@ApiModelProperty(value = "，//、")
	@TableField(fill = FieldFill.UPDATE)
	private Integer precisions;

	@ApiModelProperty(value = " ： 1,1")
	private String formPosition;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.UPDATE)
	private String maxNumRestrict;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.UPDATE)
	private String minNumRestrict;

	@ApiModelProperty(value = "id，")
	private Integer formAssistId;

	@TableField(exist = false)
	@ApiModelProperty(value = "")
	private String formType;

	@TableField(exist = false)
	@ApiModelProperty(value = "")
	private List<?> setting;

	/**
	 *
	 */
	@TableField(exist = false)
	@ApiModelProperty(value = "x")
	@JsonIgnore
	private Integer xAxis;

	@TableField(exist = false)
	@ApiModelProperty(value = "y")
	@JsonIgnore
	private Integer yAxis;

	@TableField(exist = false)
	@ApiModelProperty(value = "")
	private Map<String, Object> optionsData;

	@TableField(exist = false)
	@ApiModelProperty(value = "")
	private List<HrmFieldExtend> fieldExtendList;

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
