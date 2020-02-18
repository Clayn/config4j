package de.clayntech.config4j.impl.io;

import de.clayntech.config4j.Configuration;
import de.clayntech.config4j.impl.JsonConfiguration;
import de.clayntech.config4j.impl.util.JsonConverter;
import de.clayntech.config4j.io.ConfigurationReader;

import java.io.IOException;
import java.io.InputStream;

public class JsonConfigurationReader implements ConfigurationReader {
    private final JsonConverter converter;


    public JsonConfigurationReader(JsonConverter converter) {
        this.converter = converter;
    }


    @Override
    public Configuration load(InputStream in) throws IOException {
        StringBuilder jsonBuilder = new StringBuilder();
        String json = jsonBuilder.toString();

        return converter.fromJson(json, JsonConfiguration.class);
    }
}
