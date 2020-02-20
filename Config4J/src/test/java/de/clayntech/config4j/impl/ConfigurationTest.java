package de.clayntech.config4j.impl;

import de.clayntech.config4j.Configuration;
import de.clayntech.config4j.impl.key.IntKey;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ConfigurationTest {

    @Parameterized.Parameter
    public Class<? extends Configuration> configurationClass;

    private Configuration configuration;

    @Parameterized.Parameters
    public static Collection getConfigurations() {
        return Arrays.asList(new Object[][]{
                {SimpleConfiguration.class},
                {JsonConfiguration.class}
        });
    }

    @Before
    public void beforeTest() throws IllegalAccessException, InstantiationException {
        Assert.assertNotNull(configurationClass);
        configuration = configurationClass.newInstance();
    }

    @Test
    public void testGetConfigurations() {
        Assert.assertTrue(configuration.getConfigurations().isEmpty());
        configuration.set("Key", "Val");
        Assert.assertFalse(configuration.getConfigurations().isEmpty());
        Assert.assertTrue(configuration.getConfigurations().contains("Key"));
    }

    @Test
    public void testGetDefault() {
        Assert.assertEquals("Val", configuration.get("Key", "Val"));
        configuration.set("Key", "RealVal");
        Assert.assertEquals("RealVal", configuration.get("Key", "Val"));
    }

    @Test
    public void testGet() {
        Assert.assertNull(configuration.get("Key"));
        configuration.set("Key", "RealVal");
        Assert.assertEquals("RealVal", configuration.get("Key"));
    }

    @Test
    public void testGetKeyDefault() {
        Assert.assertEquals(5, (long) configuration.get(new IntKey("Key"), 5));
        configuration.set(new IntKey("Key"), 42);
        Assert.assertEquals(42, (long) configuration.get(new IntKey("Key"), 5));
    }

    @Test
    public void testGetKey() {
        Assert.assertNull(configuration.get(new IntKey("Key")));
        configuration.set(new IntKey("Key"), 42);
        Assert.assertEquals(42, (long) configuration.get(new IntKey("Key")));
    }

    @Test(expected = NullPointerException.class)
    public void testSetValueNull() {
        configuration.set("Key", null);
    }

    @Test(expected = NullPointerException.class)
    public void testSetKeyNull() {
        configuration.set((String) null, "Val");
    }

    @Test(expected = NullPointerException.class)
    public void testSetKeyValueNull() {
        configuration.set(new IntKey("Key"), null);
    }

    @Test(expected = NullPointerException.class)
    public void testSetKeyKeyValueNull() {
        configuration.set(null, 5);
    }
}
