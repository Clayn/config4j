package de.clayntech.config4j.impl.key;

import de.clayntech.config4j.Key;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class KeyFactory {
    private static final Map<Class<?>, Function<String, Key<?>>> KEY_CLASSES = new HashMap<>();

    static {
        KEY_CLASSES.put(String.class, Key::createAccessKey);
        KEY_CLASSES.put(int.class, IntKey::new);
        KEY_CLASSES.put(Integer.class, IntKey::new);
        KEY_CLASSES.put(boolean.class, BooleanKey::new);
        KEY_CLASSES.put(Boolean.class, BooleanKey::new);
        KEY_CLASSES.put(double.class, DoubleKey::new);
        KEY_CLASSES.put(Double.class, DoubleKey::new);
        KEY_CLASSES.put(File.class, FileKey::new);
        KEY_CLASSES.put(URL.class, URLKey::new);
    }

    /**
     * Creates a new key for the given identification and type. The possible types are the types which have
     * implementations directly in the library e.g. {@link IntKey} for {@link int} or {@link Integer}.
     *
     * @param key  the identification for the key
     * @param type the type the key should be for
     * @param <T>  the type the should be for
     * @return a new key that can access values stored with the given key and parse them to the required type.
     */
    @SuppressWarnings("unchecked")
    public static <T> Key<T> createKey(String key, Class<T> type) {
        if (!KEY_CLASSES.containsKey(type)) {
            throw new IllegalArgumentException("No registered key class found for type '" + type.getName() + "'");
        }
        return (Key<T>) KEY_CLASSES.get(type).apply(key);
    }
}
