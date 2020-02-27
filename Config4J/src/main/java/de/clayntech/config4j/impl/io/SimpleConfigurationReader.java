package de.clayntech.config4j.impl.io;

import de.clayntech.config4j.Configuration;
import de.clayntech.config4j.impl.SimpleConfiguration;
import de.clayntech.config4j.io.ConfigurationReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class SimpleConfigurationReader implements ConfigurationReader {
    @Override
    public Configuration load(InputStream in) throws IOException {
        Objects.requireNonNull(in);
        Properties prop = new Properties();
        prop.load(in);
        SimpleConfiguration config = new SimpleConfiguration();
        for (String key : prop.stringPropertyNames()) {
            config.set(key, prop.getProperty(key));
        }
        return config;
    }
}
