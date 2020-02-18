package de.clayntech.config4j.event;

public interface ConfigurationListener {
    void configurationChanged(ConfigurationChangeEvent evt);
}
