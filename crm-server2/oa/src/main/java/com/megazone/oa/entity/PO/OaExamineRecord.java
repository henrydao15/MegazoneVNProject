package com.megazone.oa.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import com.megazone.oa.entity.VO.ExamineLogUserVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_oa_examine_record")
@ApiModel(value = "OaExamineRecord object", description = "Audit record table")
public class OaExamineRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Audit Record ID")
	@TableId(value = "record_id", type = IdType.AUTO)
	private Integer recordId;

	@ApiModelProperty(value = "Approval ID")
	private Integer examineId;

	@ApiModelProperty(value = "Current approval step ID")
	private Integer examineStepId;

	@ApiModelProperty(value = "Review Status 0 Unreviewed 1 Review Passed 2 Review Rejected 3 Reviewed 4 Withdrawn")
	private Integer examineStatus;

	@ApiModelProperty(value = "Creator")
	private Long createUser;

	@ApiModelProperty(value = "creation time")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "Audit Notes")
	private String remarks;

	@TableField(exist = false)
	private List<ExamineLogUserVO> userList;

}
