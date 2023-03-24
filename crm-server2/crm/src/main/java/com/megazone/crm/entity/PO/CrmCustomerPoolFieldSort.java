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

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_crm_customer_pool_field_sort")
@ApiModel(value = "CrmCustomerPoolFieldSort", description = "")
public class CrmCustomerPoolFieldSort implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty(value = "id")
	private Integer poolId;

	@ApiModelProperty(value = "id")
	private Long userId;

	@ApiModelProperty(value = "id")
	private Integer fieldId;

	@ApiModelProperty(value = "")
	@TableField(exist = false)
	private Integer width;

	@ApiModelProperty(value = "")
	private String fieldName;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = " 1  2  3  4 5  6  7   8  9  10  11  12  13  14  15 16  17  18  19  20  21 ")
	private Integer type;

	@ApiModelProperty(value = "")
	@TableField(exist = false)
	private String formType;

	@ApiModelProperty(value = "")
	private Integer sort;

	@ApiModelProperty(value = " 0、 1、")
	private Integer isHidden;


}
