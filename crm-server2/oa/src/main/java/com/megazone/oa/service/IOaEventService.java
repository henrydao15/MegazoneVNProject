package com.megazone.oa.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.oa.entity.BO.*;
import com.megazone.oa.entity.PO.OaEvent;
import com.megazone.oa.entity.VO.QueryEventByIdVO;

import java.util.List;
import java.util.Set;

public interface IOaEventService extends BaseService<OaEvent> {

	void saveEvent(SetEventBO setEventBO);

	void updateEvent(SetEventBO setEventBO);

	void delete(DeleteEventBO deleteEventBO);

	List<OaEventDTO> queryList(QueryEventListBO queryEventListBO);

	Set<String> queryListStatus(QueryEventListBO queryEventListBO);

	QueryEventByIdVO queryById(QueryEventByIdBO queryEventByIdBO);

	void eventNotice(List<OaEvent> oaEventList);
}
