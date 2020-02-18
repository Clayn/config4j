package de.clayntech.config4j.impl.io;

import de.clayntech.config4j.io.ConfigurationFactory;
import de.clayntech.config4j.io.ConfigurationReader;
import de.clayntech.config4j.io.ConfigurationWriter;

public class SimpleConfigurationFactory implements ConfigurationFactory {
    @Override
    public ConfigurationWriter getWriter() {
        return new SimpleConfigurationWriter();
    }

    @Override
    public ConfigurationReader getReader() {
        return new SimpleConfigurationReader();
    }
}
