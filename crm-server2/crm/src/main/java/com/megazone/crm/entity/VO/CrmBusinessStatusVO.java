package com.megazone.crm.entity.VO;

import com.megazone.crm.entity.PO.CrmBusinessStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author
 */
@Data
@ToString
@ApiModel
public class CrmBusinessStatusVO {

	@ApiModelProperty
	public Integer businessId;

	@ApiModelProperty
	public Integer statusId;

	@ApiModelProperty
	public Integer isEnd;

	@ApiModelProperty
	public String statusRemark;

	public List<CrmBusinessStatus> statusList;
}
