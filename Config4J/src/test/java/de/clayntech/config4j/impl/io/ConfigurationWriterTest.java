package de.clayntech.config4j.impl.io;

import de.clayntech.config4j.Configuration;
import de.clayntech.config4j.impl.MemoryConfiguration;
import de.clayntech.config4j.impl.SimpleConfiguration;
import de.clayntech.config4j.impl.util.GsonConverter;
import de.clayntech.config4j.io.ConfigurationWriter;
import de.clayntech.config4j.io.NotStorableException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ConfigurationWriterTest {
    @Parameterized.Parameter
    public ConfigurationWriter writer;


    @Parameterized.Parameters
    public static Collection getConfigurations() {
        return Arrays.asList(new Object[][]{
                {new SimpleConfigurationWriter()},
                {new JsonConfigurationWriter(GsonConverter.newInstance())}
        });
    }

    @Before
    public void beforeTest() throws IllegalAccessException, InstantiationException {
        Assert.assertNotNull(writer);
    }

    @Test
    public void testWriteConfiguration() throws IOException {
        Configuration config = new SimpleConfiguration();
        config.set("Key", "Val");
        try (ByteArrayOutputStream bout = new ByteArrayOutputStream()) {
            writer.store(bout, config);
            bout.flush();
            Assert.assertFalse(bout.toString().trim().isEmpty());
        }
    }

    @Test(expected = NotStorableException.class)
    public void testWriteConfigurationNotStorable() throws IOException {
        Configuration config = new MemoryConfiguration();
        config.set("Key", "Val");
        try (ByteArrayOutputStream bout = new ByteArrayOutputStream()) {
            writer.store(bout, config);
        }
    }

    @Test(expected = NullPointerException.class)
    public void testWriteConfigurationStreamNull() throws IOException {
        Configuration config = new SimpleConfiguration();
        config.set("Key", "Val");
        try (ByteArrayOutputStream bout = new ByteArrayOutputStream()) {
            writer.store(null, config);
        }
    }

    @Test(expected = NullPointerException.class)
    public void testWriteConfigurationConfigNull() throws IOException {
        Configuration config = new SimpleConfiguration();
        config.set("Key", "Val");
        try (ByteArrayOutputStream bout = new ByteArrayOutputStream()) {
            writer.store(bout, null);
        }
    }
}
