package com.megazone.crm.entity.VO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.megazone.crm.entity.PO.CrmExamineStep;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CrmQueryAllExamineVO {

	private static final long serialVersionUID = 1L;

	@TableId(value = "examine_id", type = IdType.AUTO)
	private Integer examineId;

	@ApiModelProperty(value = "1  2  3")
	private Integer categoryType;

	@ApiModelProperty(value = " 1  2 ")
	private Integer examineType;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = "")
	private String icon;

	@ApiModelProperty(value = "ID（0）")
	@JsonProperty("deptList")
	private Object deptIds;

	@ApiModelProperty(value = "ID")
	@JsonProperty("userList")
	private Object userIds;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;

	@ApiModelProperty(value = "")
	private Long updateUserId;

	@ApiModelProperty(value = " 1  0  2 ")
	private Integer status;

	@ApiModelProperty(value = "")
	private String remarks;


	@ApiModelProperty
	private String updateUserName;

	@ApiModelProperty
	private String createUserName;

	@ApiModelProperty
	private List<CrmExamineStep> stepList;

}
