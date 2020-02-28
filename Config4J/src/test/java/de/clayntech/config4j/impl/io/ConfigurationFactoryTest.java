package de.clayntech.config4j.impl.io;

import de.clayntech.config4j.io.ConfigurationFactory;
import de.clayntech.config4j.io.ConfigurationReader;
import de.clayntech.config4j.io.ConfigurationWriter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ConfigurationFactoryTest {

    @Parameterized.Parameter(0)
    public ConfigurationFactory factory;

    @Parameterized.Parameter(1)
    public Class<? extends ConfigurationWriter> writerClass;

    @Parameterized.Parameter(2)
    public Class<? extends ConfigurationReader> readerClass;

    @Parameterized.Parameters
    public static Collection getConfigurations() {
        return Arrays.asList(new Object[][]{
                {new SimpleConfigurationFactory(), SimpleConfigurationWriter.class, SimpleConfigurationReader.class},
                {new JsonConfigurationFactory(), JsonConfigurationWriter.class, JsonConfigurationReader.class}
        });
    }

    @Before
    public void preTest() {
        Assert.assertNotNull(factory);
        Assert.assertNotNull(writerClass);
        Assert.assertNotNull(readerClass);
    }

    @Test
    public void testGetWriter() {
        Assert.assertEquals(writerClass, factory.getWriter().getClass());
    }

    @Test
    public void testGetReader() {
        Assert.assertEquals(readerClass, factory.getReader().getClass());
    }
}
