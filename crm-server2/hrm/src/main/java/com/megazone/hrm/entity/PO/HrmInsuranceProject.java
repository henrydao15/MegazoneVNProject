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
@TableName("wk_hrm_insurance_project")
@ApiModel(value = "HrmInsuranceProject", description = "")
public class HrmInsuranceProject implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	@TableId(value = "project_id", type = IdType.AUTO)
	private Integer projectId;

	@ApiModelProperty(value = "id")
	private Integer schemeId;

	@ApiModelProperty(value = "1  2  3  4  5  6  7  8  9  10  11 ")
	private Integer type;

	@ApiModelProperty(value = "")
	private String projectName;

	@ApiModelProperty(value = "")
	private BigDecimal defaultAmount;

	@ApiModelProperty(value = "")
	private BigDecimal corporateProportion;

	@ApiModelProperty(value = "")
	private BigDecimal personalProportion;

	@ApiModelProperty(value = "")
	private BigDecimal corporateAmount;

	@ApiModelProperty(value = "")
	private BigDecimal personalAmount;

	@ApiModelProperty(value = "1  0 ")
	private Integer isDel;


}
