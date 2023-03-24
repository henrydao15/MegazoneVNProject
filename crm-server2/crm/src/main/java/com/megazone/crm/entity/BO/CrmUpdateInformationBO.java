package com.megazone.crm.entity.BO;

import com.megazone.core.common.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CrmUpdateInformationBO {

	private List<JSONObject> list;

	private Integer label;

	private Integer id;

	private String batchId;
}
