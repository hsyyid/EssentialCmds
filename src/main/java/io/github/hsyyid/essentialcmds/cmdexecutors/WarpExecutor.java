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
package io.github.hsyyid.essentialcmds.cmdexecutors;

import com.flowpowered.math.vector.Vector3d;
import io.github.hsyyid.essentialcmds.utils.Utils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Objects;
import java.util.Optional;

public class WarpExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		String warpName = ctx.<String> getOne("warp name").get();
		Optional<Player> optionalPlayer = ctx.<Player> getOne("player");

		if (!optionalPlayer.isPresent())
		{
			if (src instanceof Player)
			{
				Player player = (Player) src;

				if (Utils.isWarpInConfig(warpName))
				{
					if (player.hasPermission("essentialcmds.warp." + warpName))
					{
						if (!Objects.equals(player.getWorld().getUniqueId(), Utils.getWarpWorldUUID(warpName)))
						{
							Vector3d position = new Vector3d(Utils.getWarpX(warpName), Utils.getWarpY(warpName), Utils.getWarpZ(warpName));
							player.transferToWorld(Utils.getWarpWorldUUID(warpName), position);
							src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported to Warp " + warpName));
							return CommandResult.success();
						}
						else
						{
							Location<World> warp = new Location<>(player.getWorld(), Utils.getWarpX(warpName), Utils.getWarpY(warpName), Utils.getWarpZ(warpName));
							player.setLocation(warp);
						}
					}
					else
					{
						src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You do not have permission to use this warp!"));
						return CommandResult.success();
					}
				}
				else
				{
					src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Warp " + warpName + " does not exist!"));
				}
			}
			else if (src instanceof ConsoleSource)
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /warp!"));
			}
			else if (src instanceof CommandBlockSource)
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /warp!"));
			}
		}
		else
		{
			Player player = optionalPlayer.get();

			if (Utils.isWarpInConfig(warpName))
			{
				if (src.hasPermission("essentialcmds.warp." + warpName) && src.hasPermission("essentialcmds.warp.others"))
				{
					if (!Objects.equals(player.getWorld().getUniqueId(), Utils.getWarpWorldUUID(warpName)))
					{
						Vector3d position = new Vector3d(Utils.getWarpX(warpName), Utils.getWarpY(warpName), Utils.getWarpZ(warpName));
						player.transferToWorld(Utils.getWarpWorldUUID(warpName), position);
						src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported to Warp " + warpName));
						return CommandResult.success();
					}
					else
					{
						Location<World> warp = new Location<>(player.getWorld(), Utils.getWarpX(warpName), Utils.getWarpY(warpName), Utils.getWarpZ(warpName));
						player.setLocation(warp);
					}
				}
				else
				{
					src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You do not have permission to use this warp!"));
					return CommandResult.success();
				}
			}
			else
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Warp " + warpName + " does not exist!"));
			}
		}

		return CommandResult.success();
	}
}
