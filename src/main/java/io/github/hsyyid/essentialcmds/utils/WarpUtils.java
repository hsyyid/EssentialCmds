/*
 * This file is part of EssentialCmds, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2015 - 2015 HassanS6000
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
