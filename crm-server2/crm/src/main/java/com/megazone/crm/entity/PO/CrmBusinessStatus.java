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
 * @since 2020-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_crm_business_status")
@ApiModel(value = "CrmBusinessStatus", description = "")
public class CrmBusinessStatus implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "status_id", type = IdType.AUTO)
	private Integer statusId;

	@ApiModelProperty(value = "ID")
	private Integer typeId;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = "")
	private String rate;

	@ApiModelProperty(value = "")
	private Integer orderNum;


}
