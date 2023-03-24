package com.megazone.admin.entity.VO;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@ApiModel(value = "Department query object", description = "Department object")
public class AdminDeptVO {

	@ApiModelProperty(value = "Department ID")
	private Integer deptId;

	@ApiModelProperty(value = "Department ID2")
	private Integer id;

	@ApiModelProperty(value = "Superior department ID, 0 is the highest level")
	private Integer pid;

	@ApiModelProperty(value = "Department Name")
	private String name;

	@ApiModelProperty(value = "department label")
	private String label;
	@ApiModelProperty(value = "Department Head")
	private Long ownerUserId;
	@ApiModelProperty(value = "Subordinate department list")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<AdminDeptVO> children;
	@ApiModelProperty
	private Integer currentNum;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.label = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
		this.id = deptId;
	}
}
