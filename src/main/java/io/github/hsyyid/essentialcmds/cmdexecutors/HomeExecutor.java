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

import io.github.hsyyid.essentialcmds.utils.Utils;

import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.source.CommandBlockSource;
import org.spongepowered.api.util.command.source.ConsoleSource;
import org.spongepowered.api.util.command.spec.CommandExecutor;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Objects;

public class HomeExecutor implements CommandExecutor
{

	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		String homeName = ctx.<String> getOne("home name").get();
		if (src instanceof Player)
		{
			Player player = (Player) src;
			if (Utils.inConfig(player.getUniqueId(), homeName))
			{
				if (Objects.equals(player.getWorld().getName(), Utils.getHomeWorldName(player.getUniqueId(), homeName)))
				{
					Location<World> home = new Location<>(player.getWorld(), Utils.getX(player.getUniqueId(), homeName), Utils.getY(player.getUniqueId(), homeName), Utils.getZ(player.getUniqueId(), homeName));
					player.setLocation(home);
					src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported to Home " + homeName));
					return CommandResult.success();
				}
				else
				{
					Vector3d position = new Vector3d(Utils.getX(player.getUniqueId(), homeName), Utils.getY(player.getUniqueId(), homeName), Utils.getZ(player.getUniqueId(), homeName));
					player.transferToWorld(Utils.getHomeWorldName(player.getUniqueId(), homeName), position);
					src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported to Home " + homeName));
					return CommandResult.success();
				}
			}
			else
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must first set your home!"));
			}
		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /home!"));
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /home!"));
		}
		return CommandResult.success();
	}
}
