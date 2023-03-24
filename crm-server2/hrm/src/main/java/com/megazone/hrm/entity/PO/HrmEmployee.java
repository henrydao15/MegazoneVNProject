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
@TableName("wk_hrm_employee")
@ApiModel(value = "HrmEmployee", description = "")
public class HrmEmployee implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	@TableId(value = "employee_id", type = IdType.AUTO)
	private Integer employeeId;

	@ApiModelProperty(value = "")
	private String employeeName;

	@ApiModelProperty(value = "")
	private String mobile;

	@ApiModelProperty(value = "")
	private String country;

	@ApiModelProperty(value = "")
	private String nation;

	@ApiModelProperty(value = " 1  2  3  4  5 ")
	private Integer idType;

	@ApiModelProperty(value = "")
	private String idNumber;

	@ApiModelProperty(value = " 1  2 ")
	private Integer sex;

	@ApiModelProperty(value = "")
	private String email;

	@ApiModelProperty(value = "")
	private String nativePlace;

	@ApiModelProperty(value = "")
	private Date dateOfBirth;

	@ApiModelProperty(value = " 1  2 ")
	private Integer birthdayType;

	@ApiModelProperty(value = " ：0323")
	private String birthday;

	@ApiModelProperty(value = "")
	private Integer age;

	@ApiModelProperty(value = "")
	private String address;

	@ApiModelProperty(value = "")
	private Integer highestEducation;

	@ApiModelProperty(value = "")
	private Date entryTime;

	@ApiModelProperty(value = " 0 ")
	private Integer probation;

	@ApiModelProperty(value = "")
	private Date becomeTime;

	@ApiModelProperty(value = "")
	private String jobNumber;

	@ApiModelProperty(value = "ID")
	@TableField(updateStrategy = FieldStrategy.NOT_NULL)
	private Integer deptId;

	@ApiModelProperty(value = "ID")
	private Integer parentId;

	@ApiModelProperty(value = "")
	private String post;

	@ApiModelProperty(value = "")
	private String postLevel;

	@ApiModelProperty(value = "")
	private String workAddress;

	@ApiModelProperty(value = "")
	private String workDetailAddress;

	@ApiModelProperty(value = "")
	private String workCity;

	@ApiModelProperty
	private Integer channelId;

	@ApiModelProperty(value = " 1  2 ")
	private Integer employmentForms;

	@ApiModelProperty(value = " 1 2  3 4 5 6 7 8")
	private Integer status;

	@ApiModelProperty(value = "")
	private Date companyAgeStartTime;

	@ApiModelProperty(value = "")
	private Integer companyAge;

	@ApiModelProperty(value = "id")
	private Integer candidateId;

	@ApiModelProperty
	private Integer entryStatus;

	@ApiModelProperty(value = "0  1 ")
	private Integer isDel;

	@ApiModelProperty(value = "id")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;


	@ApiModelProperty(value = "")
	@TableField(exist = false)
	private String deptName;


}
