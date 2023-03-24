package com.megazone.hrm.entity.VO;

import com.megazone.hrm.entity.PO.HrmActionRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class QueryRecordListVO extends HrmActionRecord {

	//    private AdminUser adminUser
	@ApiModelProperty
	private String img;

	@ApiModelProperty
	private String realname;
}
