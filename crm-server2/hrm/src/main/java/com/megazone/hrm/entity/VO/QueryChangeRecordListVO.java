package com.megazone.hrm.entity.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryChangeRecordListVO {
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty(value = "id")
	private Integer employeeId;

	@ApiModelProperty(value = " 1  2 ")
	private Integer recordType;

	@ApiModelProperty(value = " 1  2  3  4  5  6  7  8 ")
	private Integer changeReason;

	@ApiModelProperty(value = "")
	private String enableDate;

	@ApiModelProperty(value = "")
	private String beforeTotal;

	@ApiModelProperty(value = "")
	private String afterTotal;

	@ApiModelProperty(value = " 0  1  2 ")
	private Integer status;

	@ApiModelProperty
	private Integer employeeStatus;

	@ApiModelProperty(value = "")
	private String remarks;
}
