package com.megazone.hrm.entity.VO;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.megazone.core.common.Const;
import com.megazone.hrm.entity.PO.HrmFieldExtend;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class InformationFieldVO {

	@ApiModelProperty(value = "ID")
	private Integer fieldId;

	@ApiModelProperty(value = "")
	private String fieldName;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = " 1  2  3  4 5  6  7   8  9    10  11  12 ")
	private Integer type;

	@ApiModelProperty(value = " 0  1 hrm 2 hrm 3 hrm 4  5  6 ")
	private Integer componentType;

	@ApiModelProperty(value = "，，kv")
	private String options;

	@ApiModelProperty(value = "")
	private String inputTips;

	@ApiModelProperty(value = " 0  1 ")
	private Integer isFixed;

	@ApiModelProperty(value = " 0  1 ")
	private Integer isUpdateValue;

	@ApiModelProperty(value = "")
	private Object defaultValue;
	@ApiModelProperty(value = " 1  0 ")
	private Integer isUnique;

	@ApiModelProperty(value = " 1  0 ")
	private Integer isNull;

	@ApiModelProperty
	private Object fieldValue;

	@ApiModelProperty
	private Object fieldValueDesc;

	@ApiModelProperty(value = " 0  1 ")
	private Integer isEmployeeUpdate;

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
	private List<Object> setting;

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

	@ApiModelProperty(value = "")
	private String remark;

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
