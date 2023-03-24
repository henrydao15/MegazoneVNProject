package com.megazone.core.feign.oa.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wyq
 * @since 2020-05-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_oa_examine_record")
@ApiModel(value = "OaExamineRecord", description = "")
public class OaExamineRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	@TableId(value = "record_id", type = IdType.AUTO)
	private Integer recordId;

	@ApiModelProperty(value = "ID")
	private Integer examineId;

	@ApiModelProperty(value = "ID")
	private Integer examineStepId;

	@ApiModelProperty(value = " 0  1  2  3  4 ")
	private Integer examineStatus;

	@ApiModelProperty(value = "")
	private Long createUser;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	@ApiModelProperty(value = "")
	private String remarks;

	@TableField(exist = false)
	private List<ExamineLogUserVO> userList;


}
