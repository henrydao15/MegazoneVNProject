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
 * @since 2020-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_hrm_insurance_month_record")
@ApiModel(value = "HrmInsuranceMonthRecord", description = "")
public class HrmInsuranceMonthRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "i_record_id", type = IdType.AUTO)
	private Integer iRecordId;

	@ApiModelProperty
	private String title;

	@ApiModelProperty(value = "")
	private Integer year;

	@ApiModelProperty(value = "")
	private Integer month;

	@ApiModelProperty(value = "")
	private Integer num;

	private Integer status;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;


}
