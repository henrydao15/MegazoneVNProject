package com.megazone.crm.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2020-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_crm_product_detail_img")
@ApiModel(value = "CrmProductDetailImg", description = "")
public class CrmProductDetailImg implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "img_id", type = IdType.AUTO)
	private Integer imgId;

	@ApiModelProperty(value = "id")
	private Integer productId;


	private String remarks;

	@ApiModelProperty(value = "")
	private String mainFileIds;

	private String detailFileIds;


}
