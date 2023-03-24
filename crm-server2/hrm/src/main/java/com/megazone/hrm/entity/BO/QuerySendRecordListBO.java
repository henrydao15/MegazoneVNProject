package com.megazone.hrm.entity.BO;

import com.megazone.core.entity.PageEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuerySendRecordListBO extends PageEntity {

	private Integer year;

	private Integer month;
}
