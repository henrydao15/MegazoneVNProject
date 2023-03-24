package com.megazone.hrm.entity.PO;

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
 * hrm
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_hrm_action_record")
@ApiModel(value = "HrmActionRecord", description = "hrm")
public class HrmActionRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty(value = "ID")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "ip")
	private String ipAddress;

	@ApiModelProperty(value = " 1 ")
	private Integer type;

	@ApiModelProperty(value = "id")
	private Integer typeId;

	@ApiModelProperty(value = " 1  2  3  4  5  6  7  8  9  10 ")
	private Integer behavior;

	@ApiModelProperty(value = "")
	private String content;


}
