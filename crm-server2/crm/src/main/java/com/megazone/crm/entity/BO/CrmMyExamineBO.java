package com.megazone.crm.entity.BO;

import com.megazone.core.entity.PageEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrmMyExamineBO extends PageEntity {
	private Integer status;

	private Integer categoryType;
}
