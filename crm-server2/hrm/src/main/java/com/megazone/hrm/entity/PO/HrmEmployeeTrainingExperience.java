package com.megazone.hrm.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.megazone.core.common.RangeValidated;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

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
@TableName("wk_hrm_employee_training_experience")
@ApiModel(value = "HrmEmployeeTrainingExperience", description = "")
@RangeValidated
public class HrmEmployeeTrainingExperience implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "training_id", type = IdType.AUTO)
	private Integer trainingId;

	private Integer employeeId;

	@ApiModelProperty(value = "")
	private String trainingCourse;

	@ApiModelProperty(value = "")
	private String trainingOrganName;

	@ApiModelProperty(value = "")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+9", pattern = "yyyy-MM-dd")
	private Date startTime;

	@ApiModelProperty(value = "")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+9", pattern = "yyyy-MM-dd")
	private Date endTime;

	@ApiModelProperty(value = "")
	private String trainingDuration;

	@ApiModelProperty(value = "")
	private String trainingResults;

	@ApiModelProperty(value = "")
	private String trainingCertificateName;

	@ApiModelProperty(value = "")
	private String remarks;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "")
	private Integer sort;


}
