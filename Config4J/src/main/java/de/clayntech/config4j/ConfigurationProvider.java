package de.clayntech.config4j;

import de.clayntech.config4j.impl.io.SimpleConfigurationFactory;
import de.clayntech.config4j.io.ConfigurationFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

/**
 * This interface provides loading and storing of a configuration. Because the {@link ConfigurationFactory} is designed
 * for stream based access (mostly files) and you may want some other types (maybe database based configuration)
 * this interface is used by {@link Config4J}.
 *
 * @author Clayn
 * @since 0.1
 */
public interface ConfigurationProvider {


    Configuration loadConfiguration() throws IOException;

    void storeConfiguration() throws IOException;

    /**
     * Creates a new {@link ConfigurationProvider provider} that uses the given file to load and store
     * its configuration. The file will be created upon calling this method if it does not exist yet.
     *
     * @param configFile the file for the configuration
     * @param factory    the factory to save and load the configuration
     * @return a new provider for the given file and factory
     */
    static ConfigurationProvider newFileBasedProvider(File configFile, ConfigurationFactory factory) {
        return new ConfigurationProvider() {
            private Configuration config = null;

            {
                try {
                    createConfigFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            private void createConfigFile() throws IOException {
                if(config==null&&!Files.exists(configFile.toPath())) {
                    Files.createDirectories(configFile.getParentFile().toPath());
                    Files.createFile(configFile.toPath());
                }else if(config!=null&&!Files.exists(configFile.toPath())) {
                    config=null;
                    createConfigFile();
                }
            }

            @Override
            public Configuration loadConfiguration() throws IOException {
                createConfigFile();
                try(InputStream in= Files.newInputStream(configFile.toPath())) {
                    config=factory.getReader().load(in);
                    return config;
                }
            }

            @Override
            public void storeConfiguration() throws IOException {
                createConfigFile();
                try (OutputStream out = Files.newOutputStream(configFile.toPath())) {
                    factory.getWriter().store(out, config);
                }
            }
        };
    }

    /**
     * Creates a new {@link ConfigurationProvider provider} that uses the given file to load and store its configuration.
     * The file will be in the {@link java.util.Properties properties} format.
     *
     * @param configFile the file for the configuration
     * @return a new provider for the given file
     */
    static ConfigurationProvider newFileBasedProvider(File configFile) {
        return ConfigurationProvider.newFileBasedProvider(configFile, new SimpleConfigurationFactory());
    }

    static ConfigurationProvider newFileBasedProvider(String fileName) {
        return newFileBasedProvider(new File(System.getProperty("user.dir"), fileName));
    }
}
