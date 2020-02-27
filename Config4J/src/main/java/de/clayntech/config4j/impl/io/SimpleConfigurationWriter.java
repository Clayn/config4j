package de.clayntech.config4j.impl.io;

import de.clayntech.config4j.Config4J;
import de.clayntech.config4j.Configuration;
import de.clayntech.config4j.io.ConfigurationWriter;
import de.clayntech.config4j.io.NotStorableException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Properties;

public class SimpleConfigurationWriter implements ConfigurationWriter {
    @Override
    public void store(OutputStream out, Configuration config) throws IOException {
        Objects.requireNonNull(out);
        Objects.requireNonNull(config);
        if (!Config4J.isStorable(config)) {
            throw new NotStorableException();
        }
        Properties prop = new Properties();
        for (String key : config.getConfigurations()) {
            prop.setProperty(key, config.get(key));
        }
        prop.store(out, "");
    }
}
