package com.megazone.admin.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("wk_admin_user_config")
@ApiModel(value = "AdminUserConfig object", description = "User configuration table")
public class AdminUserConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "setting_id", type = IdType.AUTO)
	private Integer settingId;

	private Long userId;

	@ApiModelProperty(value = "status, 0: not enabled 1: enabled")
	private Integer status;

	@ApiModelProperty(value = "setting name")
	private String name;

	@ApiModelProperty(value = "value")
	private String value;

	@ApiModelProperty(value = "description")
	private String description;


}
