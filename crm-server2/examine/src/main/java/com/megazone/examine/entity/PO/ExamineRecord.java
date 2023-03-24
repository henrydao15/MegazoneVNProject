package com.megazone.examine.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_examine_record")
@ApiModel(value = "ExamineRecord", description = "")
public class ExamineRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	@TableId(value = "record_id", type = IdType.AUTO)
	private Integer recordId;

	@ApiModelProperty(value = "ID")
	private Long examineId;

	@ApiModelProperty(value = "")
	private Integer label;

	@ApiModelProperty(value = "ID")
	private Integer flowId;

	@ApiModelProperty(value = "ID")
	private Integer typeId;

	@ApiModelProperty(value = " 0、1、2、3 4: 5  6  7  8 ")
	private Integer examineStatus;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;

	@ApiModelProperty(value = "")
	private Long updateUserId;


}
