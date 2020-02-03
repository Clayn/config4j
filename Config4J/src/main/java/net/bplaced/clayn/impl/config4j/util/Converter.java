package net.bplaced.clayn.impl.config4j.util;

public interface Converter<F, T> {
    T convertForward(F src);

    F convertBackward(T src);
}
