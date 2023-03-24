package com.megazone.crm.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@TableName("wk_crm_scene_default")
@ApiModel(value = "CrmSceneDefault", description = "")
public class CrmSceneDefault implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "default_id", type = IdType.AUTO)
	private Integer defaultId;

	@ApiModelProperty(value = "")
	private Integer type;

	@ApiModelProperty(value = "ID")
	private Long userId;

	@ApiModelProperty(value = "ID")
	private Integer sceneId;


}
