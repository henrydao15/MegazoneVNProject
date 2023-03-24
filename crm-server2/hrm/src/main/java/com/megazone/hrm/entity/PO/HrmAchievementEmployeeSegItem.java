package com.megazone.hrm.entity.PO;

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
 * @since 2020-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_hrm_achievement_employee_seg_item")
@ApiModel(value = "HrmAchievementEmployeeSegItem", description = "")
public class HrmAchievementEmployeeSegItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "item_id", type = IdType.AUTO)
	private Integer itemId;

	private Integer segId;

	@ApiModelProperty
	private Integer tempItemId;

	@ApiModelProperty(value = "")
	private String itemName;

	@ApiModelProperty(value = "")
	private String value;

	private Integer sort;


}
