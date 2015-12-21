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
package io.github.hsyyid.essentialcmds.cmdexecutors.warp;

import com.flowpowered.math.vector.Vector3d;
import io.github.hsyyid.essentialcmds.utils.WarpUtils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;

import java.util.Objects;
import java.util.Optional;

/**
 * Handles the /warp command
 */
public class WarpExecutor implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String warpName = args.<String> getOne("warpName").get();
        Optional<Player> playerOptional = args.<Player>getOne("player");

        if (!playerOptional.isPresent()) {
            if (src instanceof Player) {
                Player player = (Player) src;

                if (WarpUtils.isWarpPresent(warpName)) {
                    if (player.hasPermission("essentialcmds.warp." + warpName)) {
                        Vector3d position = WarpUtils.getWarpVector(warpName);
                        if (!Objects.equals(player.getWorld().getUniqueId(), WarpUtils.getWarpWorldUUID(warpName))) {
                            player.transferToWorld(WarpUtils.getWarpWorldUUID(warpName), position);
                            src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported to Warp " + warpName));
                            return CommandResult.success();
                        } else {
                            player.setLocation(new Location<>(player.getWorld(), position));
                            return CommandResult.empty();
                        }
                    } else {
                        src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You do not have permission to use this warp!"));
                        return CommandResult.empty();
                    }
                } else {
                    src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Warp " + warpName + " does not exist!"));
                    return CommandResult.empty();
                }
            } else {
                src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /warp!"));
                return CommandResult.empty();
            }
        } else {
            Player player = playerOptional.get();
            if (WarpUtils.isWarpPresent(warpName)) {
                if (src.hasPermission("essentialcmds.warp." + warpName) && src.hasPermission("essentialcmds.warp.others")) {
                    Vector3d position = WarpUtils.getWarpVector(warpName);
                    if (!Objects.equals(player.getWorld().getUniqueId(), WarpUtils.getWarpWorldUUID(warpName))) {
                        player.transferToWorld(WarpUtils.getWarpWorldUUID(warpName), position);
                        src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported "+ player.getName() + "to Warp " + warpName));
                        player.sendMessage(Texts.of(TextColors.GREEN, "Succes!", TextColors.YELLOW, src.getName(), " Teleported your to Warp ", warpName));
                        return CommandResult.success();
                    } else {
                        player.setLocation(new Location<>(player.getWorld(), position));
                        src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported "+ player.getName() + "to Warp " + warpName));
                        player.sendMessage(Texts.of(TextColors.GREEN, "Succes!", TextColors.YELLOW, src.getName(), " Teleported your to Warp ", warpName));
                        return CommandResult.success();
                    }
                } else {
                    src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You do not have permission to use this warp!"));
                    return CommandResult.empty();
                }
            } else {
                src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Warp " + warpName + " does not exist!"));
                return CommandResult.empty();
            }
        }
    }
}
