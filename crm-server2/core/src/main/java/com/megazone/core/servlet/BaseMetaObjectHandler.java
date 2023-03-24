package com.megazone.core.servlet;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.megazone.core.utils.UserUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author mybatisPlus
 */
@Component
public class BaseMetaObjectHandler implements MetaObjectHandler {

	public static final String FIELD_CREATE_TIME = "createTime";
	public static final String FIELD_UPDATE_TIME = "updateTime";
	public static final String FIELD_CREATE_USER = "createUserId";

	/**
	 * @param metaObject
	 */
	@Override
	public void insertFill(MetaObject metaObject) {
		Date date = new Date();
		this.strictInsertFill(metaObject, FIELD_CREATE_TIME, Date.class, date);
		this.strictInsertFill(metaObject, FIELD_CREATE_USER, Long.class, UserUtil.getUserId());
	}


	@Override
	public MetaObjectHandler fillStrategy(MetaObject metaObject, String fieldName, Object fieldVal) {
		setFieldValByName(fieldName, fieldVal, metaObject);
		return this;
	}

	/**
	 * @param metaObject
	 */
	@Override
	public void updateFill(MetaObject metaObject) {
		this.strictUpdateFill(metaObject, FIELD_UPDATE_TIME, Date.class, new Date());
		TableInfo tableInfo = findTableInfo(metaObject);
	}
}
