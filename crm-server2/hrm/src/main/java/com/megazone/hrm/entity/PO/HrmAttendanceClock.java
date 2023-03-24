package com.megazone.hrm.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @since 2020-12-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_hrm_attendance_clock")
@ApiModel(value = "HrmAttendanceClock", description = "")
public class HrmAttendanceClock implements Serializable {

	private static final long serialVersionUID = 1L;

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

	@ApiModelProperty
	@TableField(exist = false)
	private String employeeName;


}
