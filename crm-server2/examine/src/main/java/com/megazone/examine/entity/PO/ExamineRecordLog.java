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
 * @since 2020-12-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_examine_record_log")
@ApiModel(value = "ExamineRecordLog", description = "")
public class ExamineRecordLog implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "log_id", type = IdType.AUTO)
	private Integer logId;

	@ApiModelProperty(value = "ID")
	private Long examineId;

	@ApiModelProperty(value = "ID")
	private Integer flowId;

	@ApiModelProperty(value = "ID")
	private Integer recordId;

	@ApiModelProperty(value = "1  2  3 ")
	private Integer type;

	@ApiModelProperty(value = "")
	private String remarks;

	@ApiModelProperty(value = "")
	private Integer sort;

	@ApiModelProperty(value = "0、1、2、3 4: 5  6  7  8 ")
	private Integer examineStatus;

	@ApiModelProperty(value = "ID")
	private Long examineUserId;

	@ApiModelProperty(value = "ID")
	private Integer examineRoleId;

	@ApiModelProperty(value = "ID")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;

	@ApiModelProperty(value = "ID")
	private String batchId;


}
