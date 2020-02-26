package de.clayntech.config4j.impl.io;

import de.clayntech.config4j.Configuration;
import de.clayntech.config4j.impl.util.JsonConverter;
import de.clayntech.config4j.io.ConfigurationWriter;

import java.io.IOException;
import java.io.OutputStream;

public class JsonConfigurationWriter extends AbstractJsonConverterUser implements ConfigurationWriter {
    public JsonConfigurationWriter(JsonConverter converter) {
        super(converter);
    }

    @Override
    public void store(OutputStream out, Configuration config) throws IOException {
        out.write(getConverter().toJson(config).getBytes());
        out.flush();
    }
}
