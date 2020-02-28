package de.clayntech.config4j.impl.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * A {@link JsonConverter} implementation using the Gson library.
 * This converter can be used if the application is already using the Gson library.
 * The dependency will not be included in the config4j project.
 */
public class GsonConverter implements JsonConverter {

    private final Gson gson;

    public GsonConverter(Gson gson) {
        this.gson = gson;
    }

    public GsonConverter() {
        this(new GsonBuilder().setPrettyPrinting().create());
    }

    public static JsonConverter newInstance() {
        return new GsonConverter();
    }

    @Override
    public String toJson(Object obj) {
        return gson.toJson(obj);
    }

    @Override
    public <T> T fromJson(String json, Class<T> type) {
        return gson.fromJson(json, type);
    }
}
