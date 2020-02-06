package net.bplaced.clayn.impl.config4j.event;

public interface ConfigurationListener {
    void configurationChanged(ConfigurationChangeEvent evt);
}
