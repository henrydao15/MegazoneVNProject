package com.megazone.crm.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-06-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_crm_scene")
@ApiModel(value = "CrmScene", description = "")
public class CrmScene implements Serializable {

	private static final long serialVersionUID = 1L;
	@TableId(value = "scene_id", type = IdType.AUTO)
	private Integer sceneId;
	@ApiModelProperty(value = "")
	@NotNull
	private Integer type;
	@ApiModelProperty(value = "")
	@NotNull
	private String name;
	@ApiModelProperty(value = "ID")
	private Long userId;
	@ApiModelProperty(value = "ID")
	private Integer sort;
	@ApiModelProperty(value = "")
	@NotNull
	private String data;
	@ApiModelProperty(value = "1")
	private Integer isHide;
	@ApiModelProperty(value = "10")
	private Integer isSystem;
	@ApiModelProperty(value = "")
	private String bydata;
	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;
	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;
	@ApiModelProperty(value = "")
	@TableField(exist = false)
	private Integer isDefault;

	public CrmScene() {
	}


	public CrmScene(Integer type, String name, Long userId, Integer sort, String data, Integer isHide, Integer isSystem, String bydata) {
		this.type = type;
		this.name = name;
		this.userId = userId;
		this.sort = sort;
		this.data = data;
		this.isHide = isHide;
		this.isSystem = isSystem;
		this.bydata = bydata;
	}


}
