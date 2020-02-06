package net.bplaced.clayn.impl.config4j;

import net.bplaced.clayn.impl.config4j.event.ConfigurationListener;

import java.util.Set;

public interface Configuration {

    void addListener(ConfigurationListener listener);
    void removeListener(ConfigurationListener listener);
    default String get(String key) {
        return get(key, null);
    }

    String get(String key, String def);

    default <T> T get(Key<T> key, T def) {
        String val = get(key.getKey(), null);
        if (val == null) {
            return def;
        }
        return key.convertBackward(val);
    }

    default <T> T get(Key<T> key) {
        return get(key, null);
    }

    default <T> void set(Key<T> key, T val) {
        set(key.getKey(), key.convertForward(val));
    }

    void set(String key, String val);

    Set<String> getConfigurations();

    Set<String> getProfiles();

    Configuration getProfile(String profile);
}
