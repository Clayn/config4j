package de.clayntech.config4j.impl.io;

import de.clayntech.config4j.Configuration;
import de.clayntech.config4j.impl.util.JsonConverter;
import de.clayntech.config4j.io.ConfigurationWriter;

import java.io.IOException;
import java.io.OutputStream;

public class JsonConfigurationWriter implements ConfigurationWriter {

    private final JsonConverter converter;


    public JsonConfigurationWriter(JsonConverter converter) {
        this.converter = converter;
    }

    @Override
    public void store(OutputStream out, Configuration config) throws IOException {
        out.write(converter.toJson(config).getBytes());
        out.flush();
    }
}
