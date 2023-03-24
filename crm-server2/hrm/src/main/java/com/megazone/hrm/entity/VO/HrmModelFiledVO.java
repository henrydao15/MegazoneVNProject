package com.megazone.hrm.entity.VO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.megazone.hrm.entity.PO.HrmFieldExtend;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@ToString
@Accessors(chain = true)
@ApiModel
public class HrmModelFiledVO {
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

	@ApiModelProperty(value = "")
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

	@ApiModelProperty(value = " 0  1   2  3 ")
	private Integer isManageVisible;

	@ApiModelProperty(value = " 0  1  2  3 ")
	private Integer isManageRequired;

	@ApiModelProperty(value = "0 1 2data 3 4")
	private Integer fieldType;

	@ApiModelProperty(value = "")
	private String formType;

	@ApiModelProperty(value = "")
	private List<Object> setting = new ArrayList<>();

	@TableField(exist = false)
	private Object fieldValue;

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


}
