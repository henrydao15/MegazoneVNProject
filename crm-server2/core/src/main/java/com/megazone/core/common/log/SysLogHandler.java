package com.megazone.core.common.log;

import com.megazone.core.common.SubModelType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface SysLogHandler {

	/**
	 * module name
	 */
	String applicationName() default "";

	/**
	 * submodule name
	 */
	SubModelType subModel() default SubModelType.NULL;


	/**
	 * operate
	 */
	BehaviorEnum behavior() default BehaviorEnum.NULL;

	/**
	 * Operation object
	 */
	String object() default "";

	/**
	 * Operation details
	 */
	String detail() default "";

	/**
	 * Default is false
	 * The false annotation is in the Controller, you need to rewrite the operation log logic yourself and return Content
	 * The true annotation is in the operation record implementation class. For methods that have already written operation records, the return value of the operation record is directly modified to the Content type, and the aspect directly obtains the return value.
	 */
	boolean isReturn() default false;

}
