package net.bplaced.clayn.config4j;

import net.bplaced.clayn.config4j.util.Converter;

public abstract class Key<T> implements Converter<T, String> {

    private final String key;

    public Key(String key) {
        this.key = key;
    }

    public abstract T fromString(String str);

    public abstract String toString(T val);

    @Override
    public String convertForward(T src) {
        return toString(src);
    }

    @Override
    public T convertBackward(String src) {
        return fromString(src);
    }

    public static Key<String> createBasicKey(String key) {
        return new BasicKey(key);
    }

    public String getKey() {
        return key;
    }

    private static final class BasicKey extends Key<String> {

        public BasicKey(String key) {
            super(key);
        }

        @Override
        public String toString(String src) {
            return src;
        }

        @Override
        public String fromString(String src) {
            return src;
        }
    }
}
