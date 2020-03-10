package de.clayntech.config4j.impl;

import de.clayntech.config4j.Configuration;
import de.clayntech.config4j.ConfigurationProvider;

import java.io.File;
import java.io.IOException;

/**
 * A configuration provider that uses a configuration file with a operating system dependent location.
 * The provider requires an application name to create a specific directory. <br>
 * Currently two different locations are implemented:<br><br>
 * Windows: ${user.home}/AppData/Roaming/{appName}/{fileName}<br>
 * Others: ${user.home}/{appName}/{fileName}<br><br>
 * {@code {$user.home}} will be the value from {@link System#getProperty(String)}
 */
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

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     * @throws IOException {@inheritDoc}
     */
    @Override
    public Configuration loadConfiguration() throws IOException {
        return delegate.loadConfiguration();
    }

    @Override
    public void storeConfiguration() throws IOException {
        delegate.storeConfiguration();
    }
}
