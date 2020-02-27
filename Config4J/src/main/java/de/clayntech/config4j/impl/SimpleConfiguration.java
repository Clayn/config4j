package de.clayntech.config4j.impl;

import de.clayntech.config4j.ConfigurationBase;
import de.clayntech.config4j.event.ConfigurationChangeEvent;

import java.io.OutputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

/**
 * Configuration implementation that stores the values in a simple properties file.
 * The format is the same as used by {@link Properties} using {@link Properties#store(OutputStream, String)}.
 *
 * @author Clayn
 * @since 0.1
 */
public class SimpleConfiguration extends ConfigurationBase {

    private final Properties properties;

    public SimpleConfiguration() {
        this(new Properties());
    }

    private SimpleConfiguration(Properties properties) {
        this.properties = properties;
    }

    /**
     * {@inheritDoc}
     *
     * @param key the key to get the value for
     * @param def the default value if no value was found
     * @return {@inheritDoc}
     */
    @Override
    public String get(String key, String def) {
        return properties.getProperty(key, def);
    }

    /**
     * {@inheritDoc}
     *
     * @param key the key for the value
     * @param val the value to store
     */
    @Override
    public void set(String key, String val) {
        Objects.requireNonNull(val);
        Objects.requireNonNull(key);
        String old = properties.getProperty(key);
        properties.setProperty(key, val);
        boolean fire = false;
        if (!val.equals(old)) {
            fire = true;
        }
        if (fire) {
            ConfigurationChangeEvent evt = new ConfigurationChangeEvent(key, old, val);
            getListeners().forEach((lis) -> lis.configurationChanged(evt));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public Set<String> getConfigurations() {
        return properties.stringPropertyNames();
    }
}
