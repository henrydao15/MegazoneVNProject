package com.megazone.oa.common.repetitionstrategy;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.megazone.oa.entity.BO.OaEventDTO;
import com.megazone.oa.entity.PO.OaEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component("Annually")
public class AnnuallyStrategy extends AbstractRepetitionStrategy {


	@Override
	public List<OaEventDTO> processQuery(List<OaEvent> oaEventList, long startTime, long endTime) {
		List<OaEventDTO> eventList = new ArrayList<>();
		List<DateTime> dateTimes = DateUtil.rangeToList(new Date(startTime), new Date(endTime), DateField.DAY_OF_YEAR);
		for (DateTime day : dateTimes) {
			for (OaEvent oaEvent : oaEventList) {
				Date endDate = oaEvent.getRepeatEndTime();
				DateTime offStartTime = DateUtil.parseDateTime(day.year() + "-" + DateUtil.format(oaEvent.getStartTime(), "MM-dd HH:mm:ss"));
				if (isContinue(oaEvent, offStartTime, endDate)) {
					continue;
				}
				if (oaEvent.getStartTime().getTime() > day.getTime()) {
					continue;
				}
				long betweenYear = DateUtil.betweenYear(oaEvent.getStartTime(), offStartTime, true);
				Integer repeatRate = oaEvent.getRepeatRate();
				if (betweenYear % repeatRate != 0) {
					continue;
				}
				if (!DateUtil.isSameDay(day, offStartTime)) {
					continue;
				}
				long duration = oaEvent.getEndTime().getTime() - oaEvent.getStartTime().getTime();
				OaEventDTO oaEventDTO = transfer(oaEvent);
				oaEventDTO.setStartTime(offStartTime.getTime());
				oaEventDTO.setEndTime(offStartTime.getTime() + duration);
				eventList.add(oaEventDTO);
			}
		}
		return eventList;
	}

	@Override
	protected Date processCountTime(Date startTime, int offset) {
		return DateUtil.offset(startTime, DateField.YEAR, offset);
	}


}
