package com.megazone.crm.entity.BO;

import com.megazone.core.entity.PageEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrmMarketingPageBO extends PageEntity {

	private Integer crmType;

	private String search;

	private Integer timeType;

	private Long userId;
}
