package de.clayntech.config4j.impl;

import de.clayntech.config4j.ConfigurationBase;
import de.clayntech.config4j.ProfiledConfiguration;
import de.clayntech.config4j.event.ConfigurationChangeEvent;

import java.util.*;

public class JsonConfiguration extends ConfigurationBase implements ProfiledConfiguration {
    private final String name;
    private final JsonConfiguration parent;
    private final Map<String, ProfiledConfiguration> profiles = new HashMap<>();
    private final Properties properties = new Properties();

    public JsonConfiguration(String name, JsonConfiguration parent) {
        this.name = name;
        this.parent = null;
    }

    public JsonConfiguration() {
        this(TOP_LEVEL_PROFILE_NAME, null);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ProfiledConfiguration getParent() {
        return parent;
    }

    @Override
    public Set<String> getProfilesNames() {
        return Collections.unmodifiableSet(profiles.keySet());
    }

    @Override
    public Set<ProfiledConfiguration> getProfiles() {
        return new HashSet<>(profiles.values());
    }

    @Override
    public ProfiledConfiguration getProfile(String profile) {
        return profiles.get(profile);
    }

    @Override
    public ProfiledConfiguration createProfile(String profile) {
        if (!profiles.containsKey(profile)) {
            profiles.put(profile, new JsonConfiguration(profile, this));
        }
        return getProfile(profile);
    }

    @Override
    public String get(String key, String def) {
        return properties.getProperty(key, def);
    }

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
            getListeners().stream().forEach((lis) -> lis.configurationChanged(evt));
        }
    }

    @Override
    public Set<String> getConfigurations() {
        return properties.stringPropertyNames();
    }
}
