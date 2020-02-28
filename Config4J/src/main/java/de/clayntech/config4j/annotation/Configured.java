package de.clayntech.config4j.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for fields that can be configured using a configuration. Final fields are not supported.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Configured {
    /**
     * The key of the value for this field
     *
     * @return the key of the value for this field
     */
    String key();

    /**
     * The type of the key to access the value. If not set the type of the field gets used.
     * Using this value you can use custom key classes.<br><br>
     * <b>Example: </b>
     * <br>
     * {@code  @Configured(key="Key")}
     * <br>
     * {@code private int field;}
     *
     *
     * <br><br>
     * will use a {@link de.clayntech.config4j.impl.key.IntKey} with the for the key {@code key}
     *
     * @return the type of the key to access the value
     */
    Class<?> keyType() default Auto.class;

    /**
     * Optional set method that will be used instead of directly setting the key value. The method can only
     * have one parameter that can be assigned from {@link #keyType()}
     *
     * @return the name of the set method for the field.
     */
    String set() default "";

    static final class Auto {

    }
}
