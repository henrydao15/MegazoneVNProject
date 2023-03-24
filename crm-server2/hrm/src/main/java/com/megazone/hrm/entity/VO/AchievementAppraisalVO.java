package com.megazone.hrm.entity.VO;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2021/03/12/14:55
 */
@Getter
@Setter
public class AchievementAppraisalVO {
	@TableId(value = "id")
	private Integer appraisalId;

	@ApiModelProperty(value = "")
	private String appraisalName;
}
