package com.megazone.hrm.entity.BO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.megazone.hrm.entity.PO.HrmAchievementSeg;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class SetAchievementTableBO {

	@TableId(value = "table_id", type = IdType.AUTO)
	private Integer tableId;

	@ApiModelProperty(value = "")
	@NotBlank(message = "")
	private String tableName;

	@ApiModelProperty(value = "1 OKR 2 KPI")
	private Integer type;

	@ApiModelProperty(value = "")
	private String description;

	@ApiModelProperty
	private Integer isEmpWeight;

	@ApiModelProperty(value = " 1  0 ")
	private Integer status;

	@ApiModelProperty
	private List<HrmAchievementSeg> fixedSegList;

	@ApiModelProperty
	private List<HrmAchievementSeg> noFixedSegList;


}
