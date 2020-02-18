package de.clayntech.config4j.impl.io;

import de.clayntech.config4j.impl.util.GsonConverter;
import de.clayntech.config4j.impl.util.JsonConverter;
import de.clayntech.config4j.io.ConfigurationFactory;
import de.clayntech.config4j.io.ConfigurationReader;
import de.clayntech.config4j.io.ConfigurationWriter;

public class JsonConfigurationFactory implements ConfigurationFactory {

    private final JsonConverter converter;

    public JsonConfigurationFactory(JsonConverter converter) {
        this.converter = converter;
    }

    @Override
    public ConfigurationWriter getWriter() {
        return new JsonConfigurationWriter(converter == null ? GsonConverter.newInstance() : converter);
    }

    @Override
    public ConfigurationReader getReader() {
        return new JsonConfigurationReader(converter == null ? GsonConverter.newInstance() : converter);
    }
}
