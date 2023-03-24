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
@TableName("wk_hrm_employee_education_experience")
@ApiModel(value = "HrmEmployeeEducationExperience", description = "")
@RangeValidated(minFieldName = "admissionTime", maxFieldName = "graduationTime", message = "")
public class HrmEmployeeEducationExperience implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "education_id", type = IdType.AUTO)
	private Integer educationId;

	private Integer employeeId;

	@ApiModelProperty(value = " 1、2、3、4、5、6、7、8、9、10、11、12")
	private Integer education;

	@ApiModelProperty(value = "")
	private String graduateSchool;

	@ApiModelProperty(value = "")
	private String major;

	@ApiModelProperty(value = "")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+9", pattern = "yyyy-MM-dd")
	private Date admissionTime;

	@ApiModelProperty(value = "")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+9", pattern = "yyyy-MM-dd")
	private Date graduationTime;

	@ApiModelProperty(value = " 1 、2、3、4、5")
	private Integer teachingMethods;

	@ApiModelProperty(value = " 0  1 ")
	private Integer isFirstDegree;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	private Integer sort;


}
