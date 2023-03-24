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
 * @since 2020-06-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_hrm_employee_abnormal_change_record")
@ApiModel(value = "HrmEmployeeAbnormalChangeRecord", description = "（）")
public class HrmEmployeeAbnormalChangeRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "change_record_id", type = IdType.AUTO)
	private Integer changeRecordId;

	@ApiModelProperty(value = " 1  2  3  4 ")
	private Integer type;

	@ApiModelProperty(value = "id")
	private Integer employeeId;

	@ApiModelProperty(value = "")
	private Date changeTime;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;


}
