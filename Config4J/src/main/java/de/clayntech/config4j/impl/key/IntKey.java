package de.clayntech.config4j.impl.key;

import de.clayntech.config4j.Key;

public class IntKey extends Key<Integer> {
    public IntKey(String key) {
        super(key);
    }

    @Override
    public Integer fromString(String str) {
        return Integer.parseInt(str);
    }

    @Override
    public String toString(Integer val) {
        return String.valueOf(val);
    }
}
