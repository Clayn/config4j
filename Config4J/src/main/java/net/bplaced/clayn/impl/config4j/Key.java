package net.bplaced.clayn.impl.config4j;

import net.bplaced.clayn.impl.config4j.util.Converter;

public abstract class Key<T> implements Converter<T, String> {

    private final String key;

    public Key(String key) {
        this.key = key;
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
        public String convertForward(String src) {
            return src;
        }

        @Override
        public String convertBackward(String src) {
            return src;
        }
    }
}
