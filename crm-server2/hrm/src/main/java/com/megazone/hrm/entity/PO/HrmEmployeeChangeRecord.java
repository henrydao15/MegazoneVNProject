package com.megazone.hrm.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@TableName("wk_hrm_employee_change_record")
@ApiModel(value = "HrmEmployeeChangeRecord", description = "/")
public class HrmEmployeeChangeRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "record_id", type = IdType.AUTO)
	private Integer recordId;

	@ApiModelProperty(value = "id")
	private Integer employeeId;

	@ApiModelProperty(value = " 4  5 6 7 8")
	private Integer changeType;

	@ApiModelProperty(value = " 1  2 3  4  5  6  7 ")
	private Integer changeReason;

	@ApiModelProperty(value = "")
	private Integer oldDept;

	@ApiModelProperty(value = "")
	private Integer newDept;

	@ApiModelProperty(value = "")
	private String oldPost = "";

	@ApiModelProperty(value = "")
	private String newPost = "";

	@ApiModelProperty(value = "")
	private String oldPostLevel = "";

	@ApiModelProperty(value = "")
	private String newPostLevel = "";

	@ApiModelProperty(value = "")
	private String oldWorkAddress = "";

	@ApiModelProperty(value = "")
	private String newWorkAddress = "";

	@ApiModelProperty(value = "")
	private Integer oldParentId;

	@ApiModelProperty(value = "")
	private Integer newParentId;

	@ApiModelProperty(value = "")
	private Integer probation;

	@ApiModelProperty(value = "")
	@JsonFormat(timezone = "GMT+9", pattern = "yyyy-MM-dd")
	private Date effectTime;

	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty
	@TableField(exist = false)
	private String remarks;


}
