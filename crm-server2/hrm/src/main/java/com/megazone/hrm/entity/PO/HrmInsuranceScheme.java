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
@TableName("wk_hrm_insurance_scheme")
@ApiModel(value = "HrmInsuranceScheme", description = "")
public class HrmInsuranceScheme implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	@TableId(value = "scheme_id", type = IdType.AUTO)
	private Integer schemeId;

	@ApiModelProperty(value = "")
	private String schemeName;

	@ApiModelProperty(value = "")
	private String city;

	@ApiModelProperty(value = "")
	private String houseType;

	@ApiModelProperty(value = " 1  2 ")
	private Integer schemeType;

	@ApiModelProperty(value = "1  0 ")
	private Integer isDel;

	@ApiModelProperty(value = "id")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;


}
