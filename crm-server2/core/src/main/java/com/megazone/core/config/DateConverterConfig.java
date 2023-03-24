package com.megazone.core.config;

import cn.hutool.core.date.DateUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class DateConverterConfig implements Converter<String, Date> {

	@Override
	public Date convert(@Nullable String dateStr) {
		return DateUtil.parse(dateStr);
	}

}
