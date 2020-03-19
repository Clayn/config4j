package de.clayntech.config4j.spring;

import de.clayntech.config4j.Config4J;
import de.clayntech.config4j.Configuration;
import de.clayntech.config4j.ConfigurationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.core.env.Environment;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@org.springframework.context.annotation.Configuration
public class Config4JBeanFactory extends AbstractFactoryBean<Configuration> {

    public static final String CONFIG_FILE_PATH = "config4j.application.path";
    public static final String PROVIDER = "config4j.provider.class";
    private static final Logger LOG = LoggerFactory.getLogger(Config4JBeanFactory.class);
    private static final String SPRING_CONFIG_FILE_PATH = String.format("${%s}", CONFIG_FILE_PATH);
    private static final Pattern USER_DIR_PATTERN = Pattern.compile("\\Q${user.dir}\\E");
    @Autowired
    public Environment environment;
    @Value("${" + CONFIG_FILE_PATH + "}")
    private String configFilePath;
    @Value("${" + PROVIDER + "}")
    private String providerClass;

    public Config4JBeanFactory() {
        setSingleton(true);
    }

    @Override
    public final void setSingleton(boolean singleton) {
        super.setSingleton(true);
    }

    @Override
    public Class<?> getObjectType() {
        return Configuration.class;
    }

    private File resolvePath() {
        String property = configFilePath;
        Matcher m = USER_DIR_PATTERN.matcher(property);
        if (m.find()) {
            property = m.replaceAll(System.getProperty("user.dir") + "/");
        }
        return new File(property);
    }

    @Override
    protected Configuration createInstance() throws Exception {
        File path = resolvePath();
        ConfigurationProvider provider = null;
        if (providerClass != null && !providerClass.trim().isEmpty()) {
            try {
                Class<? extends ConfigurationProvider> provClass = (Class<? extends ConfigurationProvider>) Class.forName(providerClass);
                Constructor<? extends ConfigurationProvider> construct = null;
                try {
                    construct = provClass.getDeclaredConstructor();
                } catch (Exception ex) {
                    if (construct == null) {
                        construct = provClass.getConstructor();
                    }
                }
                if (construct != null) {
                    provider = construct.newInstance();
                }
                LOG.debug("Created provider of class: {}", provClass);
            } catch (Exception ex) {
                LOG.warn("Failed to load provider '{}'", providerClass, ex);
            }
        }
        if (provider == null) {
            LOG.info("Creating default provider for file '{}'", path.getAbsolutePath());
            provider = ConfigurationProvider.newFileBasedProvider(path);
        }
        Config4J.setProvider(provider);
        return Config4J.getConfiguration();
    }


}
