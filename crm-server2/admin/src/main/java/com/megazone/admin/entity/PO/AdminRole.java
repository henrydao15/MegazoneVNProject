package com.megazone.admin.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_admin_role")
@ApiModel(value = "AdminRole object", description = "role table")
public class AdminRole implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "role_id", type = IdType.AUTO)
	private Integer roleId;

	@ApiModelProperty(value = "name")
	private String roleName;

	@ApiModelProperty(value = "0, custom role 1, management role 2, customer management role 3, personnel role 4, finance role 5, project role 8, project custom role")
	private Integer roleType;

	@ApiModelProperty(value = "remarks")
	private String remark;

	@ApiModelProperty(value = "1 enable 0 disable")
	private Integer status;

	@ApiModelProperty(value = "Data permission 1, me, 2, me and subordinates, 3, this department, 4, this department and subordinate departments, 5, all ")
	private Integer dataType;

	@ApiModelProperty(value = "0 hidden 1 not hidden")
	private Integer isHidden;

	@ApiModelProperty(value = "1 system project administrator role 2 project management role 3 project editor role 4 project read-only role")
	private Integer label;


	@TableField(exist = false)
	@ApiModelProperty(value = "menu list")
	private Map<String, List<Integer>> rules;

	@TableField(exist = false)
	@ApiModelProperty(value = "List of menu ids")
	private List<Integer> menuIds = new ArrayList<>();

}
