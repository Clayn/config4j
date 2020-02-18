package de.clayntech.config4j;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Interface for configurations that can have the same keys with different values depending of some identifier.
 * An example could be INI files with their sections.
 */
public interface ProfiledConfiguration extends Configuration {

    String TOP_LEVEL_PROFILE_NAME = "DEFAULT";

    /**
     * Returns all available profile names for this configuration
     *
     * @return all available profile names
     */
    default Set<String> getProfilesNames() {
        return getProfiles()
                .stream()
                .map(ProfiledConfiguration::getName)
                .collect(Collectors.toSet());
    }

    /**
     * Returns the name of the current profile from this confguration. The top level profile must return {@link ProfiledConfiguration#TOP_LEVEL_PROFILE_NAME}
     *
     * @return the name for the current profile
     */
    String getName();

    /**
     * Returns the parent configuration of this one. If it is the top level one {@code null} must be returned
     *
     * @return the configurations parent or {@code null} if it is already the top level one
     */
    ProfiledConfiguration getParent();

    /**
     * Returns all available profiles in this configuration.
     *
     * @return all available profiles
     */
    Set<ProfiledConfiguration> getProfiles();

    /**
     * Returns the sub configuration with the given name if available.
     *
     * @param profile the name for the profile
     * @return the sub configuration or {@code null} if no configuration with the given name is available.
     */
    ProfiledConfiguration getProfile(String profile);

    /**
     * Creates a new profile within the configuration with the given name. If a profile with that name already exists
     * that one should be returned. A new profile has no configurations set
     *
     * @param profile the name for the profile
     * @return the new created profile or an existing one with the same name
     */
    ProfiledConfiguration createProfile(String profile);


}
