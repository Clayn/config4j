package net.bplaced.clayn.impl.config4j.impl.io;

import net.bplaced.clayn.impl.config4j.io.ConfigurationFactory;
import net.bplaced.clayn.impl.config4j.io.ConfigurationReader;
import net.bplaced.clayn.impl.config4j.io.ConfigurationWriter;

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
