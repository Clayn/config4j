package de.clayntech.config4j.impl.key;

import de.clayntech.config4j.Key;
import de.clayntech.config4j.ValueParsingException;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Key implementation to access {@link URL} values.
 *
 * @author Clayn
 * @since 0.1
 */
public class URLKey extends Key<URL> {

    /**
     * Creates a new key instance with the given identification key
     *
     * @param key the key for identification
     */
    public URLKey(String key) {
        super(key);
    }

    @Override
    public URL fromString(String str) throws ValueParsingException {
        try {
            return str.trim().isEmpty() ? null : new URL(str);
        } catch (MalformedURLException e) {
            throw new ValueParsingException("Could not parse url '" + str + "'", e);
        }
    }

    @Override
    public String toString(URL val) throws ValueParsingException {
        return val == null ? "" : val.toString();
    }
}
