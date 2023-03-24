package com.megazone.examine.entity.PO;

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
 * @since 2020-11-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_examine")
@ApiModel(value = "Examine", description = "")
public class Examine implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	@TableId(value = "examine_id", type = IdType.AUTO)
	private Long examineId;

	@ApiModelProperty
	private Long examineInitId;

	@ApiModelProperty(value = "0 OA 1  2  3 4 5  6 7 8  910 1112")
	private Integer label;

	@ApiModelProperty(value = "")
	private String examineIcon;

	@ApiModelProperty(value = "")
	private String examineName;

	@ApiModelProperty(value = " 1  2 ")
	private Integer recheckType;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "1  2  3  ")
	private Integer status;

	@ApiModelProperty(value = "ID")
	private String batchId;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;

	@ApiModelProperty(value = "")
	private Long updateUserId;

	@ApiModelProperty(value = "")
	private String remarks;

	@ApiModelProperty(value = "（）")
	private String userIds;

	@ApiModelProperty(value = "（）")
	private String deptIds;

	@ApiModelProperty(value = "1  2  3  4  5  6  0 ")
	private Integer oaType;


}
