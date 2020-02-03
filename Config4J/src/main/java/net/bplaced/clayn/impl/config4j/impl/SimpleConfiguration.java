package net.bplaced.clayn.impl.config4j.impl;

import net.bplaced.clayn.impl.config4j.Configuration;

import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class SimpleConfiguration implements Configuration {

    private final Properties properties;

    public SimpleConfiguration() {
        this(new Properties());
    }

    private SimpleConfiguration(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String get(String key, String def) {
        return properties.getProperty(key, def);
    }

    @Override
    public void set(String key, String val) {
        properties.setProperty(key, val);
    }

    private String extractProfileName(String str) {
        int profNameEnd = str.indexOf(".");
        return str.substring(1, profNameEnd);
    }

    @Override
    public Set<String> getProfiles() {
        return properties.stringPropertyNames()
                .stream()
                .filter((str) -> str.startsWith("*"))
                .map(this::extractProfileName)
                .distinct()
                .collect(Collectors.toSet());
    }

    @Override
    public Configuration getProfile(String profile) {
        if (!getProfiles().contains(profile)) {
            throw new IllegalArgumentException("No profile with name '" + profile + "' found");
        }
        Properties profProp = new Properties();
        String keyStart = "*" + profile;
        properties.stringPropertyNames()
                .stream()
                .filter((str) -> str.startsWith(keyStart))
                .forEach((str) -> {
                    String newKey = str.substring(str.indexOf(keyStart) + keyStart.length() + 1);
                    profProp.setProperty(newKey, properties.getProperty(str));
                });
        return new SimpleConfiguration(profProp);
    }
}
