package net.bplaced.clayn.impl.config4j.impl;

import net.bplaced.clayn.impl.config4j.Configuration;
import net.bplaced.clayn.impl.config4j.ConfigurationBase;
import net.bplaced.clayn.impl.config4j.event.ConfigurationChangeEvent;

import java.io.OutputStream;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Configuration implementation that stores the values in a simple properties file.
 * The format is the same as used by {@link Properties} using {@link Properties#store(OutputStream, String)}
 */
public class SimpleConfiguration extends ConfigurationBase {

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
        String old=properties.getProperty(key);
        properties.setProperty(key, val);
        boolean fire=false;
        if(old!=null&&val==null) {
            fire=true;
        }else if(old!=null&&!old.equals(val)){
            fire=true;
        }else if(val!=null&&!val.equals(old)) {
            fire=true;
        }
        if(fire) {
            ConfigurationChangeEvent evt=new ConfigurationChangeEvent(key,old,val);
            getListeners().stream().forEach((lis)->lis.configurationChanged(evt));
        }
    }

    @Override
    public Set<String> getConfigurations() {
        return properties.stringPropertyNames();
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
