package com.megazone.crm.entity.BO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CrmSaveExamineBO {

	@TableId(value = "examine_id", type = IdType.AUTO)
	private Integer examineId;

	@ApiModelProperty(value = "1  2  3 4 5  6 7 8  910 1112")
	private Integer categoryType;

	@ApiModelProperty(value = " 1  2 ")
	private Integer examineType;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = "")
	private String remarks;

	@ApiModelProperty(value = "ID")
	private List<Integer> deptIds;

	@ApiModelProperty(value = "Id")
	private List<Long> userIds;

	@ApiModelProperty
	private List<Step> step;


	@Getter
	@Setter
	public static class Step {
		private Integer stepType;
		private List<Long> checkUserId;
	}


}
