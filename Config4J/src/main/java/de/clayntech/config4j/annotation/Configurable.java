package de.clayntech.config4j.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate a method or field relies on a {@link de.clayntech.config4j.conf.Config4JSetting}.
 * The documentation should precise in what way the setting is used.
 *
 * @author Clayn
 * @since 0.1
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface Configurable {
    String[] value();
}
