package com.megazone.admin.entity.VO;

import com.megazone.admin.entity.PO.AdminRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@ApiModel
public class AdminRoleVO {

	@ApiModelProperty
	private Integer pid;

	@ApiModelProperty
	private String name;

	@ApiModelProperty
	private List<AdminRole> list;
}
