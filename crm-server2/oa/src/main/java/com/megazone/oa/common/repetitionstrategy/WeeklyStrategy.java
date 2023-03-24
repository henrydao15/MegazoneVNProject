package com.megazone.oa.common.repetitionstrategy;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.megazone.core.utils.TagUtil;
import com.megazone.oa.entity.BO.OaEventDTO;
import com.megazone.oa.entity.PO.OaEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component("Weekly")
public class WeeklyStrategy extends AbstractRepetitionStrategy {


	@Override
	public List<OaEventDTO> processQuery(List<OaEvent> oaEventList, long startTime, long endTime) {
		List<OaEventDTO> eventList = new ArrayList<>();
		for (OaEvent oaEvent : oaEventList) {
			Date startDate = oaEvent.getRepeatStartTime();
			Date endDate = oaEvent.getRepeatEndTime();
			long duration = oaEvent.getEndTime().getTime() - oaEvent.getStartTime().getTime();
			Integer repeatRate = oaEvent.getRepeatRate();
			//3:/4:
			String repeatTime = oaEvent.getRepeatTime();
			Set<Integer> repeatTimeList = TagUtil.toSet(repeatTime);
			int betweenDay = (int) DateUtil.betweenDay(oaEvent.getStartTime(), oaEvent.getEndTime(), false);
			long startTime1 = startTime - DAY_TIME * betweenDay;

			int betweenStartWeek = (int) DateUtil.between(startDate, new Date(startTime1), DateUnit.WEEK, false);
			int betweenEndWeek = (int) DateUtil.between(startDate, new Date(endTime), DateUnit.WEEK, false);
			if (betweenEndWeek < 0) {
				continue;
			}
			if (betweenStartWeek < 0) {
				betweenStartWeek = 1;
			}
			int startCount = betweenStartWeek / repeatRate;
			int endCount = betweenEndWeek / repeatRate;

			for (int i = startCount; i <= endCount; i++) {
				int offset = repeatRate * i;
				DateTime beginOfWeek = DateUtil.beginOfWeek(DateUtil.offsetWeek(oaEvent.getStartTime(), offset));
				for (int day : repeatTimeList) {
					DateTime offStartTime = DateUtil.offsetDay(beginOfWeek, day - 1);
					if (isContinue(oaEvent, offStartTime, endDate)) {
						continue;
					}
					DateTime newStartTime = DateUtil.parseDateTime(DateUtil.formatDate(offStartTime) + " " + DateUtil.formatTime(oaEvent.getStartTime()));
					OaEventDTO oaEventDTO = transfer(oaEvent);
					oaEventDTO.setStartTime(newStartTime.getTime());
					oaEventDTO.setEndTime(newStartTime.getTime() + duration);
					eventList.add(oaEventDTO);
				}
			}
		}
		return eventList;
	}

	@Override
	protected Date processCountTime(Date startTime, int offset) {
		return DateUtil.offsetWeek(startTime, offset);
	}
}
