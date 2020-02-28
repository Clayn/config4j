package de.clayntech.config4j.impl.io;

import de.clayntech.config4j.Config4J;
import de.clayntech.config4j.Configuration;
import de.clayntech.config4j.ProfiledConfiguration;
import de.clayntech.config4j.impl.SimpleProfiledConfiguration;
import de.clayntech.config4j.impl.util.JsonConverter;
import de.clayntech.config4j.io.ConfigurationWriter;
import de.clayntech.config4j.io.NotStorableException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class JsonConfigurationWriter extends AbstractJsonConverterUser implements ConfigurationWriter {
    public JsonConfigurationWriter(JsonConverter converter) {
        super(converter);
    }

    @Override
    public void store(OutputStream out, Configuration config) throws IOException {
        Objects.requireNonNull(out);
        Objects.requireNonNull(config);
        if (!Config4J.isStorable(config)) {
            throw new NotStorableException();
        }
        if (config instanceof ProfiledConfiguration) {
            String json = getConverter().toJson(config);
            out.write(json.getBytes());
            out.flush();
        } else {
            ProfiledConfiguration prof = new SimpleProfiledConfiguration();
            for (String key : config.getConfigurations()) {
                prof.set(key, config.get(key));
            }
            store(out, prof);
        }
    }
}
