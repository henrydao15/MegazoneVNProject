package com.megazone.hrm.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class WriteAppraisalBO {

	@ApiModelProperty
	private Integer employeeAppraisalId;

	@ApiModelProperty
	private List<SegListBean> segList;
	@ApiModelProperty(value = " 0 1")
	private Integer isDraft;

	@Getter
	@Setter
	public static class SegListBean {

		@ApiModelProperty
		private Integer tempSegId;
		@ApiModelProperty
		private String segName;
		@ApiModelProperty
		private String value;
		@ApiModelProperty
		private Integer isFixed;
		@ApiModelProperty
		private BigDecimal weight;
		@ApiModelProperty
		private List<ItemsBean> items;
	}

	@Getter
	@Setter
	public static class ItemsBean {
		@ApiModelProperty
		private Integer tempItemId;
		@ApiModelProperty
		private String itemName;
		@ApiModelProperty
		private String value;
	}
}
