package de.clayntech.config4j.impl;

import de.clayntech.config4j.Configuration;
import de.clayntech.config4j.event.ConfigurationChangeEvent;
import de.clayntech.config4j.event.ConfigurationListener;
import de.clayntech.config4j.impl.key.IntKey;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RunWith(Parameterized.class)
public class ConfigurationTest {

    @Parameterized.Parameter
    public Class<? extends Configuration> configurationClass;

    private Configuration configuration;

    @Parameterized.Parameters
    public static Collection getConfigurations() {
        return Arrays.asList(new Object[][]{
                {SimpleConfiguration.class},
                {SimpleProfiledConfiguration.class},
                {MemoryConfiguration.class}
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

    @Test
    public void testAddListener() {
        Map<String, Boolean> map = new HashMap<>();
        map.put("Key 1", false);
        map.put("Key 2", false);
        configuration.set("Key 1", "Old");
        configuration.addListener(new ConfigurationListener() {
            @Override
            public void configurationChanged(ConfigurationChangeEvent evt) {
                if (map.containsKey(evt.getKey())) {
                    map.put(evt.getKey(), true);
                }
                map.put(evt.getOldValue(), true);
                map.put(evt.getNewValue(), true);
            }
        });
        configuration.set("Key 1", "Val");
        Assert.assertTrue(map.get("Key 1"));
        Assert.assertTrue(map.get("Old"));
        Assert.assertTrue(map.get("Val"));
        Assert.assertFalse(map.get("Key 2"));
    }

    @Test
    public void testRemoveListener() {
        Map<String, Boolean> map = new HashMap<>();
        map.put("Key 1", false);
        map.put("Key 2", false);
        ConfigurationListener listener = new ConfigurationListener() {
            @Override
            public void configurationChanged(ConfigurationChangeEvent evt) {
                if (map.containsKey(evt.getKey())) {
                    map.put(evt.getKey(), true);
                }
            }
        };
        configuration.addListener(listener);
        configuration.set("Key 1", "Val");
        Assert.assertTrue(map.get("Key 1"));
        Assert.assertFalse(map.get("Key 2"));
        configuration.removeListener(listener);
        configuration.set("Key 2", "Val");
        Assert.assertTrue(map.get("Key 1"));
        Assert.assertFalse(map.get("Key 2"));
    }
}
