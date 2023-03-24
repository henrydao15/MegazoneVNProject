package com.megazone.examine.entity.PO;

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

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_examine_condition_data")
@ApiModel(value = "ExamineConditionData", description = "")
public class ExamineConditionData implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty(value = "ID")
	private Integer conditionId;

	@ApiModelProperty(value = "ID")
	private Integer flowId;

	@ApiModelProperty(value = "ID")
	private Integer fieldId;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = " 1  2  3  4 5  6  7   8  9  10  11  12  13  14  15 16  17  18  19  20  21 ")
	private Integer type;

	@ApiModelProperty(value = "")
	private String fieldName;

	@ApiModelProperty(value = " 1  2  3  4  5  6  7  8 // 11")
	private Integer conditionType;

	@ApiModelProperty(value = "，json")
	private String value;

	@ApiModelProperty(value = "ID")
	private String batchId;


	@TableField(exist = false)
	@ApiModelProperty(value = "json")
	private String backupValue;


}
