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
