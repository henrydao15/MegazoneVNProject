package com.megazone.core.servlet;

import java.lang.annotation.*;

/**
 * @author
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface LoginFromCookie {
}
