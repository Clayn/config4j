package de.clayntech.config4j.impl.io;

import de.clayntech.config4j.Configuration;
import de.clayntech.config4j.io.ConfigurationWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class SimpleConfigurationWriter implements ConfigurationWriter {
    @Override
    public void store(OutputStream out, Configuration config) throws IOException {
        Properties prop = new Properties();
        for (String key : config.getConfigurations()) {
            prop.setProperty(key, config.get(key));
        }
        prop.store(out, "");
    }
}
