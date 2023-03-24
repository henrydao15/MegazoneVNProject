package com.megazone.hrm.entity.BO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "")
public class AddDeptBO {

	@TableId(value = "dept_id", type = IdType.AUTO)
	private Integer deptId;

	@ApiModelProperty(value = "ID 0")
	private Integer pid;

	@ApiModelProperty(value = " 1  2 ")
	@NotNull(message = "")
	private Integer deptType;

	@ApiModelProperty(value = "")
	@NotBlank(message = "")
	private String name;

	@ApiModelProperty(value = "")
	@NotBlank(message = "")
	private String code;

	@ApiModelProperty(value = "ID")
	private Integer mainEmployeeId;

	@ApiModelProperty(value = "")
	private Integer leaderEmployeeId;

}
