package com.megazone.crm.entity.VO;

import com.megazone.crm.entity.PO.CrmExamine;
import com.megazone.crm.entity.PO.CrmExamineStep;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class CrmQueryExamineStepVO extends CrmExamine {

	@ApiModelProperty(value = "")
	private Long examineUser;

	@ApiModelProperty(value = "")
	private String examineUserName;

	@ApiModelProperty(value = "")
	private List<CrmExamineStep> stepList;
}
