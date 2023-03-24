package com.megazone.crm.entity.VO;

import com.megazone.crm.entity.PO.CrmActivity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class CrmActivityVO {

	@ApiModelProperty
	private String time;

	@ApiModelProperty
	private List<CrmActivity> list;

	@ApiModelProperty
	private Boolean lastPage;
}
