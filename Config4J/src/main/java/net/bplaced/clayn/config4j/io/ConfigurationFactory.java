package net.bplaced.clayn.config4j.io;

public interface ConfigurationFactory {
    ConfigurationWriter getWriter();

    ConfigurationReader getReader();
}
