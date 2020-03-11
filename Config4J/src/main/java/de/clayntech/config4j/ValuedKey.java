package de.clayntech.config4j;

public class ValuedKey<T> extends Key<T> {
    private final T defaultValue;
    private final Key<T> originalKey;

    public ValuedKey(Key<T> orig, T defaultValue) {
        super(orig.getKey());
        this.defaultValue = defaultValue;
        this.originalKey = orig;
    }

    public final T getDefaultValue() {
        return defaultValue;
    }

    @Override
    public T fromString(String str) throws ValueParsingException {
        return originalKey.fromString(str);
    }

    @Override
    public String toString(T val) throws ValueParsingException {
        return originalKey.toString(val);
    }

    @Override
    protected Class<T> getType() {
        return originalKey.getType();
    }
}
