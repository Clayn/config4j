package de.clayntech.config4j.impl.util;

public interface JsonConverter {
    String toJson(Object obj);

    <T> T fromJson(String json, Class<T> type);
}
