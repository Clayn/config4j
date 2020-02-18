package de.clayntech.config4j;

import de.clayntech.config4j.event.ConfigurationListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class ConfigurationBase implements Configuration {

    private final List<ConfigurationListener> listeners=new ArrayList<>();

    protected final List<ConfigurationListener> getListeners() {
        return listeners;
    }

    @Override
    public void addListener(ConfigurationListener listener) {
        Optional.ofNullable(listener).ifPresent(listeners::add);
    }

    @Override
    public void removeListener(ConfigurationListener listener) {
        Optional.ofNullable(listener).ifPresent(listeners::remove);
    }
}
