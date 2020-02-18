package de.clayntech.config4j.util;

public interface Converter<F, T> {
    T convertForward(F src);

    F convertBackward(T src);
}
