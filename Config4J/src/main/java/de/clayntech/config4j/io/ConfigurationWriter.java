package de.clayntech.config4j.io;

import de.clayntech.config4j.Configuration;

import java.io.IOException;
import java.io.OutputStream;

public interface ConfigurationWriter {
    void store(OutputStream out, Configuration config) throws IOException;
}
