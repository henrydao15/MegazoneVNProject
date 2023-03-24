package com.megazone.work.entity.VO;

import com.megazone.core.feign.admin.entity.SimpleUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author wyq
 */
@Data
@Accessors(chain = true)
@ApiModel
public class WorkStatsVO {
	@ApiModelProperty
	private List<WorkClassStatsVO> classStatistics;

	@ApiModelProperty
	private List<WorkLabelStatsVO> labelStatistics;

	@ApiModelProperty
	private List<WorkUserStatsVO> memberTaskStatistics;

	@ApiModelProperty
	private List<SimpleUser> ownerList;

	@ApiModelProperty
	private WorkTaskStatsVO taskStatistics;

	@ApiModelProperty
	private List<SimpleUser> userList;
}
