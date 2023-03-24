package com.megazone.crm.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import com.megazone.core.common.RangeValidated;
import com.megazone.core.servlet.upload.FileEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-08-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_crm_marketing")
@ApiModel(value = "CrmMarketing", description = "")
@RangeValidated(minFieldName = "startTime", maxFieldName = "endTime", message = "")
public class CrmMarketing implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	@TableId(value = "marketing_id", type = IdType.AUTO)
	private Integer marketingId;

	@ApiModelProperty(value = "")
	private String marketingName;

	@ApiModelProperty(value = "1  2")
	private Integer crmType;

	@ApiModelProperty(value = "")
	private Date endTime;

	@ApiModelProperty(value = "ID")
	private String relationUserId;

	@ApiModelProperty(value = "ID")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "1  0")
	private Integer status;

	@ApiModelProperty(value = " 0 1")
	private Integer second;

	@ApiModelProperty(value = "")
	private String fieldDataId;

	@ApiModelProperty(value = "")
	private Integer browse;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;


	@ApiModelProperty(value = "")
	private Date startTime;

	@ApiModelProperty(value = "")
	private Integer shareNum;

	@ApiModelProperty(value = "")
	private Integer submitNum;

	@ApiModelProperty(value = "")
	private String synopsis;

	@ApiModelProperty(value = "id")
	private String mainFileIds;

	private String detailFileIds;

	@ApiModelProperty(value = "")
	private String address;

	@ApiModelProperty(value = "")
	private String marketingType;

	@ApiModelProperty(value = "")
	private BigDecimal marketingMoney;

	@TableField(exist = false)
	private String createUserName;

	@TableField(exist = false)
	private String crmTypeName;

	@TableField(exist = false)
	private List<FileEntity> mainFileList;

	@TableField(exist = false)
	private FileEntity mainFile;


	@ApiModelProperty(value = "ID")
	private String relationDeptId;


}
