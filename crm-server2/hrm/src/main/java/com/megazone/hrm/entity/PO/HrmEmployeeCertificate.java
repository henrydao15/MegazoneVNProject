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
@TableName("wk_hrm_employee_certificate")
@ApiModel(value = "HrmEmployeeCertificate", description = "")
@RangeValidated
public class HrmEmployeeCertificate implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "certificate_id", type = IdType.AUTO)
	private Long certificateId;

	@ApiModelProperty(value = "id")
	private Integer employeeId;

	@ApiModelProperty(value = "")
	private String certificateName;

	@ApiModelProperty(value = "")
	private String certificateLevel;

	@ApiModelProperty(value = "")
	private String certificateNum;

	@ApiModelProperty(value = "")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+9", pattern = "yyyy-MM-dd")
	private Date startTime;

	@ApiModelProperty(value = "")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+9", pattern = "yyyy-MM-dd")
	private Date endTime;

	@ApiModelProperty(value = "")
	private String issuingAuthority;

	@ApiModelProperty(value = "")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+9", pattern = "yyyy-MM-dd")
	private Date issuingTime;

	@ApiModelProperty(value = "")
	private String remarks;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	private Integer sort;


}
