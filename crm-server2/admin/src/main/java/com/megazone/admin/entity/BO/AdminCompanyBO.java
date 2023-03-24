package com.megazone.admin.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Data
@ToString
@ApiModel(value = "AdminCompany object", description = "Cloud platform company configuration table")
public class AdminCompanyBO {

	@ApiModelProperty(value = "Enterprise Name", required = true, example = "Conscience Enterprise")
	@NotEmpty
	private String companyName;

	@ApiModelProperty(value = "Enterprise Logo", example = "/logo")
	private String companyLogo;

	private String endTime;

	private Integer endDay;
}
