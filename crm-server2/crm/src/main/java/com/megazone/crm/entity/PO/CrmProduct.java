package com.megazone.crm.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
@TableName("wk_crm_product")
@ApiModel(value = "CrmProduct", description = "")
public class CrmProduct implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "product_id", type = IdType.AUTO)
	private Integer productId;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = "")
	private String num;

	@ApiModelProperty(value = "")
	private String unit;

	@ApiModelProperty(value = "")
	private BigDecimal price;

	@ApiModelProperty(value = " 1  0  3 ")
	private Integer status;

	@ApiModelProperty(value = "ID")
	private Integer categoryId;

	@ApiModelProperty(value = "")
	private String description;

	@ApiModelProperty(value = "ID")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "ID")
	private Long ownerUserId;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;

	@ApiModelProperty(value = "")
	private String batchId;


}
