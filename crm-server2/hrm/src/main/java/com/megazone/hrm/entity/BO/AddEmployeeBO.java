package com.megazone.hrm.entity.BO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
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
@ApiModel(value = "", description = "")
public class AddEmployeeBO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Id")
	private Integer employeeId;

	@ApiModelProperty(value = "")
	@NotBlank(message = "")
	private String employeeName;

	@ApiModelProperty(value = "")
	private String mobile;

	@ApiModelProperty(value = " 1  2  3  4  5 ")
	private Integer idType;

	@ApiModelProperty(value = "")
	private String idNumber;

	@ApiModelProperty(value = " 1  2 ")
	private Integer sex;

	@ApiModelProperty(value = "")
	private String email;

	@ApiModelProperty(value = "")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+9", pattern = "yyyy-MM-dd")
	private Date entryTime;

	@ApiModelProperty(value = " 0 ")
	private Integer probation;

	@ApiModelProperty(value = "")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+9", pattern = "yyyy-MM-dd")
	private Date becomeTime;

	@ApiModelProperty(value = "")
	private String jobNumber;

	@ApiModelProperty(value = "ID")
	private Integer deptId;

	@ApiModelProperty(value = "ID")
	private Integer parentId;

	@ApiModelProperty(value = "")
	private String post;

	@ApiModelProperty(value = "")
	private String workCity;

	@ApiModelProperty(value = " 1  2 ")
	private Integer employmentForms;

	@ApiModelProperty(value = " 1 2  3 4 5 6 7 8 9 10")
	private Integer status;

	@ApiModelProperty(value = "id")
	private Integer candidateId;

	@ApiModelProperty
	private Integer entryStatus;

}
