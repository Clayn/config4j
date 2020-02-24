package de.clayntech.config4j;


import de.clayntech.config4j.conf.Config4JSetting;
import de.clayntech.config4j.impl.SimpleConfiguration;
import de.clayntech.config4j.io.Source;
import de.clayntech.config4j.util.Config4JFileParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class of the config4j project to provide application wide access to the configuration.
 * If your use case requires a more protected access you don't have to use this class at all.
 */
public final class Config4J {
    private static final Logger LOG = LoggerFactory.getLogger(Config4J.class);
    private static ConfigurationProvider provider = null;
    private static final Configuration DUMMY_CONFIGURATION = new SimpleConfiguration();
    private static final Configuration PROJECT_CONFIGURATION = new SimpleConfiguration();
    private static final List<Source> SOURCE_LIST = new ArrayList<>();

    static {
        SOURCE_LIST.add(new Source("/application.c4j", Source.SourceType.RESOURCE));
        SOURCE_LIST.add(new Source(System.getProperty("user.dir") + "/application.c4j", Source.SourceType.FILE));
    }

    private Config4J() {

    }

    /**
     * Returns the Config4J project configuration. This configuration can be used to configure
     * different behaviours of Config4J. All available settings are in {@link Config4JSetting}.
     * This configuration is not meant to be stored somewhere (but you can if you want to) and may be filled
     * in the application or with your custom configuration.
     *
     * @return the projects configuration.
     */
    public static Configuration getProjectConfiguration() {
        return PROJECT_CONFIGURATION;
    }

    /**
     * Tries to load a default configuration from some default locations. This method should be called before
     * loading the actual configuration to overwrite the default values. The loading is done using the
     * {@link Config4JFileParser} class.
     */
    public static void initDefaultConfiguration() {
        for (Source src : SOURCE_LIST) {
            LOG.debug("Checking for application configuration (type: {}) at: {}", src.getType(), src.getSource());
            if (src.exists()) {
                LOG.debug("Found application configuration");
                try {
                    Configuration conf = Config4JFileParser.loadConfiguration(src.open());
                    if (Config4JSetting.DEBUG_LOADED_DEFAULT_CONFIGURATION.get()) {
                        for (String key : conf.getConfigurations()) {
                            LOG.debug("{} = {}", key, conf.get(key));
                        }
                    }
                    importDefaultConfiguration(conf);
                } catch (IOException e) {
                    LOG.debug("Failed to load the configuration", e);
                }
                break;
            } else {
                LOG.debug("Application configuration does not exist");
            }
        }
    }

    /**
     * @param def
     */
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

    /**
     * Sets the provider for {@link Config4J} that provides the globally used configuration.
     *
     * @param provider the provider for the globally used configuration.
     */
    public static void setProvider(ConfigurationProvider provider) {
        Config4J.provider = provider;
    }

    /**
     * Returns the configuration from the given provider. If no provider was installed a dummy configuration will be returned
     * that can save settings during runtime but will lose them (if not saved otherwise).
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
