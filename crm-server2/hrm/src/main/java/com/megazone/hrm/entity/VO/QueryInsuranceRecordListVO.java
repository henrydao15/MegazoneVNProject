package com.megazone.hrm.entity.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class QueryInsuranceRecordListVO {

	@TableId(value = "i_record_id", type = IdType.AUTO)
	private Integer iRecordId;

	@ApiModelProperty
	private String title;

	@ApiModelProperty(value = "")
	private Integer year;

	@ApiModelProperty(value = "")
	private Integer month;

	@ApiModelProperty(value = "")
	private Integer num;

	private Integer status;

	@ApiModelProperty(value = "")
	private BigDecimal personalInsuranceAmount;

	@ApiModelProperty(value = "")
	private BigDecimal personalProvidentFundAmount;

	@ApiModelProperty(value = "")
	private BigDecimal corporateInsuranceAmount;

	@ApiModelProperty(value = "")
	private BigDecimal corporateProvidentFundAmount;

	@ApiModelProperty
	private Integer stopNum;

}
