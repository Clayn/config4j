package de.clayntech.config4j.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Defines a source of different possible types that has a specific location. The source can check if
 * it exists and if so returns a stream to read from that source.
 */
public final class Source {
    private final String source;
    private final SourceType type;

    public Source(String source, SourceType type) {
        this.source = source;
        this.type = type;
    }

    public SourceType getType() {
        return type;
    }

    public String getSource() {
        return source;
    }

    /**
     * Checks if the source exists or not. In case of a web resource this method may block until
     * it can be resolved or the connection times out. A connection timeout of 2000 will be set.
     *
     * @return {@code true} if the source exists and can be loaded, {@code false} otherwise
     */
    public boolean exists() {
        switch (type) {
            case FILE:
                return Files.exists(Paths.get(source)) && Files.isRegularFile(Paths.get(source));
            case RESOURCE:
                return getClass().getResource(source) != null;
            case URL:
                try {
                    URLConnection con = new URL(source).openConnection();
                    con.setConnectTimeout(2000);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            default:
                return false;
        }
    }

    /**
     * Opens a new input stream to load data from this source.
     *
     * @return the input stream to read from the source or {@code null} if it does not exist
     * @throws IOException if an I/O Exception occurs
     */
    public InputStream open() throws IOException {
        if (!exists()) {
            return null;
        }
        switch (type) {
            case FILE:
                return Files.newInputStream(Paths.get(source));
            case URL:
                return new URL(source).openStream();
            case RESOURCE:
                return getClass().getResourceAsStream(source);
            default:
                return null;
        }
    }

    public enum SourceType {
        /**
         * External file located somewhere in the filesystem.
         */
        FILE,
        /**
         * Internal resource.
         *
         * @see Class#getResource(String)
         */
        RESOURCE,
        /**
         * External resource identified with an URL. Can be a web resource
         */
        URL
    }
}
