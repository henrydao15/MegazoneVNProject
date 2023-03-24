package com.megazone.core.common;

import java.lang.annotation.*;

/**
 * Internal methods that swagger does not scan, which is convenient for subsequent expansion
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface ApiExplain {
	String value();
}
