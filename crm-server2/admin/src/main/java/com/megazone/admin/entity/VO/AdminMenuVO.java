package com.megazone.admin.entity.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@ApiModel
public class AdminMenuVO {
	@ApiModelProperty(value = "Menu ID")
	@TableId(value = "menu_id", type = IdType.AUTO)
	private Integer menuId;

	@ApiModelProperty(value = "Parent menu ID")
	private Integer parentId;

	@ApiModelProperty(value = "menu name")
	private String menuName;

	@ApiModelProperty(value = "Permission ID")
	private String realm;

	@ApiModelProperty(value = "Menu Type 1 Directory 2 Menu 3 Button 4 Special")
	private Integer menuType;

	@ApiModelProperty(value = "remarks")
	private String remarks;

	@ApiModelProperty(value = "Submenu")
	private List<AdminMenuVO> childMenu;

}
