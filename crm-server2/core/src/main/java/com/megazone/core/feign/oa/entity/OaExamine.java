package com.megazone.core.feign.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-07-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_oa_examine")
@ApiModel(value = "OaExamine", description = "")
public class OaExamine implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "examine_id", type = IdType.AUTO)
	private Integer examineId;

	@ApiModelProperty(value = "")
	private Integer categoryId;

	@ApiModelProperty(value = "")
	private String content;

	@ApiModelProperty(value = "")
	private String remark;

	@ApiModelProperty
	private Integer examineStatus;

	@ApiModelProperty(value = "")
	private String typeId;

	@ApiModelProperty(value = "、")
	private BigDecimal money;

	@ApiModelProperty(value = "")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;

	@ApiModelProperty(value = "")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;

	@ApiModelProperty(value = "")
	private BigDecimal duration;

	@ApiModelProperty(value = "ID")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.UPDATE)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	@ApiModelProperty(value = "id")
	private String batchId;

	@ApiModelProperty
	private Integer examineRecordId;

}
