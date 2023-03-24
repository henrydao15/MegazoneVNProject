package com.megazone.oa.entity.BO;

import com.megazone.oa.entity.PO.OaEvent;
import com.megazone.oa.entity.PO.OaEventNotice;
import com.megazone.oa.entity.PO.OaEventRelation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SetEventBO {

	@ApiModelProperty
	private OaEvent event;

	@ApiModelProperty
	private OaEventRelation relation;

	@ApiModelProperty
	private List<OaEventNotice> notice;

	private Integer type;

	private Long time;

}
