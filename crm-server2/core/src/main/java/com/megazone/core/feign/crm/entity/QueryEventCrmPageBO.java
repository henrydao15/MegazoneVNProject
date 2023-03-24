package com.megazone.core.feign.crm.entity;

import com.megazone.core.entity.PageEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryEventCrmPageBO extends PageEntity {

	private Long userId;

	private Long time;

	private Integer type;

}
