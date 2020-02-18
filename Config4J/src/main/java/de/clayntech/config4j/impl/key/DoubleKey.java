package de.clayntech.config4j.impl.key;

import de.clayntech.config4j.Key;

public class DoubleKey extends Key<Double> {
    public DoubleKey(String key) {
        super(key);
    }

    @Override
    public Double fromString(String str) {
        return Double.parseDouble(str);
    }

    @Override
    public String toString(Double val) {
        return String.valueOf(val);
    }
}
