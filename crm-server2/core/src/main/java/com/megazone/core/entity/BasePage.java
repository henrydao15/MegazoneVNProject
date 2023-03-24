package com.megazone.core.entity;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Default paging
 */
public class BasePage<T> implements IPage<T> {

	private static final long serialVersionUID = 8545996863226528798L;

	/**
	 * Query data list
	 */
	private List<T> list = new ArrayList<>();
	/**
	 * total
	 */
	private long totalRow = 0;

	/**
	 * The number of lines displayed per page, the default is 15
	 */
	private long pageSize = 15;

	/**
	 * current page
	 */
	private long pageNumber = 1;

	/**
	 * Sort field information
	 */
	private List<OrderItem> orders = new ArrayList<>();

	/**
	 * Automatically optimize COUNT SQL
	 */
	private boolean optimizeCountSql = true;
	/**
	 * Whether to perform count query
	 */
	private boolean isSearchCount = true;

	/**
	 * additional data
	 */
	private Object extraData;


	public BasePage() {

	}

	/**
	 * Pagination constructor
	 *
	 * @param current current page
	 * @param size    The number of bars displayed per page
	 */
	public BasePage(long current, long size) {
		this(current, size, 0);
	}

	public BasePage(long current, long size, long total) {
		this(current, size, total, true);
	}

	public BasePage(long current, long size, boolean isSearchCount) {
		this(current, size, 0, isSearchCount);
	}

	public BasePage(long current, long size, long total, boolean isSearchCount) {
		if (current > 1) {
			this.pageNumber = current;
		}
		this.pageSize = size;
		this.totalRow = total;
		this.isSearchCount = isSearchCount;
	}


	@Override
	@JsonIgnore
	public List<T> getRecords() {
		return this.list;
	}

	@Override
	public BasePage<T> setRecords(List<T> records) {
		this.list = records;
		return this;
	}

	public List<T> getList() {
		return this.list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public long getTotalRow() {
		return this.totalRow;
	}

	public long getTotalPage() {
		if (getSize() == 0) {
			return 0L;
		}
		long pages = getTotal() / getSize();
		if (getTotal() % getSize() != 0) {
			pages++;
		}
		return pages;
	}

	public long getPageSize() {
		return this.pageSize;
	}

	public long getPageNumber() {
		return this.pageNumber;
	}

	public void setPageNumber(long pageNumber) {
		this.pageNumber = pageNumber;
	}

	public boolean isFirstPage() {
		return this.pageNumber == 1L;
	}

	public boolean isLastPage() {
		return getTotal() == 0 || this.pageNumber == getTotalPage();
	}

	@Override
	@JsonIgnore
	public long getTotal() {
		return this.totalRow;
	}

	@Override
	public BasePage<T> setTotal(long total) {
		this.totalRow = total;
		return this;
	}

	@Override
	@JsonIgnore
	public long getSize() {
		return this.pageSize;
	}

	@Override
	public BasePage<T> setSize(long size) {
		this.pageSize = size;
		return this;
	}

	@Override
	@JsonIgnore
	public long getCurrent() {
		return this.pageNumber;
	}

	@Override
	public BasePage<T> setCurrent(long current) {
		this.pageNumber = current;
		return this;
	}


	/**
	 * Add new sorting criteria
	 *
	 * @param items condition
	 * @return returns the paging parameter itself
	 */
	public BasePage<T> addOrder(OrderItem... items) {
		orders.addAll(Arrays.asList(items));
		return this;
	}

	@Override
	public List<OrderItem> orders() {
		return orders;
	}

	@Override
	public boolean optimizeCountSql() {
		return optimizeCountSql;
	}

	@Override
	@JsonIgnore
	public boolean isSearchCount() {
		if (totalRow < 0) {
			return false;
		}
		return isSearchCount;
	}

	public void setSearchCount(boolean isSearchCount) {
		this.isSearchCount = isSearchCount;
	}

	public BasePage<T> setOptimizeCountSql(boolean optimizeCountSql) {
		this.optimizeCountSql = optimizeCountSql;
		return this;
	}

	/**
	 * Type conversion via beanCopy
	 *
	 * @param clazz converted class
	 * @param <R>   R
	 * @return BasePage
	 */
	public <R> BasePage<R> copy(Class<R> clazz) {
		return copy(clazz, obj -> BeanUtil.copyProperties(obj, clazz));
	}

	/**
	 * Type conversion via beanCopy
	 *
	 * @param clazz converted class
	 * @param <R>   R
	 * @return BasePage
	 */
	public <R> BasePage<R> copy(Class<R> clazz, Function<? super T, ? extends R> mapper) {
		BasePage<R> basePage = new BasePage<>(getCurrent(), getSize(), getTotal(), isSearchCount());
		basePage.setRecords(getRecords().stream().map(mapper).collect(Collectors.toList()));
		return basePage;
	}

	@Override
	@JsonIgnore
	public long getPages() {
		return getTotalPage();
	}

	public Object getExtraData() {
		return extraData;
	}

	public void setExtraData(Object extraData) {
		this.extraData = extraData;
	}
}
