package com.megazone.crm.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@TableName("wk_crm_role_field")
@ApiModel(value = "CrmRoleField", description = "")
public class CrmRoleField implements Serializable {

	private static final long serialVersionUID = 1L;
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	@ApiModelProperty(value = "id")
	private Integer roleId;
	@ApiModelProperty(value = "crm")
	private Integer label;
	@TableField(exist = false)
	@ApiModelProperty(value = "")
	private Integer type;
	@ApiModelProperty(value = "id")
	private Integer fieldId;
	@ApiModelProperty(value = "")
	private String fieldName;
	@ApiModelProperty(value = "")
	private String name;
	@ApiModelProperty(value = " 1 2 3")
	private Integer authLevel;
	@ApiModelProperty(value = " 1 2 3 4")
	private Integer operateType;
	@ApiModelProperty(value = " 0  1  2 ")
	private Integer maskType;
	@ApiModelProperty(value = "  0 1 2data 3 4")
	private Integer fieldType;

	public CrmRoleField(Integer label, Integer roleId, String fieldName, String name, Integer authLevel, Integer operateType, Integer fieldType) {
		this.label = label;
		this.roleId = roleId;
		this.fieldName = fieldName;
		this.name = name;
		this.authLevel = authLevel;
		this.operateType = operateType;
		this.fieldType = fieldType;
		this.maskType = 0;
	}

	public CrmRoleField(Integer label, Integer roleId, String fieldName, String name, Integer authLevel, Integer operateType, Integer fieldType, Integer type) {
		this.label = label;
		this.roleId = roleId;
		this.fieldName = fieldName;
		this.name = name;
		this.authLevel = authLevel;
		this.operateType = operateType;
		this.fieldType = fieldType;
		this.maskType = 0;
		this.type = type;
	}
}
