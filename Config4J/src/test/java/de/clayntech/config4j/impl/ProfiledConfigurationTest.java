package de.clayntech.config4j.impl;

import de.clayntech.config4j.Configuration;
import de.clayntech.config4j.ProfiledConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ProfiledConfigurationTest {

    @Parameterized.Parameter
    public Class<? extends ProfiledConfiguration> configurationClass;

    private ProfiledConfiguration configuration;

    @Parameterized.Parameters
    public static Collection getConfigurations() {
        return Arrays.asList(new Object[][]{
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
    public void testGetProfiles() {
        Assert.assertTrue(configuration.getProfiles().isEmpty());
        configuration.createProfile("profile1");
        Assert.assertEquals(1, configuration.getProfiles().size());
        configuration.getProfile("profile1").createProfile("profile2");
        Assert.assertEquals(1, configuration.getProfiles().size());
        Assert.assertEquals(1, configuration.getProfile("profile1").getProfiles().size());
    }

    @Test
    public void testSetInProfile() {
        Configuration conf = configuration.createProfile("profile");
        configuration.set("Key", "Val");
        Assert.assertNull(conf.get("Key"));
        conf.set("Key", "Sub");
        Assert.assertEquals("Val", configuration.get("Key"));
        Assert.assertEquals("Sub", conf.get("Key"));
    }

    @Test
    public void testGetProfile() {
        Assert.assertNull(configuration.getProfile("profile"));
        configuration.createProfile("profile");
        Assert.assertNotNull(configuration.getProfile("profile"));
    }

    @Test
    public void testGetName() {
        testGetProfiles();
        Assert.assertEquals(ProfiledConfiguration.TOP_LEVEL_PROFILE_NAME, configuration.getName());
        Assert.assertEquals("profile1", configuration.getProfile("profile1").getName());
    }

    @Test
    public void testGetParent() {
        testGetProfiles();
        Assert.assertNull(configuration.getParent());
        Assert.assertEquals(configuration, configuration.getProfile("profile1").getParent());
    }

    @Test
    public void testGetProfileNames() {
        testGetProfiles();
        Assert.assertEquals(1, configuration.getProfileNames().size());
        Assert.assertEquals(1, configuration.getProfile("profile1").getProfileNames().size());

        Assert.assertTrue(configuration.getProfileNames().contains("profile1"));
        Assert.assertTrue(configuration.getProfile("profile1").getProfileNames().contains("profile2"));
    }
}
