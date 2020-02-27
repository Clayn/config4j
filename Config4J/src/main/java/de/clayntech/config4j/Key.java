package de.clayntech.config4j;

import de.clayntech.config4j.util.Converter;

import java.util.Objects;

/**
 * The key class is responsible for identifying values in a configuration and parse them from and to a specific type.
 * While you can use only String objects for the keys, using this class gives you configuration values
 * of the desired type and also can do some checks (e.g. if the value is of the expected type).
 *
 * @param <T> the type of the configuration value
 * @author Clayn
 * @since 0.1
 */
public abstract class Key<T> implements Converter<T, String> {

    private final String key;

    /**
     * Creates a new key instance with the given identification key
     * @param key the key for identification
     */
    public Key(String key) {
        this.key = Objects.requireNonNull(key, "The identification key must not be 'null'");
    }

    /**
     * Returns a basic key to simple access or store values. This is more a convenient method
     * as direct access using a key name as {@link String} is supported.
     *
     * @param key the name for the key
     * @return a new key to access some values.
     */
    public static Key<String> createAccessKey(String key) {
        return new BasicKey(key);
    }

    /**
     * Parses a value from the given string if possible.
     *
     * @param str the value to parse
     * @return the value parsed from the string
     */
    public abstract T fromString(String str) throws ValueParsingException;

    /**
     * Parses the given value to a string representation. That parsed string must be parsable back to the value.
     *
     * @param val the value to parse
     * @return the string representation
     * @throws ValueParsingException if an exception happens during the parsing
     */
    public abstract String toString(T val) throws ValueParsingException;

    /**
     * {@inheritDoc}
     *
     * @param src {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public final String convertForward(T src) {
        return toString(src);
    }

    /**
     * {@inheritDoc}
     *
     * @param src {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public final T convertBackward(String src) {
        return fromString(src);
    }

    /**
     * Returns the identifier for this key class.
     *
     * @return the key identifier
     */
    public String getKey() {
        return key;
    }

    private static final class BasicKey extends Key<String> {

        public BasicKey(String key) {
            super(key);
        }

        @Override
        public String toString(String src) {
            return src;
        }

        @Override
        public String fromString(String src) {
            return src;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;
        Key<?> key1 = (Key<?>) o;
        return key.equals(key1.key);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
