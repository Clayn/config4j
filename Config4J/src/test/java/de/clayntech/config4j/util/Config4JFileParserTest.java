package de.clayntech.config4j.util;

import de.clayntech.config4j.Configuration;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class Config4JFileParserTest {

    @Test
    public void testLoadConfiguration() throws IOException {
        Configuration conf = Config4JFileParser.loadConfiguration(getClass().getResourceAsStream("/de/clayntech/config4j/util/test.c4j"));
        Assert.assertNotNull(conf);
        Assert.assertEquals("value", conf.get("key"));
        conf = Config4JFileParser.loadConfiguration(getClass().getResourceAsStream("/de/clayntech/config4j/util/test2.c4j"));
        Assert.assertNotNull(conf);
        Assert.assertEquals("value", conf.get("key"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLoadConfigurationNoHeader() throws IOException {
        Config4JFileParser.loadConfiguration(getClass().getResourceAsStream("/de/clayntech/config4j/util/test_noheader.c4j"));
    }

    @Test(expected = RuntimeException.class)
    public void testLoadConfigurationMissingFactory() throws IOException {
        Config4JFileParser.loadConfiguration(getClass().getResourceAsStream("/de/clayntech/config4j/util/test_missingfactory.c4j"));
    }
}
