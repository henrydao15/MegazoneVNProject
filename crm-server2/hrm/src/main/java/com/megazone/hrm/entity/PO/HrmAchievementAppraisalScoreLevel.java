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
import java.math.BigDecimal;

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
@TableName("wk_hrm_achievement_appraisal_score_level")
@ApiModel(value = "HrmAchievementAppraisalScoreLevel", description = "")
public class HrmAchievementAppraisalScoreLevel implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "level_id", type = IdType.AUTO)
	private Integer levelId;

	@ApiModelProperty(value = "id")
	private Integer appraisalId;

	@ApiModelProperty(value = "")
	private String levelName;

	@ApiModelProperty(value = "")
	private BigDecimal minScore;

	@ApiModelProperty(value = "")
	private BigDecimal maxScore;

	@ApiModelProperty(value = "")
	private Integer minNum;

	@ApiModelProperty(value = "")
	private Integer maxNum;

	private Integer sort;


}
