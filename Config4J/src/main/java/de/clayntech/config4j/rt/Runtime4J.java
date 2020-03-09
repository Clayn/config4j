package de.clayntech.config4j.rt;

import java.util.HashMap;
import java.util.Map;

public class Runtime4J {
    private static final Runtime4J INSTANCE = new Runtime4J();

    private final Map<String, Object> runtimeValues = new HashMap<>();
    private final Map<String, Class<?>> reservedTypes = new HashMap<>();

    private Runtime4J() {
    }

    public static Runtime4J getRuntime() {
        return INSTANCE;
    }

    public <T> T getObject(String key) {
        return (T) runtimeValues.get(key);
    }

    public void reserveType(String key, Class<?> type) {
        if (reservedTypes.containsKey(key)) {
            throw new IllegalArgumentException("There is alreay a type reserved for '" + key + "'");
        }
        reservedTypes.put(key, type);
    }

    private boolean checkTypes(Class<?> type, Object obj) {
        if (type == null) {
            return true;
        }
        return type.isAssignableFrom(obj.getClass());
    }

    public void setObject(String key, Object obj) {
        if (checkTypes(reservedTypes.get(key), obj)) {
            runtimeValues.put(key, obj);
        } else {
            throw new IllegalArgumentException("Type " + obj.getClass().getName() + " does not match with reserved type " + reservedTypes.get(key).getName());
        }

    }
}
