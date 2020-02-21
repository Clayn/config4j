package de.clayntech.config4j.io;

import de.clayntech.config4j.Configuration;
import de.clayntech.config4j.conf.Config4JSetting;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public interface ConfigurationReader {
    Configuration load(InputStream in) throws IOException;

    /**
     * Loads the configuration from the given source using the {@link #load(InputStream)} method.
     * If the source does not exist an exception may be thrown if {@link Config4JSetting#THROW_ERROR_ON_SOURCE_MISSING}
     * is set to {@code true} otherwise {@code null} gets returned.
     *
     * @param src the source to load the configuration from
     * @return the loaded configuration or {@code null} if the source does not exist and {@link Config4JSetting#THROW_ERROR_ON_SOURCE_MISSING}
     * is set to {@code true}
     * @throws SourceMissingException if {@link Config4JSetting#THROW_ERROR_ON_SOURCE_MISSING} is set to {@code true}
     *                                and the source does not exist.
     * @throws IOException            if an I/O Exception occurs.
     */
    default Configuration load(Source src) throws SourceMissingException, IOException {
        Objects.requireNonNull(src);
        if (src.exists()) {
            try (InputStream in = src.open()) {
                return load(in);
            }
        } else {
            if (Config4JSetting.THROW_ERROR_ON_SOURCE_MISSING.get()) {
                throw new SourceMissingException("Missing source " + src.getSource());
            } else {
                LoggerFactory.getLogger(getClass())
                        .error("Source '{}' is missing", src.getSource());
                return null;
            }
        }
    }
}
