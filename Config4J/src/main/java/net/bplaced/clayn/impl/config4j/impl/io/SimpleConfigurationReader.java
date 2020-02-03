package net.bplaced.clayn.impl.config4j.impl.io;

import net.bplaced.clayn.impl.config4j.Configuration;
import net.bplaced.clayn.impl.config4j.impl.SimpleConfiguration;
import net.bplaced.clayn.impl.config4j.io.ConfigurationReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SimpleConfigurationReader implements ConfigurationReader {
    @Override
    public Configuration load(InputStream in) throws IOException {
        Properties prop = new Properties();
        prop.load(in);
        SimpleConfiguration config = new SimpleConfiguration();
        for (String key : prop.stringPropertyNames()) {
            config.set(key, prop.getProperty(key));
        }
        return config;
    }
}
