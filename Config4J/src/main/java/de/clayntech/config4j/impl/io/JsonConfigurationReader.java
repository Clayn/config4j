package de.clayntech.config4j.impl.io;

import de.clayntech.config4j.ProfiledConfiguration;
import de.clayntech.config4j.impl.SimpleProfiledConfiguration;
import de.clayntech.config4j.impl.util.JsonConverter;
import de.clayntech.config4j.io.ConfigurationReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * Reads a configuration from a stream that is formatted as json and uses a {@link JsonConverter converter} for conversion.
 *
 * @author Clayn
 * @since 0.1
 */
public class JsonConfigurationReader extends AbstractJsonConverterUser implements ConfigurationReader {

    public JsonConfigurationReader(JsonConverter converter) {
        super(converter);
    }


    @Override
    public ProfiledConfiguration load(InputStream in) throws IOException {
        Objects.requireNonNull(in);
        StringBuilder jsonBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line).append("\n");
            }
        }
        String json = jsonBuilder.toString();
        return getConverter().fromJson(json, SimpleProfiledConfiguration.class);
    }
}
