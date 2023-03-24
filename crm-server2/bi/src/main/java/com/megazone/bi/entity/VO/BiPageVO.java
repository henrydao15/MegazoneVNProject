package com.megazone.bi.entity.VO;

import com.megazone.core.entity.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BiPageVO<T> extends BasePage<T> {

	private Object duration;

	private Object money;
}
