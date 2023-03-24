package com.megazone.hrm.entity.PO;

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
import java.math.BigDecimal;
import java.util.List;

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
@TableName("wk_hrm_achievement_seg")
@ApiModel(value = "HrmAchievementSeg", description = "")
public class HrmAchievementSeg implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "seg_id", type = IdType.AUTO)
	private Integer segId;

	private Integer tableId;

	@ApiModelProperty(value = "")
	private String segName;

	@ApiModelProperty(value = " 1  0 ")
	private Integer isFixed;

	@ApiModelProperty(value = " -1  0~100")
	private BigDecimal weight;

	@ApiModelProperty(value = "()")
	private Integer sort;


	@ApiModelProperty
	@TableField(exist = false)
	private List<HrmAchievementSegItem> items;


}
