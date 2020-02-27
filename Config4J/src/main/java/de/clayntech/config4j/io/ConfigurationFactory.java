package de.clayntech.config4j.io;

/**
 * A configuration factory provides a {@link ConfigurationReader reader} and a {@link ConfigurationWriter writer}
 * to load and store a configuration in a specific format. The configuration should only be loaded by the same factory
 * that stored it.
 *
 * @author Clayn
 * @since 0.1
 */
public interface ConfigurationFactory {
    /**
     * Returns a writer to store a configuration
     *
     * @return a writer to store a configuration
     */
    ConfigurationWriter getWriter();

    /**
     * Returns a reader to load a configuration
     *
     * @return a reader to load a configuration
     */
    ConfigurationReader getReader();
}
