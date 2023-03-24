package com.megazone.hrm.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
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
@TableName("wk_hrm_recruit_candidate")
@ApiModel(value = "HrmRecruitCandidate", description = "")
public class HrmRecruitCandidate implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	@TableId(value = "candidate_id", type = IdType.AUTO)
	private Integer candidateId;

	@ApiModelProperty(value = "")
	private String candidateName;

	@ApiModelProperty(value = "")
	private String mobile;

	@ApiModelProperty(value = " 1 2")
	private Integer sex;

	@ApiModelProperty(value = "")
	@Min(value = 0, message = "0")
	private Integer age;

	@ApiModelProperty(value = "")
	private String email;

	@ApiModelProperty(value = "id")
	private Integer postId;

	@ApiModelProperty(value = " ")
	private Integer stageNum;

	@ApiModelProperty(value = "")
	@Min(value = 0, message = "0")
	private Integer workTime;

	@ApiModelProperty(value = " 1 2 3 4 5 6 7")
	private Integer education;

	@ApiModelProperty(value = "")
	private String graduateSchool;

	@ApiModelProperty(value = "")
	private String latestWorkPlace;

	@ApiModelProperty(value = "")
	private Integer channelId;

	@ApiModelProperty(value = "")
	private String remark;

	@ApiModelProperty(value = " 1  2 3 4 5offer 6 7 8")
	private Integer status;

	@ApiModelProperty(value = "")
	private String eliminate;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "id")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "")
	private Date statusUpdateTime;

	@ApiModelProperty(value = "")
	private Date entryTime;

	@ApiModelProperty(value = "id")
	private String batchId;


	@ApiModelProperty
	@TableField(exist = false)
	private String postName;

	@ApiModelProperty
	@TableField(exist = false)
	private String channelName;


}
