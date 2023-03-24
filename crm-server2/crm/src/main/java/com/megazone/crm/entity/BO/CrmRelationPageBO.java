package com.megazone.crm.entity.BO;

import com.megazone.core.entity.PageEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrmRelationPageBO extends PageEntity {

	private Integer customerId;

	private Integer contractId;
}
