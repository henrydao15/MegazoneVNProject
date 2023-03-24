package com.megazone.core.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Objects;

/**
 * The class to be inherited for paging
 */
@Data
public class PageEntity {
	@ApiModelProperty
	private Integer page = 1;

	@ApiModelProperty
	private Integer limit = 15;

	@ApiModelProperty(value = "Whether paging, 0: no paging 1 paging", allowableValues = "0,1")
	private Integer pageType = 1;

	public <T> BasePage<T> parse() {
		BasePage<T> page = new BasePage<>(getPage(), getLimit());
		if (Objects.equals(0, pageType)) {
			page.setSize(10000);
		}
		return page;
	}

	public void setPageType(Integer pageType) {
		this.pageType = pageType;
		if (pageType == 0) {
			limit = 10000;
		}
	}

	public Integer getLimit() {
		if (limit > 100 && 1 == pageType) {
			limit = 100;
		}
		return limit;
	}
}
