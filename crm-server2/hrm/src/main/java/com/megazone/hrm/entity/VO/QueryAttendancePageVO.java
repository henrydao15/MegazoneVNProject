package com.megazone.hrm.entity.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class QueryAttendancePageVO {
	@ApiModelProperty(value = "id")
	@TableId(value = "clock_id", type = IdType.AUTO)
	private Integer clockId;

	@ApiModelProperty(value = "")
	private Integer clockEmployeeId;

	@ApiModelProperty(value = "")
	private Date clockTime;

	@ApiModelProperty(value = " 1  2 ")
	private Integer clockType;

	@ApiModelProperty(value = "")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date attendanceTime;

	@ApiModelProperty(value = " 1 2")
	private Integer type;

	@ApiModelProperty(value = "")
	private String address;

	@ApiModelProperty(value = "")
	private String lng;

	@ApiModelProperty(value = "")
	private String lat;

	@ApiModelProperty(value = "")
	private String remark;

	@ApiModelProperty(value = "")
	private String employeeName;

	@ApiModelProperty(value = "")
	private String jobNumber;

	@ApiModelProperty(value = "")
	private String deptName;

	@ApiModelProperty(value = "")
	private String post;
}
