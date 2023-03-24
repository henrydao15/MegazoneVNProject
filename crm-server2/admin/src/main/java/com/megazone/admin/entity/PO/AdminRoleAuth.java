package com.megazone.admin.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_admin_role_auth")
@ApiModel(value = "AdminRoleAuth object", description = "Role table")
public class AdminRoleAuth implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty(value = "role ID")
	private Integer roleId;

	@ApiModelProperty(value = "Menu ID")
	private Integer menuId;

	@ApiModelProperty(value = "Character ID that can be queried")
	private Integer authRoleId;

	@ApiModelProperty(value = "remarks")
	private String remark;
}
