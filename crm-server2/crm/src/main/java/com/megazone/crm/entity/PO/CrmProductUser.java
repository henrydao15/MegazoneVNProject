package com.megazone.crm.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
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
@TableName("wk_crm_product_user")
@ApiModel(value = "CrmProductUser", description = "")
public class CrmProductUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "product_user_id", type = IdType.AUTO)
	private Long productUserId;

	private String productIds;

	private Long userId;


}
