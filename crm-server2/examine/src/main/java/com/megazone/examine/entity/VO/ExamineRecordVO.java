package com.megazone.examine.entity.VO;

import com.megazone.core.feign.admin.entity.SimpleUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author
 * @date 2020/12/18
 */
@Data
public class ExamineRecordVO {

	@ApiModelProperty(value = "")
	List<ExamineFlowDataVO> examineFlowList;
	@ApiModelProperty(value = "")
	private Integer label;
	@ApiModelProperty(value = "  1  0")
	private Integer isRecheck;
	@ApiModelProperty(value = "  1  0")
	private Integer isCheck;
	@ApiModelProperty(value = "")
	private SimpleUser createUser;


}
