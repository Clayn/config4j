package net.bplaced.clayn.impl;

import net.bplaced.clayn.config4j.ConfigurationBase;
import net.bplaced.clayn.config4j.ProfiledConfiguration;

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
        properties.setProperty(key, val);
    }

    @Override
    public Set<String> getConfigurations() {
        return properties.stringPropertyNames();
    }
}
