package io.github.hsyyid.essentialcmds.utils;

import com.flowpowered.math.vector.Vector3d;
import io.github.hsyyid.essentialcmds.api.util.config.ConfigManager;
import io.github.hsyyid.essentialcmds.api.util.config.Configs;
import io.github.hsyyid.essentialcmds.api.util.config.Configurable;
import io.github.hsyyid.essentialcmds.managers.config.Warps;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;

import java.util.UUID;

/**
 *
 */
public class WarpUtils {

    private static Configurable warpConfig = Warps.getWarps();
    private static ConfigManager configManager = new ConfigManager();

    public static boolean isWarpPresent(String warpName) {
            return warpConfig.get().getNode("warps").getChildrenMap().containsKey(warpName);
    }

    public static UUID getWarpWorldUUID(String warpName) {
        CommentedConfigurationNode node = Configs.getConfig(warpConfig).getNode("warps", warpName);
        return UUID.fromString(node.getString());
    }

    public static Vector3d getWarpVector(String warpName) {
        CommentedConfigurationNode node = Configs.getConfig(warpConfig).getNode("list", warpName);

        CommentedConfigurationNode x = Configs.getConfig(warpConfig).getNode(node.getPath(), "x");
        CommentedConfigurationNode y = Configs.getConfig(warpConfig).getNode(node.getPath(), "y");
        CommentedConfigurationNode z = Configs.getConfig(warpConfig).getNode(node.getPath(), "z");

        return new Vector3d(x.getDouble(), y.getDouble(), z.getDouble());
    }
}
