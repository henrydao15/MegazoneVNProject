package com.megazone.oa.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_oa_examine_data")
@ApiModel(value = "OaExamineData object", description = "oa approval custom field value table")
public class OaExamineData implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty(value = "field id")
	private Integer fieldId;

	@ApiModelProperty(value = "field name")
	private String name;

	private String value;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	private String batchId;

}
