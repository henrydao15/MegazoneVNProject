package com.megazone.crm.entity.PO;

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
 * @since 2020-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_crm_receivables_plan_data")
@ApiModel(value = "CrmReceivablesPlanData", description = "")
public class CrmReceivablesPlanData implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	private Integer fieldId;

	@TableField(exist = false)
	private String fieldName;

	@ApiModelProperty(value = "")
	private String name;

	private String value;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	private String batchId;


}
