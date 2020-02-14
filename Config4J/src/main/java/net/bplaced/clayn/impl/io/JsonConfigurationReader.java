package net.bplaced.clayn.impl.io;

import net.bplaced.clayn.config4j.Configuration;
import net.bplaced.clayn.config4j.io.ConfigurationReader;
import net.bplaced.clayn.impl.JsonConfiguration;
import net.bplaced.clayn.impl.util.JsonConverter;

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
