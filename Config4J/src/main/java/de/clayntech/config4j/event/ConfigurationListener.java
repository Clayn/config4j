package de.clayntech.config4j.event;

public interface ConfigurationListener {
    /**
     * Called when some value gets changed in a configuration.
     *
     * @param evt the event that holds the information about the change
     */
    void configurationChanged(ConfigurationChangeEvent evt);
}
