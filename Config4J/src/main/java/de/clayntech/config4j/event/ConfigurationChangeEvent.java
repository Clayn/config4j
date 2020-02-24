package de.clayntech.config4j.event;

import java.util.Objects;

/**
 * Event that holds several information about a configuration change
 */
public class ConfigurationChangeEvent {
    private final String key;
    private final String oldValue;
    private final String newValue;

    public ConfigurationChangeEvent(String key, String oldValue, String newValue) {
        Objects.requireNonNull(key);
        this.key = key;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    /**
     * Returns the key of the configuration that was changed.
     *
     * @return the key of the configuration that was changed
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the old value of the key. May be {@code null} if it wasn't set before
     *
     * @return the old value of the key. May be {@code null} if it wasn't set before
     */
    public String getOldValue() {
        return oldValue;
    }

    /**
     * Returns the new value of the key.
     *
     * @return the new value of the key
     */
    public String getNewValue() {
        return newValue;
    }
}
