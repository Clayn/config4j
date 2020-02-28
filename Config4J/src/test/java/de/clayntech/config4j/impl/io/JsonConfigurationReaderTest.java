package de.clayntech.config4j.impl.io;

import de.clayntech.config4j.Configuration;
import de.clayntech.config4j.ProfiledConfiguration;
import de.clayntech.config4j.impl.SimpleProfiledConfiguration;
import de.clayntech.config4j.impl.util.GsonConverter;
import de.clayntech.config4j.io.Source;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class JsonConfigurationReaderTest {

    @Test
    public void testLoadConfiguration() throws IOException {
        Configuration conf;
        Assert.assertNotNull(getClass().getResourceAsStream("/de/clayntech/config4j/impl/io/config.json"));
        try (InputStream in = getClass().getResourceAsStream("/de/clayntech/config4j/impl/io/config.json")) {
            conf = new JsonConfigurationReader(GsonConverter.newInstance()).load(in);
        }
        Assert.assertNotNull(conf);
        Assert.assertEquals("Val", conf.get("Key"));
        ProfiledConfiguration profiled = (ProfiledConfiguration) conf;
        Assert.assertEquals("Val", profiled.getProfile("profile").get("Key"));
    }

    @Test
    public void testReadWriteConfiguration() throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ProfiledConfiguration config = new SimpleProfiledConfiguration();
        config.set("Key", "Val");
        config.set("Key2", "Val2");
        config.createProfile("profile").set("Key", "Val");
        new JsonConfigurationWriter(GsonConverter.newInstance())
                .store(bout, config);
        bout.flush();
        System.out.println(bout.toString());
        try (InputStream in = new ByteArrayInputStream(bout.toByteArray())) {
            Configuration conf = new JsonConfigurationReader(GsonConverter.newInstance()).load(in);
            Assert.assertNotNull(conf);
            Assert.assertEquals("Val", conf.get("Key"));
            ProfiledConfiguration profiled = (ProfiledConfiguration) conf;
            Assert.assertEquals("Val", profiled.getProfile("profile").get("Key"));
        }
    }

    @Test
    public void testLoadConfigurationSource() throws IOException {
        Configuration conf;
        Source src = new Source("/de/clayntech/config4j/impl/io/config.json", Source.SourceType.RESOURCE);
        Assert.assertTrue(src.exists());
        conf = new JsonConfigurationReader(GsonConverter.newInstance()).load(src);
        Assert.assertNotNull(conf);
        Assert.assertEquals("Val", conf.get("Key"));
        ProfiledConfiguration profiled = (ProfiledConfiguration) conf;
        Assert.assertEquals("Val", profiled.getProfile("profile").get("Key"));
    }


}
