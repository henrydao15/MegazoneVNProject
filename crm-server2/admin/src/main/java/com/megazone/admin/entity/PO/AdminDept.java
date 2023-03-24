package com.megazone.admin.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_admin_dept")
@ApiModel(value = "AdminDept object", description = "Department table")
public class AdminDept implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "dept_id", type = IdType.AUTO)
	private Integer deptId;

	@ApiModelProperty(value = "The parent ID top-level department is 0")
	private Integer pid;

	@ApiModelProperty(value = "Department Name")
	private String name;

	@ApiModelProperty(value = "The bigger the order, the later")
	private Integer num;

	@ApiModelProperty(value = "Department Notes")
	private String remark;

	@ApiModelProperty(value = "Department Head")
	private Long ownerUserId;

	@ApiModelProperty
	@TableField(exist = false)
	private Integer currentNum;


}
