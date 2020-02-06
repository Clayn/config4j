package net.bplaced.clayn.impl.config4j;


import net.bplaced.clayn.impl.config4j.impl.SimpleConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class Config4J {
    private static final Logger LOG= LoggerFactory.getLogger(Config4J.class);
    private static ConfigurationProvider provider=null;
    private static final Configuration DUMMY_CONFIGURATION=new SimpleConfiguration();

    private Config4J() {

    }

    public static void setProvider(ConfigurationProvider provider) {
        Config4J.provider = provider;
    }

    /**
     * Returns the configuration from the given provider.
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
