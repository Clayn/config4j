package de.clayntech.config4j.impl.io;

import de.clayntech.config4j.Config4J;
import de.clayntech.config4j.Configuration;
import de.clayntech.config4j.conf.Config4JSetting;
import de.clayntech.config4j.io.Source;
import de.clayntech.config4j.io.SourceMissingException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class SimpleConfigurationReaderTest {

    @Test
    public void testLoadConfiguration() throws IOException {
        Configuration conf;
        Assert.assertNotNull(getClass().getResourceAsStream("/de/clayntech/config4j/impl/io/simple.properties"));
        try (InputStream in = getClass().getResourceAsStream("/de/clayntech/config4j/impl/io/simple.properties")) {
            conf = new SimpleConfigurationReader().load(in);
        }
        Assert.assertNotNull(conf);
        Assert.assertEquals("Foo", conf.get("Key1"));
        Assert.assertEquals("Bah", conf.get("Key2"));
    }

    @Test
    public void testLoadConfigurationSource() throws IOException {
        Configuration conf;
        Source src = new Source("/de/clayntech/config4j/impl/io/simple.properties", Source.SourceType.RESOURCE);
        Assert.assertTrue(src.exists());
        conf = new SimpleConfigurationReader().load(src);
        Assert.assertNotNull(conf);
        Assert.assertEquals("Foo", conf.get("Key1"));
        Assert.assertEquals("Bah", conf.get("Key2"));
    }

    @Test(expected = SourceMissingException.class)
    public void testLoadConfigurationSourceMissing() throws IOException {
        Source src = new Source("missing", Source.SourceType.RESOURCE);
        new SimpleConfigurationReader().load(src);
    }

    @Test
    public void testLoadConfigurationSourceMissingNoThrow() throws IOException {
        Source src = new Source("missing", Source.SourceType.RESOURCE);
        Config4JSetting.THROW_ERROR_ON_SOURCE_MISSING.set(false);
        try {
            Assert.assertNull(new SimpleConfigurationReader().load(src));
        } finally {
            Config4JSetting.THROW_ERROR_ON_SOURCE_MISSING.set(true);
        }
    }
}
