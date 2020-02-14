package net.bplaced.clayn.config4j.io;

import net.bplaced.clayn.config4j.Configuration;

import java.io.IOException;
import java.io.OutputStream;

public interface ConfigurationWriter {
    void store(OutputStream out, Configuration config) throws IOException;
}
