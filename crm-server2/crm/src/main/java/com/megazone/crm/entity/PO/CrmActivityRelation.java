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
 * @since 2020-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_crm_activity_relation")
@ApiModel(value = "CrmActivityRelation", description = "")
public class CrmActivityRelation implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "r_id", type = IdType.AUTO)
	private Integer rId;

	private Integer activityId;

	@ApiModelProperty(value = "3  5 ")
	private Integer type;

	private Integer typeId;


}
