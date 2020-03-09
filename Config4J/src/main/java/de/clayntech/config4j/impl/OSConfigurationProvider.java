package de.clayntech.config4j.impl;

import de.clayntech.config4j.Configuration;
import de.clayntech.config4j.ConfigurationProvider;

import java.io.File;
import java.io.IOException;

public class OSConfigurationProvider implements ConfigurationProvider {

    private final String fileName;
    private final String appName;
    private final ConfigurationProvider delegate;

    public OSConfigurationProvider(String appName, String fileName) {
        this.fileName = fileName;
        this.appName = appName;
        delegate = ConfigurationProvider.newFileBasedProvider(getOSConfigurationFile());
    }

    private File getOSConfigurationFile() {
        boolean windows = System.getProperty("os.name")
                .toLowerCase().contains("windows");
        File base = new File(System.getProperty("user.home") + (windows ? "/AppData/Roaming" : ""), appName);
        File confFile = new File(base, fileName);
        return confFile;
    }

    @Override
    public Configuration loadConfiguration() throws IOException {
        return delegate.loadConfiguration();
    }

    @Override
    public void storeConfiguration() throws IOException {
        delegate.storeConfiguration();
    }
}
