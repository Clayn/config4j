package de.clayntech.config4j.impl.key;

import de.clayntech.config4j.Key;
/**
 * Key implementation to access {@link Integer} values.
 *
 * @author Clayn
 * @since 0.1
 */
public class IntKey extends Key<Integer> {
    public IntKey(String key) {
        super(key);
    }

    @Override
    protected Class<Integer> getType() {
        return Integer.TYPE;
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
