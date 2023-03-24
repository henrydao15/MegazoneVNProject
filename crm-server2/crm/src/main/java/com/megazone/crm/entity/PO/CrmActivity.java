package com.megazone.crm.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.servlet.upload.FileEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * crm activity sheet
 * </p>
 *
 * @author
 * @since 2020-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_crm_activity")
@ApiModel(value = "CrmActivity object", description = "crm activity table")
public class CrmActivity implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "activity id")
	@TableId(value = "activity_id", type = IdType.AUTO)
	private Integer activityId;

	@ApiModelProperty(value = "Activity Type 1 Follow-up Record 2 Create Record 3 Opportunity Stage Change 4 Field Check-in")
	private Integer type;

	@ApiModelProperty(value = "Follow-up type")
	@NotNull(message = "Follow-up type cannot be null")
	private String category;

	@ApiModelProperty(value = "Activity Type 1 Lead 2 Customer 3 Contact 4 Product 5 Opportunity 6 Contract 7 Receipt 8 Log 9 Approval 10 Schedule 11 Task 12 Email")
	@NotNull(message = "Type cannot be null")
	private Integer activityType;

	@ApiModelProperty(value = "Activity Type Id")
	@NotNull(message = "Type id cannot be null")
	private Integer activityTypeId;

	@ApiModelProperty(value = "active content")
	@NotNull(message = "Content cannot be empty")
	private String content;

	@ApiModelProperty(value = "Associated Opportunity")
	private String businessIds;

	@ApiModelProperty(value = "Associated Contact")
	private String contactsIds;

	@ApiModelProperty(value = "Next contact time")
	private Date nextTime;

	@ApiModelProperty(value = "0 deleted 1 not deleted")
	private Integer status;

	@ApiModelProperty(value = "longitude")
	private String lng;

	@ApiModelProperty(value = "latitude")
	private String lat;

	@ApiModelProperty(value = "Check-in address")
	private String address;

	@ApiModelProperty(value = "Creator ID")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "creation time")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "batch id")
	private String batchId;
	@ApiModelProperty(value = "nickname")
	@TableField(exist = false)
	private String realname;

	@ApiModelProperty(value = "avatar")
	@TableField(exist = false)
	private String userImg;

	@ApiModelProperty(value = "Customer ID")
	@TableField(exist = false)
	private Integer customerId;

	@ApiModelProperty(value = "Customer Name")
	@TableField(exist = false)
	private String customerName;

	@TableField(exist = false)
	private String activityTypeName;

	@TableField(exist = false)
	private String crmTypeName;

	@ApiModelProperty(value = "file")
	@TableField(exist = false)
	private List<FileEntity> file;
	@ApiModelProperty(value = "image")
	@TableField(exist = false)
	private List<FileEntity> img;

	@ApiModelProperty(value = "Opportunity List")
	@TableField(exist = false)
	private List<SimpleCrmEntity> businessList;

	@ApiModelProperty(value = "contact list")
	@TableField(exist = false)
	private List<SimpleCrmEntity> contactsList;

	@TableField(exist = false)
	private String createUserName;

	@TableField(exist = false)
	private String contactsNames;

	@TableField(exist = false)
	private String businessNames;
}
