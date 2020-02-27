package de.clayntech.config4j.util;

import de.clayntech.config4j.Configuration;
import de.clayntech.config4j.impl.io.JsonConfigurationFactory;
import de.clayntech.config4j.impl.io.SimpleConfigurationFactory;
import de.clayntech.config4j.impl.util.GsonConverter;
import de.clayntech.config4j.io.ConfigurationFactory;

import java.io.*;

/**
 * Utility class to load configurations stored in the {@code c4j} file format. <br>
 * The format is:<br><br>
 * <code>
 * ## [factory]<br>
 * [configuration data]
 * </code>
 * <br><br>
 * For {@code [factory]} you have to specify the factory class used to get the reader and writer.<br>
 * There are some shortcut values available:<br>
 * {@code simple} for the {@link SimpleConfigurationFactory}<br>
 * {@code json} for the {@link JsonConfigurationFactory}<br>
 * {@code [configuration data]} is the normal data for a configuration in the desired format e.g. json
 *
 * @author Clayn
 * @since 0.1
 */
public final class Config4JFileParser {
    /**
     * Attempts to load a configuration from the given source. The source must provide the configuration in
     * the {@code c4j} format. <br> That format is defined as the format of the configuration file such as a
     * properties or json file but must start with an identification to specify the actual format.<br>
     * That identification must be {@code ## <ident>}. The {@code ident} parameter is the class for the
     * {@link ConfigurationFactory} to use. There are some shortcuts for the included implementations. <br>
     * {@code simple} for the {@link SimpleConfigurationFactory}<br>
     * {@code json} for the {@link JsonConfigurationFactory}
     *
     * @param in the source to load the configuration from
     * @return the loaded configuration
     * @throws IOException if an I/O Exception occurs
     */
    public static Configuration loadConfiguration(InputStream in) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String header = null;
            String line = null;
            boolean headerFound = false;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            while ((line = reader.readLine()) != null) {
                if (headerFound) {
                    bout.write(line.getBytes());
                }
                if (!headerFound && line.startsWith("##")) {
                    header = line;
                    headerFound = true;
                }
            }
            if (!headerFound) {
                throw new IllegalArgumentException("Given source is not a properly formatted c4j file");
            }
            String remain = header.substring(2).trim();
            try {
                ConfigurationFactory factory = getFactory(remain);
                return factory.getReader().load(new ByteArrayInputStream(bout.toByteArray()));
            } catch (Exception e) {
                throw new RuntimeException("Failed to get configuration factory " + remain, e);
            }
        }
    }

    private static ConfigurationFactory getFactory(String name) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        switch (name) {
            case "simple":
                return new SimpleConfigurationFactory();
            case "json":
                return new JsonConfigurationFactory(GsonConverter.newInstance());
            default:
                return (ConfigurationFactory) Class.forName(name).newInstance();
        }
    }
}
