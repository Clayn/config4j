package de.clayntech.config4j.impl;

import de.clayntech.config4j.ConfigurationBase;
import de.clayntech.config4j.ProfiledConfiguration;
import de.clayntech.config4j.event.ConfigurationChangeEvent;

import java.util.*;

public class SimpleProfiledConfiguration extends ConfigurationBase implements ProfiledConfiguration {
    private final String name;
    private final transient SimpleProfiledConfiguration parent;
    private final Map<String, SimpleProfiledConfiguration> profiles = new HashMap<>();
    private final Properties properties = new Properties();


    SimpleProfiledConfiguration(String name, SimpleProfiledConfiguration parent) {
        this.name = name;
        this.parent = parent;
    }

    Map<String, SimpleProfiledConfiguration> getProfiles0() {
        return profiles;
    }

    public SimpleProfiledConfiguration() {
        this(TOP_LEVEL_PROFILE_NAME, null);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public ProfiledConfiguration getParent() {
        return parent;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public Set<ProfiledConfiguration> getProfiles() {
        return new HashSet<>(getProfiles0().values());
    }

    /**
     * {@inheritDoc}
     *
     * @param profile the name for the profile
     * @return {@inheritDoc}
     */
    @Override
    public ProfiledConfiguration getProfile(String profile) {
        return getProfiles0().get(profile);
    }

    /**
     * {@inheritDoc}
     *
     * @param profile the name for the profile
     * @return {@inheritDoc}
     */
    @Override
    public ProfiledConfiguration createProfile(String profile) {
        if (!getProfiles0().containsKey(profile)) {
            getProfiles0().put(profile, new SimpleProfiledConfiguration(profile, this));
        }
        return getProfile(profile);
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
        properties.put(key, val);
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
