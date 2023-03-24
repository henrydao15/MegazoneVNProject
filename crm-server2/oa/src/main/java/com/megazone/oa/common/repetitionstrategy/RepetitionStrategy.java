package com.megazone.oa.common.repetitionstrategy;

import com.megazone.oa.entity.BO.OaEventDTO;
import com.megazone.oa.entity.PO.OaEvent;

import java.util.List;
import java.util.Map;

public interface RepetitionStrategy {
	List<OaEventDTO> processQuery(List<OaEvent> oaEventList, long startTime, long endTime);

	void setSpecialDayMap(Map<String, Integer> specialDayMap);

	OaEvent processTime(OaEvent oaEvent);
}
