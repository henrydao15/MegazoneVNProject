package com.megazone.hrm.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_hrm_salary_option_template")
@ApiModel(value = "HrmSalaryOptionTemplate", description = "")
public class HrmSalaryOptionTemplate implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty(value = "")
	private Integer code;

	@ApiModelProperty(value = "")
	private Integer parentCode;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = "")
	private String remarks;

	@ApiModelProperty(value = " 0  1 ")
	private Integer isFixed;

	@ApiModelProperty(value = " 0  1 ")
	private Integer isPlus;

	@ApiModelProperty(value = " 0  1 ")
	private Integer isTax;

	@ApiModelProperty(value = " 0  1 ")
	private Integer isShow;

	@ApiModelProperty
	private Integer isCompute;

	@ApiModelProperty
	@TableField(exist = false)
	private Integer isOpen;


}
