package de.clayntech.config4j.util;

import de.clayntech.config4j.Configuration;

public interface Config4JMapper<T> extends Converter<T, Configuration> {

    T fromConfiguration(Configuration configuration);

    Configuration toConfiguration(T object);

    @Override
    default T convertBackward(Configuration configuration) {
        return fromConfiguration(configuration);
    }

    @Override
    default Configuration convertForward(T object) {
        return toConfiguration(object);
    }
}
