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
@TableName("wk_hrm_dept")
@ApiModel(value = "HrmDept", description = "")
public class HrmDept implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "dept_id", type = IdType.AUTO)
	private Integer deptId;

	@ApiModelProperty(value = "ID 0")
	private Integer pid;

	@ApiModelProperty(value = "1  2 ")
	private Integer deptType;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = "")
	private String code;

	@ApiModelProperty(value = "ID")
	private Integer mainEmployeeId;

	@ApiModelProperty(value = "")
	private Integer leaderEmployeeId;


	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;


}
