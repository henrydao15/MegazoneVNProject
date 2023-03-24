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
@TableName("wk_hrm_employee_data")
@ApiModel(value = "HrmEmployeeData", description = "")
public class HrmEmployeeData implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	private Integer fieldId;

	private Integer labelGroup;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = "")
	private String fieldValue;

	@ApiModelProperty(value = "")
	private String fieldValueDesc;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "employee_id")
	private Integer employeeId;


	@ApiModelProperty(value = "")
	@TableField(exist = false)
	private Integer type;


}
