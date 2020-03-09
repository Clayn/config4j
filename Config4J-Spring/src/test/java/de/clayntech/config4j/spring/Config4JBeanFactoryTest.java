package de.clayntech.config4j.spring;

import de.clayntech.config4j.Configuration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Config4JBeanFactory.class)
public class Config4JBeanFactoryTest {

    @Autowired
    public Environment env;
    @Autowired
    private Configuration configuration;
    @Autowired
    private Configuration config;
    @Autowired
    private Config4JBeanFactory factory;

    @Test
    public void testWiring() {
        Assert.assertNotNull(env);
        Assert.assertTrue(env.containsProperty(Config4JBeanFactory.CONFIG_FILE_PATH));
        Assert.assertNotNull(factory);
        Assert.assertNotNull(config);
        Assert.assertNotNull(configuration);
        Assert.assertEquals(configuration, config);
        Assert.assertNotNull(factory.environment);
        Assert.assertTrue(new File(env.getProperty(Config4JBeanFactory.CONFIG_FILE_PATH, "")).exists());
    }

    @After
    public void cleanUp() {
        File f = new File(env.getProperty(Config4JBeanFactory.CONFIG_FILE_PATH, ""));
        if (f.exists()) {
            f.delete();
        }
    }
}
