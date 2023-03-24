package com.megazone.hrm.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2020-11-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_hrm_salary_archives")
@ApiModel(value = "HrmSalaryArchives", description = "")
public class HrmSalaryArchives implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty(value = " 1  2  3  4  5  6  7  8 ")
	private Integer changeReason;

	@ApiModelProperty(value = "")
	private Date changeData;

	@ApiModelProperty(value = "id")
	private Integer employeeId;

	@ApiModelProperty(value = " 0  1  2 ")
	private Integer changeType;

	@ApiModelProperty(value = "")
	private String remarks;


}
