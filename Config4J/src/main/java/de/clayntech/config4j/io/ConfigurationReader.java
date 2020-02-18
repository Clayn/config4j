package de.clayntech.config4j.io;

import de.clayntech.config4j.Configuration;

import java.io.IOException;
import java.io.InputStream;

public interface ConfigurationReader {
    Configuration load(InputStream in) throws IOException;
}
