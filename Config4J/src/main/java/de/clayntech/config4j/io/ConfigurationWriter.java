package de.clayntech.config4j.io;

import de.clayntech.config4j.Configuration;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Writer class to store a configuration in a specific format to a data sink.
 *
 * @author Clayn
 * @since 0.1
 */
public interface ConfigurationWriter {
    /**
     * Stores the configuration in the implementations format to the outputstream.
     * The writer may transform the configuration if it is needed but must never change the original
     * configuration
     *
     * @param out    the destination for the configuration
     * @param config the configuration to store
     * @throws IOException          if an I/O Exception occurs
     * @throws NotStorableException if the configuration was marked to not be saved
     */
    void store(OutputStream out, Configuration config) throws IOException, NotStorableException;
}
