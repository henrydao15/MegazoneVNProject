package com.megazone.hrm.entity.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.megazone.core.entity.BasePage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class QueryHistorySalaryDetailVO {

	@TableId(value = "s_record_id", type = IdType.AUTO)
	private Integer sRecordId;

	@ApiModelProperty
	private String title;

	@ApiModelProperty(value = "")
	private Integer year;

	@ApiModelProperty(value = "")
	private Integer month;

	@ApiModelProperty(value = "")
	private Integer num;

	@ApiModelProperty
	private Date startTime;

	@ApiModelProperty
	private Date endTime;

	@ApiModelProperty(value = "id")
	private Integer examineRecordId;

	@ApiModelProperty
	private Integer checkStatus;

	@ApiModelProperty(value = "id")
	private Long createUserId;

	@ApiModelProperty(value = "")
	private BigDecimal personalInsuranceAmount;

	@ApiModelProperty(value = "")
	private BigDecimal personalProvidentFundAmount;

	@ApiModelProperty(value = "")
	private BigDecimal corporateInsuranceAmount;

	@ApiModelProperty(value = "")
	private BigDecimal corporateProvidentFundAmount;

	@ApiModelProperty(value = "")
	private BigDecimal expectedPaySalary;

	@ApiModelProperty(value = "")
	private BigDecimal personalTax;

	@ApiModelProperty(value = "")
	private BigDecimal realPaySalary;

	@ApiModelProperty
	private Integer status;

	@ApiModelProperty
	private List<SalaryOptionHeadVO> salaryOptionHeadList;

	@ApiModelProperty
	private BasePage<QuerySalaryPageListVO> pageData;

}
