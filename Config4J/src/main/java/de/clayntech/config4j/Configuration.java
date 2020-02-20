package de.clayntech.config4j;

import de.clayntech.config4j.event.ConfigurationListener;

import java.util.Objects;
import java.util.Set;

public interface Configuration {

    void addListener(ConfigurationListener listener);

    void removeListener(ConfigurationListener listener);

    default String get(String key) {
        return get(key, null);
    }

    String get(String key, String def);

    default <T> T get(Key<T> key, T def) {
        Objects.requireNonNull(key);
        String val = get(key.getKey(), null);
        if (val == null) {
            return def;
        }
        return key.convertBackward(val);
    }

    default <T> T get(Key<T> key) {
        Objects.requireNonNull(key);
        return get(key, null);
    }

    default <T> void set(Key<T> key, T val) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(val);
        set(key.getKey(), key.convertForward(val));
    }

    void set(String key, String val);

    Set<String> getConfigurations();
}
