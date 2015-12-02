package io.github.hsyyid.essentialcmds.managers.config;

import io.github.hsyyid.essentialcmds.EssentialCmds;
import io.github.hsyyid.essentialcmds.api.util.config.Configurable;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Handles the config.conf file
 */
public class Config implements Configurable {

    private Config() {}

    private static Config config = new Config();

    public static Config getConfig() {
        return config;
    }

    private Path configFile = Paths.get(EssentialCmds.getEssentialCmds().getConfigDir() + "/config.conf");
    private ConfigurationLoader<CommentedConfigurationNode> configLoader = HoconConfigurationLoader.builder().setPath(configFile).build();
    private CommentedConfigurationNode configNode;

    @Override
    public void setup() {
        if (!Files.exists(configFile)) {
            try {
                Files.createFile(configFile);
                load();
                populate();
                save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            load();
        }
    }

    @Override
    public void load() {
        try {
            configNode = configLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        try {
            configLoader.save(configNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void populate() {

        get().getNode("afk", "timer").setValue(30000);
        get().getNode("afk", "kick", "use").setValue(false);
        get().getNode("afk", "kick", "timer").setValue(30000);
        get().getNode("joinmsg").setValue("&4Welcome!");
    }

    @Override
    public CommentedConfigurationNode get() {
        return configNode;
    }
}
