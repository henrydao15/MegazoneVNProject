package com.megazone.crm.entity.BO;

import com.megazone.core.entity.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class CrmBackLogBO extends PageEntity {

	@ApiModelProperty
	private Integer type;

	@ApiModelProperty
	private Integer isSub;

	@ApiModelProperty(value = "")
	private List<CrmSearchBO.Search> data = new ArrayList<>();


}
