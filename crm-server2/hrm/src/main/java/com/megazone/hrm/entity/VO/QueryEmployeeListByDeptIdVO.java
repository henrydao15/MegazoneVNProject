package com.megazone.hrm.entity.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class QueryEmployeeListByDeptIdVO {

	@ApiModelProperty(value = "id")
	@TableId(value = "employee_id", type = IdType.AUTO)
	private Integer employeeId;

	@ApiModelProperty(value = "")
	private String employeeName;

	@ApiModelProperty(value = "")
	private String deptName;

	@ApiModelProperty(value = "")
	private String post;

	@ApiModelProperty(value = "")
	private String jobNumber;

	@ApiModelProperty(value = " 1  2 ")
	private Integer employmentForms;

	@ApiModelProperty(value = "")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date entryTime;
}
