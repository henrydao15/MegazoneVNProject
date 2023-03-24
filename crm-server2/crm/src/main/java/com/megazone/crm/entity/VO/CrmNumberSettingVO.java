package com.megazone.crm.entity.VO;

import com.megazone.crm.entity.PO.CrmNumberSetting;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author
 */
@ToString
@Data
@ApiModel
public class CrmNumberSettingVO {

	@ApiModelProperty(value = "ID", required = true)
	private Integer settingId;

	@ApiModelProperty(value = "", required = true, allowableValues = "0,1")
	private Integer status;

	@ApiModelProperty(value = "", required = true)
	private String label;

	@ApiModelProperty
	private List<CrmNumberSetting> setting;
}
