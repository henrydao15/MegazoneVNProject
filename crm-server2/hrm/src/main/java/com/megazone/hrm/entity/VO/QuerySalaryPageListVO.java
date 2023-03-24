package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class QuerySalaryPageListVO {

	@ApiModelProperty
	private Integer sEmpRecordId;

	@ApiModelProperty(value = "id")
	private Integer employeeId;

	@ApiModelProperty(value = "")
	private String jobNumber;
	@ApiModelProperty(value = "")
	private String employeeName;

	@ApiModelProperty(value = "")
	private String deptName;

	@ApiModelProperty(value = "")
	private String post;

	@ApiModelProperty(value = "")
	private BigDecimal actualWorkDay;

	@ApiModelProperty(value = "")
	private BigDecimal needWorkDay;

	@ApiModelProperty
	private List<SalaryValue> salary;

	private Integer isDel;

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SalaryValue {
		@ApiModelProperty
		private Integer id;
		@ApiModelProperty
		private Integer code;
		@ApiModelProperty
		private String value;
		@ApiModelProperty
		private Integer isFixed;
		@ApiModelProperty
		private String name;
	}
}
