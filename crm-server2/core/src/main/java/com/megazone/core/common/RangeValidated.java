package com.megazone.core.common;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Range check annotation (min cannot be greater than max)
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RangeValidator.class)
public @interface RangeValidated {
	String message() default "Start time is less than end time";

	String[] minFieldName() default "startTime";

	String[] maxFieldName() default "endTime";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
