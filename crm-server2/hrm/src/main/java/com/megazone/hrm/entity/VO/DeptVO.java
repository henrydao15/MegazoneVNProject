package com.megazone.hrm.entity.VO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DeptVO {

	@ApiModelProperty(value = "id")
	private Integer deptId;

	@ApiModelProperty(value = "ID 0")
	private Integer pid;

	@ApiModelProperty(value = "")
	private String pName;

	@ApiModelProperty(value = "1  2 ")
	private Integer deptType;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = "")
	private String code;

	@ApiModelProperty(value = "ID")
	private Integer mainEmployeeId;

	@ApiModelProperty(value = "")
	private Integer leaderEmployeeId;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;


	@ApiModelProperty
	private String leadEmployeeName;

	@ApiModelProperty
	private String mainEmployeeName;

	@ApiModelProperty
	private Integer allNum;

	@ApiModelProperty
	private Integer fullTimeNum;

	@ApiModelProperty
	private Integer nuFullTimeNum;

	@ApiModelProperty
	private Integer myAllNum;

	@ApiModelProperty
	private Integer myFullTimeNum;

	@ApiModelProperty
	private Integer myNuFullTimeNum;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<DeptVO> children;

}
