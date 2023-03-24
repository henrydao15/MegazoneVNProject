package com.megazone.oa.entity.BO;

import com.megazone.oa.entity.PO.OaExamineField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ToString
@ApiModel(value = "ExamineField save object", description = "Approve custom field table")
public class ExamineFieldBO {

	@ApiModelProperty(value = "Approval type id")
	@NotNull
	private Integer categoryId;

	@ApiModelProperty(value = "ExamineField object list")
	private List<OaExamineField> data;
}
