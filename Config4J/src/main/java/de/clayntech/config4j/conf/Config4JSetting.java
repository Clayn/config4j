package de.clayntech.config4j.conf;

import de.clayntech.config4j.Config4J;
import de.clayntech.config4j.ConfigurationBase;
import de.clayntech.config4j.Key;
import de.clayntech.config4j.impl.key.KeyFactory;

/**
 * Manages the setting used by components of the Config4J project. A directly access to the project configuration value
 * can be done with this class.
 *
 * @param <T> the type of the setting
 * @author Clayn
 * @since 0.1
 */
public final class Config4JSetting<T> {
    /**
     * When trying to load a configuration from a {@link de.clayntech.config4j.io.Source} object and it does not exists,
     * an exception gets thrown if this setting is {@code true}.<br>
     * <b>Default: </b>{@code true}
     */
    public static final Config4JSetting<Boolean> THROW_ERROR_ON_SOURCE_MISSING = new Config4JSetting<>(KeyFactory.createKey("config4j.setting.source.missing.throw", boolean.class), true);
    /**
     * When set to {@code true} the loaded default configuration will be printed using a {@link org.slf4j.Logger} with the debug level.
     * <br>
     * <b>Default: </b>{@code false}
     */
    public static final Config4JSetting<Boolean> DEBUG_LOADED_DEFAULT_CONFIGURATION = new Config4JSetting<>(KeyFactory.createKey("config4j.setting.debug.default", boolean.class), false);
    /**
     * When set to {@code true} the method {@link ConfigurationBase#getListeners()} will return an unmodifieable list of the
     * listeners to prevent changes to the list.<br>
     * <b>Default: </b>{@code true}
     */
    public static final Config4JSetting<Boolean> CONFIGURATION_BASE_UNMODIFIABLE_LIST = new Config4JSetting<>(KeyFactory.createKey("config4j.setting.base.unmodifiable", boolean.class), true);

    public static final Config4JSetting<Boolean> CREATE_DEFAULT_PROVIDER = new Config4JSetting<>(KeyFactory.createKey("config4j.setting.base.unmodifiable", boolean.class), true);


    private final Key<T> settingsKey;
    private final T defaultValue;

    public Config4JSetting(Key<T> settingsKey, T defaultValue) {
        this.settingsKey = settingsKey;
        this.defaultValue = defaultValue;
    }

    /**
     * Returns the current project configuration value for this setting. If no value was set, the default
     * value gets returned.
     *
     * @return the current project configuration value for this setting or the default value if no
     * configuration was set.
     */
    public T get() {
        return Config4J.getProjectConfiguration().get(settingsKey, defaultValue);
    }

    /**
     * Sets the project configuration value for this setting
     *
     * @param val the new value for this setting
     */
    public void set(T val) {
        Config4J.getProjectConfiguration().set(settingsKey, val);
    }
}
