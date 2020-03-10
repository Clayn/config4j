package de.clayntech.config4j.rt;

import java.util.HashMap;
import java.util.Map;

/**
 * Object holder class for runtime objects that can't or shouldn't be serialized.
 * Keys for the objects can be reserved for a specific type to ensure that you always have a matching
 * object.
 */
public class Runtime4J {
    private static final Runtime4J INSTANCE = new Runtime4J();

    private final Map<String, Object> runtimeValues = new HashMap<>();
    private final Map<String, Class<?>> reservedTypes = new HashMap<>();

    private Runtime4J() {
    }

    public static Runtime4J getRuntime() {
        return INSTANCE;
    }

    /**
     * Returns the object stored with the given key.
     *
     * @param key the key to get
     * @param <T> the type of the object to be casted to
     * @return the stored object or {@code null} if no object was set.
     * @throws ClassCastException if the object could not be casted to {@code T}
     * @see #setObject(String, Object)
     * @see #setObject(String, Object, boolean)
     */
    public <T> T getObject(String key) {
        return (T) runtimeValues.get(key);
    }

    /**
     * Reserves a specific type for the given key. Already reserved keys can't be changed.
     * If there exists an object for the key which does not match the type it will be removed.
     *
     * @param key  the key to reserve a type for
     * @param type the type to reserve
     */
    public void reserveType(String key, Class<?> type) {
        if (reservedTypes.containsKey(key)) {
            throw new IllegalArgumentException("There is alreay a type reserved for '" + key + "'");
        }
        reservedTypes.put(key, type);
        if (runtimeValues.containsKey(key)) {
            if (!checkTypes(type, runtimeValues.get(key))) {
                runtimeValues.remove(key);
            }
        }
    }

    private boolean checkTypes(Class<?> type, Object obj) {
        if (type == null) {
            return true;
        }
        return type.isAssignableFrom(obj.getClass());
    }

    /**
     * Stores the object using the given key. The type of the object can be reserved as well
     *
     * @param key     the key to use
     * @param obj     the object to store
     * @param reserve {@code true} if the type of the object should be reserved for the key
     * @throws IllegalArgumentException if a type is reserved that does not match the objects type
     * @see #getObject(String)
     * @see #setObject(String, Object)
     * @see #reserveType(String, Class)
     */
    public void setObject(String key, Object obj, boolean reserve) {
        if (checkTypes(reservedTypes.get(key), obj)) {
            runtimeValues.put(key, obj);
            if (reserve && !reservedTypes.containsKey(key)) {
                reservedTypes.put(key, obj.getClass());
            }
        } else {
            throw new IllegalArgumentException("Type " + obj.getClass().getName() + " does not match with reserved type " + reservedTypes.get(key).getName());
        }

    }

    /**
     * Stores the object using the given key.
     *
     * @param key the key to use
     * @param obj the object to store
     * @throws IllegalArgumentException if a type is reserved that does not match the objects type
     * @see #getObject(String)
     * @see #setObject(String, Object, boolean)
     */
    public void setObject(String key, Object obj) {
        setObject(key, obj, false);
    }
}
