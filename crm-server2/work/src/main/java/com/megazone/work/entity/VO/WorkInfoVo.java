package com.megazone.work.entity.VO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.megazone.core.common.JSONObject;
import com.megazone.core.feign.admin.entity.SimpleUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author wyq
 */
@Data
public class WorkInfoVo {
	@ApiModelProperty(value = "ID")
	private Integer workId;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = " 1 3 2 ")
	private Integer status;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

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

	@ApiModelProperty(value = "ID")
	private String fileId;

	private String batchId;

	@ApiModelProperty(value = "")
	private String ownerUserId;

	@ApiModelProperty(value = "2")
	private List<SimpleUser> ownerUser;

	@ApiModelProperty(value = " 0 1")
	private Integer collect;

	@ApiModelProperty(value = "")
	private Integer orderNum;

	@ApiModelProperty(value = "")
	private JSONObject authList;

	private JSONObject permission;

}
