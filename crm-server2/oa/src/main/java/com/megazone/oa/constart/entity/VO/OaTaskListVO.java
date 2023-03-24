package com.megazone.oa.constart.entity.VO;

import com.megazone.core.entity.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @author wyq
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel
public class OaTaskListVO {
	@ApiModelProperty
	private Integer allTask;

	@ApiModelProperty
	private Integer stopTask;

	@ApiModelProperty
	private BasePage<TaskInfoVO> page;

	private List<Map<String, Object>> exportList;
}
