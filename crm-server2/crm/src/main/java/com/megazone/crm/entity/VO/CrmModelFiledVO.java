package com.megazone.crm.entity.VO;

import cn.hutool.core.util.StrUtil;
import com.megazone.core.common.FieldEnum;
import com.megazone.crm.entity.PO.CrmFieldExtend;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author crm，
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel
public class CrmModelFiledVO implements Serializable {

	@ApiModelProperty(value = "ID")
	private Integer fieldId;
	@ApiModelProperty(value = "")
	private String fieldName;
	@ApiModelProperty(value = "")
	private String formType;
	@ApiModelProperty(value = "")
	private String name;
	@ApiModelProperty(value = " 1  2  3  4 5  6  7   8  9  10  11  12  13  14  15 16  17  18  19  20  21 ")
	private Integer type;
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
	@ApiModelProperty(value = "，，，")
	private String options;
	@ApiModelProperty(value = "")
	private Integer fieldType;
	@ApiModelProperty(value = "")
	private List<Object> setting = new ArrayList<>();
	@ApiModelProperty(value = " 1 2 3")
	private Integer authLevel;
	@ApiModelProperty(value = "value")
	private Object value;
	@ApiModelProperty(value = "")
	private Integer sysInformation;
	@ApiModelProperty
	private Integer autoGeneNumber;
	@ApiModelProperty(value = " ")
	private Integer sorting;
	@ApiModelProperty(value = "  1 100%  2 75%  3 50%  4 25%")
	private Integer stylePercent;
	@ApiModelProperty(value = "")
	private String maxNumRestrict;
	@ApiModelProperty(value = "")
	private String minNumRestrict;
	@ApiModelProperty(value = "，//、")
	private Integer precisions;
	@ApiModelProperty(value = " ： 1,1")
	private String formPosition;
	@ApiModelProperty(value = "id，")
	private Integer formAssistId;
	/**
	 *
	 */
	@ApiModelProperty(value = "x")
	private Integer xAxis;
	@ApiModelProperty(value = "y")
	private Integer yAxis;
	@ApiModelProperty(value = "")
	private Map<String, Object> optionsData;
	@ApiModelProperty(value = "")
	private List<CrmFieldExtend> fieldExtendList;
	@ApiModelProperty(value = "   - ： | /： ")
	private String remark;

	public CrmModelFiledVO() {

	}

	public CrmModelFiledVO(String fieldName, String name) {
		this.fieldName = fieldName;
		this.name = name;
	}

	public CrmModelFiledVO(String fieldName, FieldEnum fieldEnum) {
		this.fieldName = StrUtil.toCamelCase(fieldName);
		this.type = fieldEnum.getType();
		this.formType = fieldEnum.getFormType();
	}

	public CrmModelFiledVO(String fieldName, FieldEnum fieldEnum, Integer fieldType) {
		this.fieldName = StrUtil.toCamelCase(fieldName);
		this.type = fieldEnum.getType();
		this.formType = fieldEnum.getFormType();
		this.fieldType = fieldType;
	}

	public CrmModelFiledVO(String fieldName, FieldEnum fieldEnum, String name, Integer fieldType) {
		this.fieldName = StrUtil.toCamelCase(fieldName);
		this.type = fieldEnum.getType();
		this.formType = fieldEnum.getFormType();
		this.name = name;
		this.fieldType = fieldType;
	}

	public CrmModelFiledVO setFieldName(String fieldName) {
		this.fieldName = StrUtil.toCamelCase(fieldName);
		return this;
	}

}
