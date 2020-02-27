package de.clayntech.config4j.impl.io;

import de.clayntech.config4j.impl.util.GsonConverter;
import de.clayntech.config4j.impl.util.JsonConverter;
import de.clayntech.config4j.io.ConfigurationFactory;
import de.clayntech.config4j.io.ConfigurationReader;
import de.clayntech.config4j.io.ConfigurationWriter;

/**
 * Configuration factory that stores and loads profiled configurations in a json format.
 * To convert the configuration from and to json, a {@link JsonConverter converter} is required.
 * If no such converter was set, an instance of {@link GsonConverter} will be used. That may cause errors
 * as the dependency is not added to the library and must be provided. That way you can either use a different
 * converter or your used Gson version.
 *
 * @author Clayn
 * @since 0.1
 */
public class JsonConfigurationFactory implements ConfigurationFactory {

    private final JsonConverter converter;

    /**
     * Creates a new JsonConfigurationFactory with the given converter to use.
     *
     * @param converter the converter to use.
     */
    public JsonConfigurationFactory(JsonConverter converter) {
        this.converter = converter;
    }

    public JsonConfigurationFactory() {
        this(null);
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
