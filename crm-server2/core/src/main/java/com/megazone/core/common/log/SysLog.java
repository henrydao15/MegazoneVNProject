package com.megazone.core.common.log;

import com.megazone.core.common.SubModelType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface SysLog {
	SubModelType subModel() default SubModelType.NULL;

	Class logClass() default void.class;
}
