package com.megazone.hrm.entity.VO;

import com.megazone.hrm.entity.PO.HrmEmployeeQuitInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PostInformationVO {

	@ApiModelProperty
	private List<InformationFieldVO> information;

	@ApiModelProperty
	private HrmEmployeeQuitInfo employeeQuitInfo;
}
