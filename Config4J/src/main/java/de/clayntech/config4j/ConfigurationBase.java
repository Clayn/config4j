package de.clayntech.config4j;

import de.clayntech.config4j.annotation.Configurable;
import de.clayntech.config4j.conf.Config4JSetting;
import de.clayntech.config4j.event.ConfigurationListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Base class for all configuration implementations that don't need to extend any other class
 * and don't want to handle the listener management by them self.
 *
 * @author Clayn
 * @since 0.1
 */
public abstract class ConfigurationBase implements Configuration {

    private transient final List<ConfigurationListener> listeners = new ArrayList<>();

    /**
     * Gives access to all listeners in this configuration base. If configured with {@link Config4JSetting#CONFIGURATION_BASE_UNMODIFIABLE_LIST}
     * the returned list is unmodifiable.
     *
     * @return a list with all listeners added to this configuration base.
     */
    @Configurable("CONFIGURATION_BASE_UNMODIFIABLE_LIST")
    protected final List<ConfigurationListener> getListeners() {
        return Config4JSetting.CONFIGURATION_BASE_UNMODIFIABLE_LIST.get() ? Collections.unmodifiableList(listeners) : listeners;
    }

    /**
     * {@inheritDoc}
     *
     * @param listener the listener to add
     */
    @Override
    public void addListener(ConfigurationListener listener) {
        Optional.ofNullable(listener).ifPresent(listeners::add);
    }

    /**
     * {@inheritDoc}
     *
     * @param listener the listener to remove
     */
    @Override
    public void removeListener(ConfigurationListener listener) {
        Optional.ofNullable(listener).ifPresent(listeners::remove);
    }
}
