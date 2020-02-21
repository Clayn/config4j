package de.clayntech.config4j.impl.io;

import de.clayntech.config4j.Configuration;
import de.clayntech.config4j.ProfiledConfiguration;
import de.clayntech.config4j.impl.SimpleProfiledConfiguration;
import de.clayntech.config4j.impl.util.JsonConverter;
import de.clayntech.config4j.io.ConfigurationReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonConfigurationReader implements ConfigurationReader {
    private final JsonConverter converter;


    public JsonConfigurationReader(JsonConverter converter) {
        this.converter = converter;
    }


    @Override
    public ProfiledConfiguration load(InputStream in) throws IOException {
        StringBuilder jsonBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line).append("\n");
            }
        }
        String json = jsonBuilder.toString();
        return converter.fromJson(json, SimpleProfiledConfiguration.class);
    }
}
