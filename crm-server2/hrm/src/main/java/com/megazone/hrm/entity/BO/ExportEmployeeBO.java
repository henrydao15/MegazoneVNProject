package com.megazone.hrm.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExportEmployeeBO {

	@ApiModelProperty(value = " 1 2  3 4 5 6 7 8 9 10 11  12 ")
	private Integer status;

	private List<Integer> employeeIds;
}
