package com.megazone.admin.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("wk_admin_model_sort")
@ApiModel(value = "AdminModelSort object", description = "Customer management navigation bar sorting table")
public class AdminModelSort implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty(value = "Navigation Type 1 Head Navigation 2 Customer Management Left Navigation")
	private Integer type;

	@ApiModelProperty(value = "Module 1 Dashboard 2 To-Do 3 Leads 4 Customers 5 Contacts 6 Opportunities 7 Contracts 8 Payments 9 Invoices 10 Returns 11 Products 12 Marketing Activities")
	private String model;

	@ApiModelProperty(value = "sort")
	private Integer sort;

	@ApiModelProperty(value = "whether hidden 0 not hidden 1 hidden")
	private Integer isHidden;

	@ApiModelProperty(value = "userid")
	private Long userId;


}
