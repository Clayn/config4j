package de.clayntech.config4j.impl.io;

import de.clayntech.config4j.Configuration;
import de.clayntech.config4j.impl.MemoryConfiguration;
import de.clayntech.config4j.impl.SimpleConfiguration;
import de.clayntech.config4j.io.ConfigurationWriter;
import de.clayntech.config4j.io.NotStorableException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class SimpleConfigurationWriterTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    private Configuration configuration;
    private ConfigurationWriter writer;
    private File configFile;
    private Path configPath;

    @Before
    public void setUpTest() throws IOException {
        configuration = new SimpleConfiguration();
        configFile = folder.newFile("config.properties");
        configPath = configFile.toPath();
        writer = new SimpleConfigurationWriter();
    }

    @Test
    public void testStoreEmptyConfiguration() throws IOException {
        configFile.delete();
        Assert.assertFalse(configFile.exists());
        try (OutputStream out = Files.newOutputStream(configPath)) {
            writer.store(out, configuration);
            out.flush();
        }
        Assert.assertTrue(configFile.exists());
        Properties prop = new Properties();
        try (InputStream in = Files.newInputStream(configPath)) {
            prop.load(in);
        }
        Assert.assertTrue(prop.stringPropertyNames().isEmpty());
    }


    @Test
    public void testStoreConfiguration() throws IOException {
        configFile.delete();
        configuration.set("Key", "Val");
        configuration.set("Key 1", "Val 1");
        Assert.assertFalse(configFile.exists());
        try (OutputStream out = Files.newOutputStream(configPath)) {
            writer.store(out, configuration);
            out.flush();
        }
        Assert.assertTrue(configFile.exists());
        Properties prop = new Properties();
        try (InputStream in = Files.newInputStream(configPath)) {
            prop.load(in);
        }
        Assert.assertFalse(prop.stringPropertyNames().isEmpty());
        Assert.assertTrue(prop.stringPropertyNames().containsAll(configuration.getConfigurations()));
        for (String key : prop.stringPropertyNames()) {
            Assert.assertEquals(configuration.get(key), prop.getProperty(key));
        }
    }

    @Test(expected = NullPointerException.class)
    public void testStoreConfigurationStreamNull() throws IOException {
        configFile.delete();
        Assert.assertFalse(configFile.exists());
        try (OutputStream out = Files.newOutputStream(configPath)) {
            writer.store(null, configuration);
            out.flush();
        }
    }

    @Test(expected = NullPointerException.class)
    public void testStoreConfigurationConfigurationNull() throws IOException {
        configFile.delete();
        Assert.assertFalse(configFile.exists());
        try (OutputStream out = Files.newOutputStream(configPath)) {
            writer.store(out, null);
            out.flush();
        }
    }

    @Test(expected = NotStorableException.class)
    public void testStoreConfigurationNotStorable() throws IOException {
        configFile.delete();
        Assert.assertFalse(configFile.exists());
        try (OutputStream out = Files.newOutputStream(configPath)) {
            writer.store(out, new MemoryConfiguration());
            out.flush();
        }
    }

}
