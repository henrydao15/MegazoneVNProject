package com.megazone.hrm.entity.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class QueryHistorySalaryListVO {

	@TableId(value = "s_record_id", type = IdType.AUTO)
	@ApiModelProperty
	private Integer sRecordId;

	@ApiModelProperty
	private String title;

	@ApiModelProperty(value = "")
	private Integer num;

	@ApiModelProperty(value = "")
	private BigDecimal expectedPaySalary;

	@ApiModelProperty(value = "")
	private BigDecimal personalTax;

	@ApiModelProperty(value = "")
	private BigDecimal realPaySalary;

	@ApiModelProperty(value = "id")
	private Integer examineRecordId;

	@ApiModelProperty
	private Integer checkStatus;

	@ApiModelProperty(value = "id")
	private Long createUserId;

}
