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
@TableName("wk_hrm_employee_quit_info")
@ApiModel(value = "HrmEmployeeQuitInfo", description = "")
public class HrmEmployeeQuitInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "quit_info_id", type = IdType.AUTO)
	@ApiModelProperty(value = "id")
	private Integer quitInfoId;

	@ApiModelProperty(value = "id")
	private Integer employeeId;

	@ApiModelProperty(value = "")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+9", pattern = "yyyy-MM-dd")
	private Date planQuitTime;

	@ApiModelProperty(value = "")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+9", pattern = "yyyy-MM-dd")
	private Date applyQuitTime;

	@ApiModelProperty(value = "")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+9", pattern = "yyyy-MM-dd")
	private Date salarySettlementTime;

	@ApiModelProperty(value = " 1  2  3 ")
	private Integer quitType;

	@ApiModelProperty(value = " 1 2 3 4 5 6 7 8 9 10  11 12 13/ 14 15 16 ")
	private Integer quitReason;

	@ApiModelProperty(value = "")
	private String remarks;

	@ApiModelProperty
	private Integer oldStatus;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;


}
