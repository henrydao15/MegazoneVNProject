package com.megazone.crm.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import com.megazone.core.feign.admin.entity.SimpleDept;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
@TableName("wk_crm_business_type")
@ApiModel(value = "CrmBusinessType", description = "")
public class CrmBusinessType implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "type_id", type = IdType.AUTO)
	private Integer typeId;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = "ID")
	private String deptIds;

	@ApiModelProperty(value = "")
	@TableField(exist = false)
	private List<SimpleDept> deptList;

	@ApiModelProperty(value = "ID")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "")
	@TableField(exist = false)
	private String createName;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;

	@ApiModelProperty(value = "012")
	private Integer status;


	@TableField(exist = false)
	@ApiModelProperty(value = "")
	private List<CrmBusinessStatus> statusList;


}
