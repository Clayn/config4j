package de.clayntech.config4j;

import de.clayntech.config4j.event.ConfigurationListener;

import java.util.Objects;
import java.util.Set;

/**
 * Provides the basic API to store and get values. The values must be parsable to strings associated with string keys.
 */
public interface Configuration {

    /**
     * Adds a new configuration listener that will be informed if a setting was changed.
     *
     * @param listener the listener to add
     */
    void addListener(ConfigurationListener listener);

    /**
     * Removes the given configuration listener.
     *
     * @param listener the listener to remove
     */
    void removeListener(ConfigurationListener listener);

    /**
     * Returns the value for the given key. If no such value was found, returns {@code null}.
     * <br>
     * Defaults to: {@code return get(key,null);}
     *
     * @param key the key to get the value for
     * @return the value stored with the given key or {@code null} if no value is available
     * @see #get(String, String)
     * @see #get(Key)
     * @see #get(Key, Object)
     */
    default String get(String key) {
        return get(key, null);
    }

    /**
     * Returns the value for the given key. If no such value was found, returns the {@code def} value.
     *
     * @param key the key to get the value for
     * @param def the default value if no value was found
     * @return the value stored with the given key or {@code def} if no value is available
     * @see #get(String)
     * @see #get(Key)
     * @see #get(Key, Object)
     */
    String get(String key, String def);

    /**
     * Returns the value for the given key. If no such value was found, returns the {@code def} value.
     *
     * @param key the key to get the value for
     * @param def the default value if no value was found
     * @return the value stored with the given key or {@code def} if no value is available
     * @see #get(String)
     * @see #get(Key)
     * @see #get(String, String)
     */
    default <T> T get(Key<T> key, T def) {
        Objects.requireNonNull(key);
        String val = get(key.getKey(), null);
        if (val == null) {
            return def;
        }
        return key.convertBackward(val);
    }

    /**
     * Returns the value for the given key. If no such value was found, returns {@code null}.
     * <br>
     * Defaults to: {@code return get(key,null);}
     *
     * @param key the key to get the value for. Must not be {@code null}
     * @return the value stored with the given key or {@code null} if no value is available
     * @see #get(String, String)
     * @see #get(Key)
     * @see #get(String)
     */
    default <T> T get(Key<T> key) {
        Objects.requireNonNull(key);
        return get(key, null);
    }

    /**
     * Stores the given value with the given key. Key and value must not be {@code null}.
     * The key is used to parse the value to a string.<br>
     * Defaults to: {@code set(key.getKey(),key.convertForward(val));}
     *
     * @param key the key for the value
     * @param val the value to store
     * @see #set(String, String)
     */
    default <T> void set(Key<T> key, T val) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(val);
        set(key.getKey(), key.convertForward(val));
    }

    /**
     * Stores the given value with the given key. Key and value must not be {@code null}.
     *
     * @param key the key for the value
     * @param val the value to store
     * @see #set(Key, Object)
     */
    void set(String key, String val);

    /**
     * Returns all settings stored in this configuration. No key in the returned set should return {@code null} when
     * used with {@link #get(String)}.
     *
     * @return a set of all setting keys in this configuration.
     */
    Set<String> getConfigurations();
}
