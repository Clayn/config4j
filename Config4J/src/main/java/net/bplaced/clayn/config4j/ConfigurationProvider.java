package net.bplaced.clayn.config4j;

import net.bplaced.clayn.config4j.io.ConfigurationFactory;
import net.bplaced.clayn.impl.io.SimpleConfigurationFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public interface ConfigurationProvider {
    Configuration loadConfiguration() throws IOException;

    void storeConfiguration() throws IOException;

    /**
     * Creates a new {@link ConfigurationProvider provider} that uses the given file to load and store
     * its configuration. The file will be created upon calling this method.
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
                try(OutputStream out=Files.newOutputStream(configFile.toPath())) {
                    factory.getWriter().store(out,config);
                }
            }
        };
    }

    static ConfigurationProvider newFileBasedProvider(File configFile) {
        return ConfigurationProvider.newFileBasedProvider(configFile, new SimpleConfigurationFactory());
    }
}
