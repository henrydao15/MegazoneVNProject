package com.megazone.bi.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_crm_achievement")
@ApiModel(value = "CrmAchievement object", description = "Performance target")
public class CrmAchievement implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "achievement_id", type = IdType.AUTO)
	private Integer achievementId;

	@ApiModelProperty(value = "Object ID")
	private Integer objId;

	@ApiModelProperty(value = "object name")
	@TableField(exist = false)
	private String objName;

	@ApiModelProperty(value = "Array of object IDs")
	@TableField(exist = false)
	private List<Integer> objIds;

	@ApiModelProperty(value = "1 company 2 departments 3 employees")
	private Integer type;

	@ApiModelProperty(value = "year")
	private String year;

	@ApiModelProperty(value = "January")
	private BigDecimal january;

	@ApiModelProperty(value = "February")
	private BigDecimal february;

	@ApiModelProperty(value = "March")
	private BigDecimal march;

	@ApiModelProperty(value = "April")
	private BigDecimal april;

	@ApiModelProperty(value = "May")
	private BigDecimal may;

	@ApiModelProperty(value = "June")
	private BigDecimal june;

	@ApiModelProperty(value = "July")
	private BigDecimal july;

	@ApiModelProperty(value = "August")
	private BigDecimal august;

	@ApiModelProperty(value = "September")
	private BigDecimal september;

	@ApiModelProperty(value = "October")
	private BigDecimal october;

	@ApiModelProperty(value = "November")
	private BigDecimal november;

	@ApiModelProperty(value = "December")
	private BigDecimal december;

	@ApiModelProperty(value = "1 sale (target) 2 payment (target)")
	private Integer status;

	@ApiModelProperty(value = "Year target")
	private BigDecimal yeartarget;

}
