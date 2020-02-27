package de.clayntech.config4j.util;

/**
 * Converts a value from one type to another or the other way round.
 *
 * @param <F> the type of the first value
 * @param <T> the type of the second value
 * @author Clayn
 * @since 0.1
 */
public interface Converter<F, T> {
    /**
     * Converts a value of the first type to the second one
     *
     * @param src the value to convert
     * @return the converted value
     */
    T convertForward(F src);

    /**
     * Converts a value of the second type to the first one
     *
     * @param src the value to convert
     * @return the converted value
     */
    F convertBackward(T src);
}
