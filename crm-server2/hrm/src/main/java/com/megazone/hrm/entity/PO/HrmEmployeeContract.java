package com.megazone.hrm.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

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
@TableName("wk_hrm_employee_contract")
@ApiModel(value = "HrmEmployeeContract", description = "")
public class HrmEmployeeContract implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "contract_id", type = IdType.AUTO)
	private Integer contractId;

	@ApiModelProperty
	private Integer employeeId;

	@ApiModelProperty(value = "")
	private String contractNum;

	@ApiModelProperty(value = "1、 2、 3、 4、 5、 6、 7、 8、 9、")
	private Integer contractType;

	@ApiModelProperty
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+9", pattern = "yyyy-MM-dd")
	private Date startTime;

	@ApiModelProperty
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+9", pattern = "yyyy-MM-dd")
	private Date endTime;

	@ApiModelProperty(value = "")
	private Integer term;

	@ApiModelProperty(value = "  0 1 、 2、 ")
	private Integer status;

	@ApiModelProperty(value = "")
	private String signCompany;

	@ApiModelProperty(value = "")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+9", pattern = "yyyy-MM-dd")
	private Date signTime;

	@ApiModelProperty(value = "")
	private String remarks;

	@ApiModelProperty(value = " 0  1 ")
	private Integer isExpireRemind;

	@ApiModelProperty
	private Integer sort;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	private String batchId;


}
