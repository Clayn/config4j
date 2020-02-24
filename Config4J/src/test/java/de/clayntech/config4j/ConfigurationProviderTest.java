package de.clayntech.config4j;

import de.clayntech.config4j.impl.SimpleConfiguration;
import de.clayntech.config4j.io.ConfigurationFactory;
import de.clayntech.config4j.io.ConfigurationReader;
import de.clayntech.config4j.io.ConfigurationWriter;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ConfigurationProviderTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testCreateFileBasedProvider() throws IOException {
        File f = folder.newFile("test.properties");
        if (f.exists()) {
            f.delete();
        }
        Assert.assertFalse(f.exists());
        ConfigurationProvider.newFileBasedProvider(f);
        Assert.assertTrue(f.exists());
    }

    @Test
    public void testCreateFileBasedProviderSave() throws IOException {
        ConfigurationFactory factory = Mockito.mock(ConfigurationFactory.class);
        ConfigurationWriter writer = Mockito.mock(ConfigurationWriter.class);
        Mockito.when(factory.getWriter()).thenReturn(writer);
        ConfigurationProvider prov = ConfigurationProvider.newFileBasedProvider(folder.newFile("test.properties"), factory);
        prov.storeConfiguration();
        Mockito.verify(factory, Mockito.times(1))
                .getWriter();
        Mockito.verify(writer, Mockito.times(1))
                .store(Mockito.any(), Mockito.any());
    }

    @Test
    public void testCreateFileBasedProviderLoad() throws IOException {
        Configuration back = new SimpleConfiguration();
        ConfigurationFactory factory = Mockito.mock(ConfigurationFactory.class);
        ConfigurationReader reader = Mockito.mock(ConfigurationReader.class);
        Mockito.when(reader.load(Mockito.any(InputStream.class)))
                .thenReturn(back);
        Mockito.when(factory.getReader()).thenReturn(reader);
        ConfigurationProvider prov = ConfigurationProvider.newFileBasedProvider(folder.newFile("test.properties"), factory);
        Configuration conf = prov.loadConfiguration();
        Assert.assertEquals(back, conf);
        Mockito.verify(factory, Mockito.times(1))
                .getReader();
        Mockito.verify(reader, Mockito.times(1))
                .load(Mockito.any(InputStream.class));
    }
}
