package net.bplaced.clayn.impl.config4j.event;

import net.bplaced.clayn.impl.config4j.Key;

import java.util.Objects;

public class ConfigurationChangeEvent {
    private final String key;
    private final String oldValue;
    private final String newValue;

    public ConfigurationChangeEvent(String key,String oldValue, String newValue) {
        Objects.requireNonNull(key);
        this.key = key;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public String getKey() {
        return key;
    }


    public String getOldValue() {
        return oldValue;
    }

    public String getNewValue() {
        return newValue;
    }
}
