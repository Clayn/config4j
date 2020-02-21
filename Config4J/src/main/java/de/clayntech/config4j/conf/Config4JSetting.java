package de.clayntech.config4j.conf;

import de.clayntech.config4j.Config4J;
import de.clayntech.config4j.Key;
import de.clayntech.config4j.impl.key.KeyFactory;

/**
 * Manages the setting used by components of the Config4J project. A directly access to the project configuration value
 * can be done with this class.
 *
 * @param <T> the type of the setting
 */
public final class Config4JSetting<T> {
    public static final Config4JSetting<Boolean> THROW_ERROR_ON_SOURCE_MISSING = new Config4JSetting<>(KeyFactory.createKey("config4j.setting.source.missing.throw", boolean.class), true);
    public static final Config4JSetting<Boolean> DEBUG_LOADED_DEFAULT_CONFIGURATION = new Config4JSetting<>(KeyFactory.createKey("config4j.setting.debug.default", boolean.class), false);
    public static final Config4JSetting<Boolean> CONFIGURATION_BASE_UNMODIFIABLE_LIST = new Config4JSetting<>(KeyFactory.createKey("config4j.setting.base.unmodifiable", boolean.class), false);


    private final Key<T> settingsKey;
    private final T defaultValue;

    public Config4JSetting(Key<T> settingsKey, T defaultValue) {
        this.settingsKey = settingsKey;
        this.defaultValue = defaultValue;
    }

    /**
     * Returns the current project configuration value for this setting.
     *
     * @return the current project configuration value for this setting
     */
    public T get() {
        return Config4J.getProjectConfiguration().get(settingsKey, defaultValue);
    }
}
