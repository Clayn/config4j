package net.bplaced.clayn.impl;

import net.bplaced.clayn.impl.config4j.Configuration;
import net.bplaced.clayn.impl.config4j.impl.SimpleConfiguration;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class SimpleConfigurationTest {

    @Test
    public void testGetProfiles() {
        SimpleConfiguration config = new SimpleConfiguration();
        Assert.assertTrue(config.getProfiles().isEmpty());
        config.set("*profile1.somevalue", "Value");
        Assert.assertFalse(config.getProfiles().isEmpty());
        Assert.assertTrue(config.getProfiles().containsAll(Arrays.asList("profile1")));
        config.set("*profile2.somevalue", "Value 2");
        Assert.assertFalse(config.getProfiles().isEmpty());
        Assert.assertTrue(config.getProfiles().containsAll(Arrays.asList("profile1", "profile2")));
    }

    @Test
    public void testGetProfile() {
        SimpleConfiguration config = new SimpleConfiguration();
        Configuration profile = null;
        try {
            profile = config.getProfile("profile1");
            Assert.fail();
        } catch (Exception ex) {
            Assert.assertNull(profile);
        }
        config.set("*profile1.somevalue", "Value");
        Assert.assertNotNull(config.getProfile("profile1"));
        Assert.assertEquals("Value", config.getProfile("profile1").get("somevalue"));
        config.set("*profile2.somevalue", "Value 2");
        Assert.assertNotNull(config.getProfile("profile2"));
        Assert.assertEquals("Value 2", config.getProfile("profile2").get("somevalue"));
    }
}
