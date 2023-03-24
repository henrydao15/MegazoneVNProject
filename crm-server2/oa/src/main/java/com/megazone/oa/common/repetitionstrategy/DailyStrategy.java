package com.megazone.oa.common.repetitionstrategy;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.megazone.oa.entity.BO.OaEventDTO;
import com.megazone.oa.entity.PO.OaEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component("Daily")
public class DailyStrategy extends AbstractRepetitionStrategy {

	@Override
	public List<OaEventDTO> processQuery(List<OaEvent> oaEventList, long startTime, long endTime) {
		List<OaEventDTO> eventList = new ArrayList<>();
		for (OaEvent oaEvent : oaEventList) {
			Date startDate = oaEvent.getRepeatStartTime();
			Date endDate = oaEvent.getRepeatEndTime();
			Integer repeatRate = oaEvent.getRepeatRate();
			int betweenDay = (int) DateUtil.betweenDay(oaEvent.getStartTime(), oaEvent.getEndTime(), true);
			long startTime1 = startTime - DAY_TIME * betweenDay;
			int betweenStartDay = (int) DateUtil.between(startDate, new Date(startTime1), DateUnit.DAY, false);
			int betweenEndDay = (int) DateUtil.between(startDate, new Date(endTime), DateUnit.DAY, false);
			if (betweenEndDay < 0) {
				continue;
			}
			if (betweenStartDay < 0) {
				betweenStartDay = 0;
			}
			int startCount = betweenStartDay / repeatRate;
			int endCount = betweenEndDay / repeatRate + 1;
			for (int i = startCount; i < endCount; i++) {
				int offset = repeatRate * i;
				DateTime offStartTime = DateUtil.offsetDay(oaEvent.getStartTime(), offset);
				DateTime offEndTime = DateUtil.offsetDay(oaEvent.getEndTime(), offset);
				if (offEndTime.getTime() < startTime) {
					continue;
				}
				if (isContinue(oaEvent, offStartTime, endDate)) {
					continue;
				}
				OaEventDTO oaEventDTO = transfer(oaEvent);
				oaEventDTO.setStartTime(offStartTime.getTime());
				oaEventDTO.setEndTime(offEndTime.getTime());
				eventList.add(oaEventDTO);
			}
		}
		return eventList;
	}

	@Override
	protected Date processCountTime(Date startTime, int offset) {
		return DateUtil.offsetDay(startTime, offset);
	}


}
