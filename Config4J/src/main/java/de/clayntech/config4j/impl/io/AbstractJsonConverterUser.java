package de.clayntech.config4j.impl.io;

import de.clayntech.config4j.impl.util.JsonConverter;

import java.util.Objects;

/**
 * Basic class for all others that require a {@link JsonConverter} upon initializing.
 *
 * @author Clayn
 * @since 0.1
 */
public abstract class AbstractJsonConverterUser {
    private final JsonConverter converter;

    public AbstractJsonConverterUser(JsonConverter converter) {
        this.converter = Objects.requireNonNull(converter, getClass().getSimpleName() + " needs a JsonConverter");
    }

    protected final JsonConverter getConverter() {
        return converter;
    }
}
