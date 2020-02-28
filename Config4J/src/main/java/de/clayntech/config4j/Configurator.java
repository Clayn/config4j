package de.clayntech.config4j;

import de.clayntech.config4j.annotation.Configured;
import de.clayntech.config4j.impl.key.KeyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Configurator {
    private static final Logger LOG = LoggerFactory.getLogger(Configurator.class);
    private final Configuration configuration;

    public Configurator(Configuration configuration) {
        this.configuration = Objects.requireNonNull(configuration);
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    private List<Field> findFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        Stream.concat(Arrays.stream(clazz.getFields()), Arrays.stream(clazz.getDeclaredFields()))
                .filter((f) -> f.isAnnotationPresent(Configured.class))
                .forEach(fields::add);
        if (clazz.getSuperclass() != null) {
            fields.addAll(findFields(clazz.getSuperclass()));
        }
        return fields;
    }

    private Object findValue(ProfiledConfiguration conf, Key<?> key) {
        if (conf.getConfigurations().contains(key.getKey())) {
            return conf.get(key);
        } else {
            for (ProfiledConfiguration child : conf.getProfiles()) {
                if (child.getConfigurations().contains(key.getKey())) {
                    return child.get(key); //Search through top level profiles
                }
            }
            for (ProfiledConfiguration child : conf.getProfiles()) {
                Object val = findValue(child, key); //If no value was found, do a deeper inspection
                if (val != null) {
                    return val;
                }
            }
        }
        return null;
    }

    private Object findValue(Key<?> key) {
        if (configuration instanceof ProfiledConfiguration) {
            return findValue((ProfiledConfiguration) configuration, key);
        } else {
            return configuration.get(key);
        }
    }

    private Method findSetter(Class<?> clazz, String name, Class<?> type) {
        Method m = null;
        try {
            m = clazz.getDeclaredMethod(name, type);
        } catch (NoSuchMethodException e) {
            try {
                m = clazz.getMethod(name, type);
            } catch (NoSuchMethodException ex) {
                LOG.debug("No setter method '{}' for type {} was found in class {}", name, type, clazz);
            }
        }
        if (m == null && clazz.getSuperclass() != null) {
            return findSetter(clazz.getSuperclass(), name, type);
        }
        return m;
    }

    public void configure(Object obj) {
        List<Field> fields = findFields(obj.getClass());
        for (Field f : fields) {
            Configured conf = f.getAnnotation(Configured.class);
            if (configuration.getConfigurations().contains(conf.key())) {
                boolean customKey = !conf.keyType().equals(Configured.Auto.class);
                Class<?> type = customKey ? conf.keyType() : f.getType();
                Key<?> key;
                try {
                    key = customKey ? (Key<?>) type.getDeclaredConstructor(String.class).newInstance(conf.key()) : KeyFactory.createKey(conf.key(), type);
                } catch (Exception e) {
                    LOG.warn("Could not create key for class {}", type, e);
                    continue;
                }
                Object val = configuration.get(key);
                if (conf.set().trim().isEmpty()) {
                    boolean access = f.isAccessible();
                    f.setAccessible(true);
                    try {
                        f.set(obj, val);
                    } catch (IllegalAccessException e) {
                        LOG.error("Failed to set value for field {}", f.getName(), e);
                    } finally {
                        f.setAccessible(access);
                    }
                } else {
                    Method m = findSetter(obj.getClass(), conf.set(), type);
                    if (m != null) {
                        try {
                            m.invoke(obj, val);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            LOG.error("Failed to set value of type {} using '{}'", type, conf.set(), e);
                        }
                    } else {
                        LOG.warn("No setter '{}' found for type {}", conf.set(), type);
                    }
                }
            }
        }
    }
}
