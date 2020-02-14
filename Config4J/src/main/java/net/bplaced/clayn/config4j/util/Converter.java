package net.bplaced.clayn.config4j.util;

public interface Converter<F, T> {
    T convertForward(F src);

    F convertBackward(T src);
}
