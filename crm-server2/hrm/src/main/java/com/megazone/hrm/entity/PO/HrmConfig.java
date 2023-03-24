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
 *
 * </p>
 *
 * @author
 * @since 2020-05-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_hrm_config")
@ApiModel(value = "HrmConfig", description = "")
public class HrmConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "config_id", type = IdType.AUTO)
	private Integer configId;

	@ApiModelProperty(value = " 1 ")
	private Integer type;

	@ApiModelProperty(value = "")
	private String value;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;


}
