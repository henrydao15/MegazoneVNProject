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
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-06-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_hrm_recruit_post")
@ApiModel(value = "HrmRecruitPost", description = "")
@RangeValidated(minFieldName = {"minSalary", "minAge"}, maxFieldName = {"maxSalary", "maxAge"})
public class HrmRecruitPost implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	@TableId(value = "post_id", type = IdType.AUTO)
	private Integer postId;

	@ApiModelProperty(value = "")
	private String postName;

	@ApiModelProperty(value = "id")
	private Integer deptId;

	@ApiModelProperty(value = " 1  2 3")
	private Integer jobNature;

	@ApiModelProperty(value = "")
	private String city;

	@ApiModelProperty(value = "")
	private Integer recruitNum;

	@ApiModelProperty(value = "")
	private String reason;

	@ApiModelProperty(value = " 1 2 3 4 5 6")
	private Integer workTime;

	@ApiModelProperty(value = " 1 2 3 4 5 6")
	private Integer educationRequire;

	@ApiModelProperty(value = " -1 ")
	@TableField(value = "min_salary", updateStrategy = FieldStrategy.IGNORED)
	private BigDecimal minSalary;

	@ApiModelProperty(value = " -1 ")
	@TableField(value = "max_salary", updateStrategy = FieldStrategy.IGNORED)
	private BigDecimal maxSalary;

	@ApiModelProperty(value = " 1 / 2 /")
	private Integer salaryUnit;

	@ApiModelProperty(value = " -1 ")
	@TableField(value = "min_age", updateStrategy = FieldStrategy.IGNORED)
	private Integer minAge;

	@ApiModelProperty(value = " -1 ")
	@TableField(value = "max_age", updateStrategy = FieldStrategy.IGNORED)
	private Integer maxAge;

	@ApiModelProperty(value = "")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+9", pattern = "yyyy-MM-dd")
	private Date latestEntryTime;

	@ApiModelProperty(value = "id")
	private Integer ownerEmployeeId;

	@ApiModelProperty(value = "")
	private String interviewEmployeeIds;

	@ApiModelProperty(value = "")
	private String description;

	@ApiModelProperty(value = " 1 2 ")
	private Integer emergencyLevel;

	@ApiModelProperty(value = "")
	private Integer postTypeId;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "id")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "id")
	private String batchId;

	@ApiModelProperty(value = "0   1 ")
	private Integer status;


	@TableField(exist = false)
	private String deptName;


}
