package net.bplaced.clayn.config4j;


import net.bplaced.clayn.impl.SimpleConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Main class of the config4j project to provide application wide access to the configuration.
 * If your use case requires a more protected access you don't have to use this class at all.
 */
public final class Config4J {
    private static final Logger LOG = LoggerFactory.getLogger(Config4J.class);
    private static ConfigurationProvider provider = null;
    private static final Configuration DUMMY_CONFIGURATION = new SimpleConfiguration();

    private Config4J() {

    }

    public static void importDefaultConfiguration(Configuration def) {
        if (provider == null) {
            return;
        }
        Configuration used = getConfiguration();
        merge(used, def);
        saveConfiguration();
    }

    private static void merge(Configuration base, Configuration imp) {
        for (String key : imp.getConfigurations()) {
            if (!base.getConfigurations().contains(key)) {
                base.set(key, imp.get(key));
            }
        }
    }

    public static void setProvider(ConfigurationProvider provider) {
        Config4J.provider = provider;
    }

    /**
     * Returns the configuration from the given provider.
     *
     * @return the registered providers configuration
     */
    public static Configuration getConfiguration() {
        try {
            if(provider==null) {
                LOG.warn("Configuration was requested with no provider installed");
                return DUMMY_CONFIGURATION;
            }else {
                return provider.loadConfiguration();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves the configuration with the registered provider. The saves will be done with the configuration
     * returned by {@link #getConfiguration()};
     */
    public static void saveConfiguration() {
        if(provider==null) {
            LOG.warn("No provider is installed for saving the configuration");
            return;
        }
        try {
            provider.storeConfiguration();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
