package com.megazone.hrm.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_hrm_salary_slip_option")
@ApiModel(value = "HrmSalarySlipOption", description = "")
public class HrmSalarySlipOption implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty(value = "id")
	private Integer slipId;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = " 1  2 ")
	private Integer type;

	@ApiModelProperty(value = "code")
	private Integer code;

	@ApiModelProperty(value = "value")
	private String value;

	@ApiModelProperty(value = "")
	private String remark;

	@ApiModelProperty(value = "id")
	private Integer pid;

	@ApiModelProperty(value = "")
	private Integer sort;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@TableField(exist = false)
	@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
	private List<HrmSalarySlipOption> optionList;


}
