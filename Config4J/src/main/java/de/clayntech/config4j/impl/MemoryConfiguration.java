package de.clayntech.config4j.impl;

import de.clayntech.config4j.ProfiledConfiguration;
import de.clayntech.config4j.annotation.Memory;

@Memory
public class MemoryConfiguration extends SimpleProfiledConfiguration {
    MemoryConfiguration(String profile, MemoryConfiguration memoryConfiguration) {
        super(profile, memoryConfiguration);
    }

    public MemoryConfiguration() {
        this(TOP_LEVEL_PROFILE_NAME, null);
    }

    @Override
    public ProfiledConfiguration createProfile(String profile) {
        if (!getProfiles0().containsKey(profile)) {
            getProfiles0().put(profile, new MemoryConfiguration(profile, this));
        }
        return getProfile(profile);
    }
}
