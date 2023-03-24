package com.megazone.core.feign.crm.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
 * @since 2021-03-04
 */
@Data
public class CrmFieldPatch implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	private Integer id;

	@ApiModelProperty(value = "id")
	private Integer parentFieldId;

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

	@ApiModelProperty(value = "")
	private Integer operating;

	@ApiModelProperty(value = "  0 1")
	private Integer isHidden;

	@ApiModelProperty(value = "")
	private Date updateTime;

	@ApiModelProperty(value = "  0. 1. 2")
	private Integer fieldType;

	@ApiModelProperty(value = "%")
	private Integer stylePercent;

	@ApiModelProperty(value = "，")
	private Integer precisions;

	@ApiModelProperty(value = " ： 1,1")
	private String formPosition;

	@ApiModelProperty(value = "")
	private String maxNumRestrict;

	@ApiModelProperty(value = "")
	private String minNumRestrict;

	@ApiModelProperty(value = "id，")
	private Integer formAssistId;


	@ApiModelProperty(value = "")
	private String formType;

	@ApiModelProperty(value = "")
	private Map<String, Object> optionsData;

	@ApiModelProperty(value = "")
	private List<String> setting;

	@ApiModelProperty(value = "value")
	private Object value;

}
