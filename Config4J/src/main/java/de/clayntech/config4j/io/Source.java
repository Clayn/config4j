package de.clayntech.config4j.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Defines a source of different possible types that has a specific location. The source can check if
 * it exists and if so returns a stream to read from that source.
 *
 * @author Clayn
 * @since 0.1
 */
public final class Source {
    private final String source;
    private final SourceType type;

    /**
     * Creates a new source object with the given source location and type.
     * @param source the location of the source
     * @param type the type of the source
     */
    public Source(String source, SourceType type) {
        this.source = Objects.requireNonNull(source);
        this.type = Objects.requireNonNull(type);
    }

    public SourceType getType() {
        return type;
    }

    public String getSource() {
        return source;
    }

    /**
     * Returns a new file type source with the given file as location.
     *
     * @param file the file that is the source
     * @return a new file type source object.
     */
    public static Source newFileSource(String file) {
        return new Source(file, SourceType.FILE);
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

    /**
     * Returns a new file type source with the given file as location.
     *
     * @param file the file that is the source
     * @return a new file type source object.
     */
    public static Source newFileSource(File file) {
        return newFileSource(file.getAbsolutePath());
    }

    /**
     * Returns a new url type source with the given url as location.
     *
     * @param url the url that is the source
     * @return a new url type source object.
     */
    public static Source newURLSource(String url) {
        return new Source(url, SourceType.URL);
    }

    /**
     * Returns a new url type source with the given url as location.
     *
     * @param url the url that is the source
     * @return a new url type source object.
     */
    public static Source newURLSource(URL url) {
        return newURLSource(url.toString());
    }

    /**
     * Returns a new resource type source with the given resource as location.
     *
     * @param resource the resource that is the source
     * @return a new resource type source object.
     * @see Class#getResource(String)
     * @see Class#getResourceAsStream(String)
     */
    public static Source newResourceSource(String resource) {
        return new Source(resource, SourceType.RESOURCE);
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
                    URL url = new URL(source);
                    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                    int responseCode = huc.getResponseCode();
                    return responseCode != HttpURLConnection.HTTP_NOT_FOUND;
                } catch (Exception e) {
                    return false;
                }
            default:
                return false;
        }
    }
}
