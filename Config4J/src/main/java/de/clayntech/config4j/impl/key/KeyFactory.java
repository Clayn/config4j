package de.clayntech.config4j.impl.key;

import de.clayntech.config4j.Key;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class KeyFactory {
    private static final Map<Class<?>, Function<String, Key<?>>> KEY_CLASSES = new HashMap<>();

    static {
        KEY_CLASSES.put(String.class, Key::createBasicKey);
        KEY_CLASSES.put(int.class, IntKey::new);
        KEY_CLASSES.put(Integer.class, IntKey::new);
        KEY_CLASSES.put(boolean.class, BooleanKey::new);
        KEY_CLASSES.put(Boolean.class, BooleanKey::new);
        KEY_CLASSES.put(double.class, DoubleKey::new);
        KEY_CLASSES.put(Double.class, DoubleKey::new);
        KEY_CLASSES.put(File.class, FileKey::new);
    }


    public static final <T> Key<T> createKey(String key, Class<T> type) {
        if (!KEY_CLASSES.containsKey(type)) {
            throw new IllegalArgumentException();
        }
        return (Key<T>) KEY_CLASSES.get(type).apply(key);
    }
}