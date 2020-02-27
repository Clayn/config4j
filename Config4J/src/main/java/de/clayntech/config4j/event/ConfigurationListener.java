package de.clayntech.config4j.event;

/**
 * Listener for configuration change events.
 *
 * @author Clayn
 * @since 0.1
 */
public interface ConfigurationListener {
    /**
     * Called when some value gets changed in a configuration.
     *
     * @param evt the event that holds the information about the change
     */
    void configurationChanged(ConfigurationChangeEvent evt);
}
