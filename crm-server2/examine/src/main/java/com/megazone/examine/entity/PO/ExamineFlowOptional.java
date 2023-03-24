package com.megazone.examine.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("wk_examine_flow_optional")
@ApiModel(value = "ExamineFlowOptional", description = "")
public class ExamineFlowOptional implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty(value = "ID")
	private Integer flowId;

	@ApiModelProperty(value = "ID")
	private Long userId;

	@ApiModelProperty(value = "ID")
	private Integer roleId;

	@ApiModelProperty(value = " 1  2 ")
	private Integer chooseType;

	@ApiModelProperty
	private Integer rangeType;

	@ApiModelProperty(value = "1  2  3 ")
	private Integer type;

	@ApiModelProperty(value = "")
	private Integer sort;

	@ApiModelProperty(value = "ID")
	private String batchId;


}