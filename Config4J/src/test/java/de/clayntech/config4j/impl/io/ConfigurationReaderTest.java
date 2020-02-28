package de.clayntech.config4j.impl.io;

import de.clayntech.config4j.conf.Config4JSetting;
import de.clayntech.config4j.impl.util.GsonConverter;
import de.clayntech.config4j.io.ConfigurationReader;
import de.clayntech.config4j.io.Source;
import de.clayntech.config4j.io.SourceMissingException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ConfigurationReaderTest {

    @Parameterized.Parameter
    public ConfigurationReader reader;


    @Parameterized.Parameters
    public static Collection getConfigurations() {
        return Arrays.asList(new Object[][]{
                {new SimpleConfigurationReader()},
                {new JsonConfigurationReader(GsonConverter.newInstance())}
        });
    }

    @Test(expected = SourceMissingException.class)
    public void testLoadConfigurationSourceMissing() throws IOException {
        Source src = new Source("missing", Source.SourceType.RESOURCE);
        reader.load(src);
    }

    @Test
    public void testLoadConfigurationSourceMissingNoThrow() throws IOException {
        Source src = new Source("missing", Source.SourceType.RESOURCE);
        Config4JSetting.THROW_ERROR_ON_SOURCE_MISSING.set(false);
        try {
            Assert.assertNull(reader.load(src));
        } finally {
            Config4JSetting.THROW_ERROR_ON_SOURCE_MISSING.set(true);
        }
    }
}
