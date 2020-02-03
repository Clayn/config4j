package net.bplaced.clayn.impl.config4j.io;

import net.bplaced.clayn.impl.config4j.Configuration;

import java.io.IOException;
import java.io.InputStream;

public interface ConfigurationReader {
    Configuration load(InputStream in) throws IOException;
}
