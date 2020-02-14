package net.bplaced.clayn.impl.util;

import com.google.gson.Gson;

public class GsonConverter implements JsonConverter {

    private final Gson gson;

    public GsonConverter(Gson gson) {
        this.gson = gson;
    }

    public GsonConverter() {
        this(new Gson());
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
