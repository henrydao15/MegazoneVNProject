package com.megazone.hrm.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

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
@TableName("wk_hrm_employee_social_security_info")
@ApiModel(value = "HrmEmployeeSocialSecurityInfo", description = "")
public class HrmEmployeeSocialSecurityInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "social_security_info_id", type = IdType.AUTO)
	private Integer socialSecurityInfoId;

	private Integer employeeId;

	@ApiModelProperty(value = " 0  1 ")
	private Integer isFirstSocialSecurity;

	@ApiModelProperty(value = " 0  1 ")
	private Integer isFirstAccumulationFund;

	@ApiModelProperty(value = "")
	private String socialSecurityNum;

	@ApiModelProperty(value = "")
	private String accumulationFundNum;

	@ApiModelProperty(value = "（2020.05）")
	private String socialSecurityStartMonth;

	@ApiModelProperty(value = "")
	private Integer schemeId;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@TableField(exist = false)
	@ApiModelProperty(value = "")
	private String schemeName;


}
