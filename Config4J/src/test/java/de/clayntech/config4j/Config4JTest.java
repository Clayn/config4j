package de.clayntech.config4j;

import de.clayntech.config4j.conf.Config4JSetting;
import de.clayntech.config4j.impl.MemoryConfiguration;
import de.clayntech.config4j.impl.SimpleConfiguration;
import org.junit.*;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import java.io.IOException;

public class Config4JTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    private ConfigurationProvider provider;
    private boolean createDefaultSetting = false;
    private boolean logSetting = false;

    @Before
    public void setupTest() {
        provider = Mockito.mock(ConfigurationProvider.class);
        Config4J.setProvider(provider);
        createDefaultSetting = Config4JSetting.CREATE_DEFAULT_PROVIDER.get();
        logSetting = Config4JSetting.DEBUG_LOADED_DEFAULT_CONFIGURATION.get();
    }

    @After
    public void cleanup() {
        Config4JSetting.CREATE_DEFAULT_PROVIDER.set(createDefaultSetting);
        Config4JSetting.DEBUG_LOADED_DEFAULT_CONFIGURATION.set(logSetting);
    }


    @Test
    @SuppressWarnings("all")
    public void testGetConfigurationNoProviderCreate() {
        Config4J.setProvider(null);
        Config4JSetting.CREATE_DEFAULT_PROVIDER.set(true);
        System.setProperty("user.dir", folder.getRoot().getAbsolutePath());
        Configuration config = Config4J.getConfiguration();
        Assert.assertNotNull(config);
        Assert.assertNotEquals(MemoryConfiguration.class, config.getClass());
        Assert.assertNotEquals(0, folder.getRoot().list().length);
    }

    @Test
    @SuppressWarnings("all")
    public void testGetConfigurationNoProvider() {
        Config4J.setProvider(null);
        Config4JSetting.CREATE_DEFAULT_PROVIDER.set(false);
        System.setProperty("user.dir", folder.getRoot().getAbsolutePath());
        Configuration config = Config4J.getConfiguration();
        Assert.assertNotNull(config);
        Assert.assertEquals(MemoryConfiguration.class, config.getClass());
        Assert.assertEquals(0, folder.getRoot().list().length);
    }

    @Test
    public void testGetConfiguration() throws IOException {
        Configuration config = Mockito.mock(Configuration.class);
        Mockito.when(provider.loadConfiguration()).thenReturn(config);
        Configuration loaded = Config4J.getConfiguration();
        Assert.assertNotNull(loaded);
        Assert.assertEquals(config, loaded);
        Mockito.verify(provider, Mockito.times(1)).loadConfiguration();
    }

    @Test(expected = RuntimeException.class)
    public void testGetConfigurationError() throws IOException {
        Configuration config = Mockito.mock(Configuration.class);
        Mockito.when(provider.loadConfiguration()).thenThrow(new IOException());
        Config4J.getConfiguration();
    }

    @Test
    public void testStoreConfiguration() throws IOException {
        Mockito.doNothing().when(provider).storeConfiguration();
        Config4J.saveConfiguration();
        Mockito.verify(provider, Mockito.times(1)).storeConfiguration();
    }

    @Test(expected = RuntimeException.class)
    public void testStoreConfigurationError() throws IOException {
        Mockito.doThrow(new IOException()).when(provider).storeConfiguration();
        Config4J.saveConfiguration();
    }

    @Test
    public void testStoreConfigurationNoProvider() throws IOException {
        Config4J.setProvider(null);
        Config4J.saveConfiguration();
    }

    @Test
    public void testImportDefaultConfiguration() throws IOException {
        Configuration config = new SimpleConfiguration();
        config.set("Key", "Val");
        Mockito.when(provider.loadConfiguration()).thenReturn(config);
        Config4J.importDefaultConfiguration(config);
        Mockito.verify(provider, Mockito.atLeast(1)).loadConfiguration();
        Assert.assertEquals("Val", Config4J.getConfiguration().get("Key"));
    }

    @Test
    public void testImportDefaultConfigurationNoProvider() throws IOException {
        Configuration imp = new SimpleConfiguration();
        Config4JSetting.CREATE_DEFAULT_PROVIDER.set(false);
        Config4J.setProvider(null);
        Config4J.importDefaultConfiguration(imp);
        Configuration config = Config4J.getConfiguration();
        Assert.assertNotNull(config);
        Assert.assertEquals(MemoryConfiguration.class, config.getClass());
        Assert.assertEquals(0, folder.getRoot().list().length);
    }

    @Test
    public void testInitDefaultConfiguration() throws IOException {
        Configuration config = new SimpleConfiguration();
        Config4JSetting.DEBUG_LOADED_DEFAULT_CONFIGURATION.set(true);
        Mockito.when(provider.loadConfiguration()).thenReturn(config);
        Config4J.initDefaultConfiguration();
        Assert.assertEquals("Val", Config4J.getConfiguration().get("Key"));
    }

    @Test
    public void testInitDefaultConfigurationNoLog() throws IOException {
        Configuration config = new SimpleConfiguration();
        Mockito.when(provider.loadConfiguration()).thenReturn(config);
        Config4J.initDefaultConfiguration();
        Assert.assertEquals("Val", Config4J.getConfiguration().get("Key"));
    }

}
