package com.megazone.oa.entity.BO;

import com.megazone.oa.entity.PO.OaEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEventBO {

	private Integer type;

	private Long time;

	private OaEvent event;
}
