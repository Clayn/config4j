package net.bplaced.clayn.impl.config4j.io;

public interface ConfigurationFactory {
    ConfigurationWriter getWriter();

    ConfigurationReader getReader();
}
