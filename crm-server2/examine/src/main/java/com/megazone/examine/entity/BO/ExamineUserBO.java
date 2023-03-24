package com.megazone.examine.entity.BO;

import com.megazone.examine.entity.PO.ExamineFlow;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@ApiModel
@Accessors(chain = true)
public class ExamineUserBO {

	@ApiModelProperty
	private List<Long> userList;

	@ApiModelProperty
	private Integer roleId;

	@ApiModelProperty
	private Integer type;

	@ApiModelProperty
	private ExamineFlow examineFlow;
}
