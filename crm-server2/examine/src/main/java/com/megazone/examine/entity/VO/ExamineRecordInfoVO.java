package com.megazone.examine.entity.VO;

import com.megazone.core.feign.crm.entity.SimpleCrmInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author
 * @date 2020/12/25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ExamineRecordInfoVO extends SimpleCrmInfo {

	private Integer logId;

	@ApiModelProperty(value = "ID")
	private Long examineId;

	@ApiModelProperty(value = "ID")
	private Integer flowId;

	@ApiModelProperty(value = "ID")
	private Integer recordId;

	@ApiModelProperty(value = "1  2  3 ")
	private Integer type;

	@ApiModelProperty(value = "")
	private String remarks;

	@ApiModelProperty(value = "")
	private Integer sort;

	@ApiModelProperty(value = "0、1、2、3 4: 5  6  7  8 ")
	private Integer examineStatus;

	@ApiModelProperty(value = "ID")
	private Long examineUserId;

	@ApiModelProperty(value = "ID")
	private Integer examineRoleId;

	@ApiModelProperty(value = "")
	private Date createTime;

	@ApiModelProperty(value = "")
	private Date updateTime;

	@ApiModelProperty(value = "ID")
	private String batchId;
}
