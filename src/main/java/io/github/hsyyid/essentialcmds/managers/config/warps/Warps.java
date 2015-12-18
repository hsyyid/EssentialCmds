package io.github.hsyyid.essentialcmds.managers.config.warps;

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
 *
 */
public class Warps implements Configurable {

    private Warps() {}

    private static Warps warps = new Warps();

    public static Warps getWarps() {
        return warps;
    }

    private Path warpsDirectory = Paths.get(EssentialCmds.getEssentialCmds().getConfigDir() + "/warps/");
    private Path warpsFile = Paths.get(warpsDirectory + "warps.conf");
    private ConfigurationLoader<CommentedConfigurationNode> warpsLoader = HoconConfigurationLoader.builder().setPath(warpsFile).build();
    private CommentedConfigurationNode warpsNode;


    @Override
    public void setup() {
        if (!Files.exists(warpsDirectory))
            try {
                Files.createDirectories(warpsDirectory);
            } catch (IOException e) {
                e.printStackTrace();
            }

        if (!Files.exists(warpsFile))
            try {
                Files.createFile(warpsFile);
                load();
                populate();
                save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        load();
    }

    @Override
    public void load() {
        try {
            warpsNode = warpsLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        try {
            warpsLoader.save(warpsNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void populate() {
        get().getNode("version").setValue(1);
    }

    @Override
    public CommentedConfigurationNode get() {
        return warpsNode;
    }
}
