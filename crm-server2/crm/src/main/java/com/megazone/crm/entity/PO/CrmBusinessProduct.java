package com.megazone.crm.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

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
@TableName("wk_crm_business_product")
@ApiModel(value = "CrmBusinessProduct", description = "")
public class CrmBusinessProduct implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "r_id", type = IdType.AUTO)
	private Integer rId;

	@ApiModelProperty(value = "ID")
	private Integer businessId;

	@ApiModelProperty(value = "ID")
	private Integer productId;

	@ApiModelProperty(value = "")
	private BigDecimal price;

	@ApiModelProperty(value = "")
	private BigDecimal salesPrice;

	@ApiModelProperty(value = "")
	private BigDecimal num;

	@ApiModelProperty(value = "")
	private BigDecimal discount;

	@ApiModelProperty(value = "（）")
	private BigDecimal subtotal;

	@ApiModelProperty(value = "")
	private String unit;


	@ApiModelProperty(value = "")
	@TableField(exist = false)
	private String name;

	@ApiModelProperty(value = "")
	@TableField(exist = false)
	private String categoryName;

}
