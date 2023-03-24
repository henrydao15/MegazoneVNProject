package com.megazone.work.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import com.megazone.work.entity.BO.WorkOwnerRoleBO;
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
 * @since 2020-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_work")
@ApiModel(value = "Work", description = "")
public class Work implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	@TableId(value = "work_id", type = IdType.AUTO)
	private Integer workId;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = " 1 3 2 ")
	private Integer status;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;

	@ApiModelProperty(value = "ID")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "")
	private String description;

	@ApiModelProperty(value = "")
	private String color;

	@ApiModelProperty(value = " 1  0 ")
	private Integer isOpen;

	@ApiModelProperty(value = "id")
	private Integer ownerRole;

	@ApiModelProperty(value = "")
	private Date archiveTime;

	@ApiModelProperty(value = "")
	private Date deleteTime;

	@ApiModelProperty(value = " 0 1")
	private Integer isSystemCover;

	@ApiModelProperty(value = " ")
	private String coverUrl;

	private String batchId;


	@ApiModelProperty(value = "")
	private String ownerUserId;

	@ApiModelProperty(value = "")
	@TableField(exist = false)
	private List<WorkOwnerRoleBO> WorkOwnerRoleList;


}
