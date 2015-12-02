package io.github.hsyyid.essentialcmds.api.util.config;

import io.github.hsyyid.essentialcmds.managers.config.Config;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;

/**
 * Utility class to get all the config classes from one place
 */
public abstract class Configs {

    public static CommentedConfigurationNode getConfig() {
        return Config.getConfig().get();
    }

    public static void saveConfig() {
        Config.getConfig().save();
        return;
    }
}
