package de.clayntech.config4j.impl.key;

import de.clayntech.config4j.Key;
/**
 * Key implementation to access {@link Double} values.
 *
 * @author Clayn
 * @since 0.1
 */
public class DoubleKey extends Key<Double> {
    public DoubleKey(String key) {
        super(key);
    }

    @Override
    protected Class<Double> getType() {
        return Double.TYPE;
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
