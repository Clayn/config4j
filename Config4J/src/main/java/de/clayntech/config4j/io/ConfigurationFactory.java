package de.clayntech.config4j.io;

public interface ConfigurationFactory {
    ConfigurationWriter getWriter();

    ConfigurationReader getReader();
}
