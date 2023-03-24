package com.megazone.work.entity.BO;

import com.megazone.work.entity.PO.WorkUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wyq
 */
@Data
@ApiModel
public class SetWorkOwnerRoleBO {
	@ApiModelProperty
	private Integer workId;

	@ApiModelProperty
	private List<WorkUser> list;
}
