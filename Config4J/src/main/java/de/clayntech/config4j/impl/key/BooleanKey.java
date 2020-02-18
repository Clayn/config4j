package de.clayntech.config4j.impl.key;

import de.clayntech.config4j.Key;

public class BooleanKey extends Key<Boolean> {
    public BooleanKey(String key) {
        super(key);
    }

    @Override
    public Boolean fromString(String str) {
        return Boolean.parseBoolean(str);
    }

    @Override
    public String toString(Boolean val) {
        return String.valueOf(val);
    }
}
