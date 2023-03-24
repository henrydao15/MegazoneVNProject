package com.megazone.examine.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author
 * @date 2020/12/23
 */
@Data
public class ExaminePreviewVO {


	@ApiModelProperty(value = "")
	private String remarks;

	/**
	 *
	 */
	private List<ExamineFlowVO> examineFlowList;

}
