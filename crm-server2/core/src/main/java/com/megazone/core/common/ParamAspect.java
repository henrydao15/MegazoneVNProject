package com.megazone.core.common;

import java.lang.annotation.*;

/**
 * Methods with this annotation are not facet scanned
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface ParamAspect {
}
