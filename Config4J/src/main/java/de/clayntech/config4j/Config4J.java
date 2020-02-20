package de.clayntech.config4j;


import de.clayntech.config4j.impl.SimpleConfiguration;
import de.clayntech.config4j.util.Config4JFileParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    /**
     * Tries to load a default configuration from some default locations. This method should be called before
     * loading the actual configuration to overwrite the default values. The loading is done using the
     * {@link Config4JFileParser} class.
     */
    public static void initDefaultConfiguration() {
        String source = "/application.c4j";
        LOG.debug("Searching for default configuration at: {}", source);
        URL u = Config4J.class.getResource(source);
        InputStream in = null;
        if (u != null) {
            in = Config4J.class.getResourceAsStream(source);
        } else {
            LOG.debug("No such file found");
            source = new File(System.getProperty("user.dir"), "application.c4j").getAbsolutePath();
            LOG.debug("Searching for default configuration at: {}", source);
            if (Files.exists(Paths.get(source))) {
                try {
                    in = Files.newInputStream(Paths.get(source));
                } catch (IOException e) {
                    LOG.error("Failed to open the default configuration file", e);
                }
            } else {
                LOG.debug("No such file found. Skipping initialization of the default configuration");
            }
        }
        if (in != null) {
            try (InputStream input = in) {
                Configuration conf = Config4JFileParser.loadConfiguration(input);
                importDefaultConfiguration(conf);
                LOG.debug("Imported the default configuration from {}", source);
            } catch (IOException e) {
                LOG.error("Failed to read the default configuration file", e);
            }
        }
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
