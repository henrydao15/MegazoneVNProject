package com.megazone.hrm.entity.PO;

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
 * @since 2020-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_hrm_recruit_interview")
@ApiModel(value = "HrmRecruitInterview", description = "")
public class HrmRecruitInterview implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	@TableId(value = "interview_id", type = IdType.AUTO)
	private Integer interviewId;

	@ApiModelProperty(value = "id")
	private Integer candidateId;

	@ApiModelProperty(value = " 1 2 3")
	@TableField(value = "type", updateStrategy = FieldStrategy.IGNORED)
	private Integer type;

	@ApiModelProperty(value = "")
	private Integer stageNum;

	@ApiModelProperty(value = "id")
	@TableField(value = "interview_employee_id", updateStrategy = FieldStrategy.IGNORED)
	private Integer interviewEmployeeId;

	@ApiModelProperty(value = "")
	@TableField(value = "other_interview_employee_ids", updateStrategy = FieldStrategy.IGNORED)
	private String otherInterviewEmployeeIds;

	@ApiModelProperty(value = "")
	private Date interviewTime;

	@ApiModelProperty(value = "")
	private String address;

	@ApiModelProperty(value = "")
	private String remark;

	@ApiModelProperty(value = " 1 2 3 4 ")
	private Integer result;

	@ApiModelProperty(value = "")
	private String evaluate;

	@ApiModelProperty(value = "")
	private String cancelReason;

	@ApiModelProperty(value = "id")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;


}
