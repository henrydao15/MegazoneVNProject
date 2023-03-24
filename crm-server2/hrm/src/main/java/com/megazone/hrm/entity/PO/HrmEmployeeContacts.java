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
 * @since 2020-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_hrm_employee_contacts")
@ApiModel(value = "HrmEmployeeContacts", description = "")
public class HrmEmployeeContacts implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "contacts_id", type = IdType.AUTO)
	private Integer contactsId;

	private Integer employeeId;

	@ApiModelProperty(value = "")
	private String contactsName;

	@ApiModelProperty(value = "")
	private String relation;

	@ApiModelProperty(value = "")
	private String contactsPhone;

	@ApiModelProperty(value = "")
	private String contactsWorkUnit;

	@ApiModelProperty(value = "")
	private String contactsPost;

	@ApiModelProperty(value = "")
	private String contactsAddress;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	private Integer sort;


}
