package com.megazone.oa.service.impl;

import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.oa.entity.PO.OaLogRecord;
import com.megazone.oa.mapper.OaLogRecordMapper;
import com.megazone.oa.service.IOaLogRecordService;
import org.springframework.stereotype.Service;

@Service
public class OaLogRecordServiceImpl extends BaseServiceImpl<OaLogRecordMapper, OaLogRecord> implements IOaLogRecordService {

}
