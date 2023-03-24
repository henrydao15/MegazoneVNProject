package com.megazone.admin.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Objects;

@Data
@Accessors(chain = true)
@TableName("wk_admin_menu")
@ApiModel(value = "AdminMenu object", description = "Background menu table")
public class AdminMenu implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Menu ID")
	@TableId(value = "menu_id", type = IdType.AUTO)
	private Integer menuId;

	@ApiModelProperty(value = "Parent menu ID")
	private Integer parentId;

	@ApiModelProperty(value = "menu name")
	private String menuName;

	@ApiModelProperty(value = "Permission ID")
	private String realm;

	@ApiModelProperty(value = "authority URL")
	private String realmUrl;

	@ApiModelProperty(value = "owning module")
	private String realmModule;

	@ApiModelProperty(value = "Menu Type 1 Directory 2 Menu 3 Button 4 Special")
	private Integer menuType;

	@ApiModelProperty(value = "Sort (valid at the same level)")
	private Integer sort;

	@ApiModelProperty(value = "Status 1 enable 0 disable")
	private Integer status;

	@ApiModelProperty(value = "Menu Description")
	private String remarks;

	@TableField(exist = false)
	@ApiModelProperty(value = "Data permission")
	private Integer dataType;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AdminMenu adminMenu = (AdminMenu) o;
		return Objects.equals(menuId, adminMenu.menuId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(menuId);
	}
}
