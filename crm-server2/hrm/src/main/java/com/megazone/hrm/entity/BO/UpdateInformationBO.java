package com.megazone.hrm.entity.BO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class UpdateInformationBO {

	@ApiModelProperty
	private Integer employeeId;

	@ApiModelProperty
	private Integer contactsId;

	@ApiModelProperty
	private List<InformationFieldBO> dataList;

	@Getter
	@Setter
	public static class InformationFieldBO implements Serializable {

		private static final long serialVersionUID = 1L;

		@TableId(value = "id", type = IdType.AUTO)
		private Integer id;

		private Integer fieldId;

		private Integer labelGroup;

		@ApiModelProperty(value = "")
		private String fieldName;

		@ApiModelProperty(value = "")
		private String name;

		@ApiModelProperty(value = "")
		private Object fieldValue;

		@ApiModelProperty(value = "")
		private String fieldValueDesc;

		@ApiModelProperty(value = " 1  0 ")
		private Integer isFixed;

		@ApiModelProperty(value = "")
		private Integer type;

		@ApiModelProperty(value = "id")
		private Integer employeeId;


	}
}
