package net.bplaced.clayn.impl.key;

import net.bplaced.clayn.config4j.Key;

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
