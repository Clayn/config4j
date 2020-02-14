package net.bplaced.clayn.impl.util;

public interface JsonConverter {
    String toJson(Object obj);

    <T> T fromJson(String json, Class<T> type);
}
