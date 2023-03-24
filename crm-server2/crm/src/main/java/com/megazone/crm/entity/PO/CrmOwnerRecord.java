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
 * @since 2020-08-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_crm_owner_record")
@ApiModel(value = "CrmOwnerRecord", description = "")
public class CrmOwnerRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "record_id", type = IdType.AUTO)
	private Integer recordId;

	@ApiModelProperty(value = "id")
	private Integer typeId;

	@ApiModelProperty(value = "")
	private Integer type;

	@ApiModelProperty(value = "")
	private Long preOwnerUserId;

	@ApiModelProperty(value = "")
	private Long postOwnerUserId;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;


}
