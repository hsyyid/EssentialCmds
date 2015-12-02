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

import io.github.hsyyid.essentialcmds.EssentialCmds;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;

public class TeleportWorldExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Player> optPlayer = ctx.<Player> getOne("player");
		String name = ctx.<String> getOne("name").get();

		if (!optPlayer.isPresent())
		{
			if (src instanceof Player)
			{
				Player player = (Player) src;

				Optional<World> optWorld = EssentialCmds.game.getServer().getWorld(name);

				if (optWorld.isPresent())
				{
					Location<World> spawnLocation = optWorld.get().getSpawnLocation();
					player.transferToWorld(name, spawnLocation.getPosition());
					src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported to world."));
				}
				else
				{
					src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "World specified does not exist!"));
				}
			}
			else
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You cannot teleport, you are not a player!"));
			}
		}
		else
		{
			Player player = optPlayer.get();

			Optional<World> optWorld = EssentialCmds.game.getServer().getWorld(name);

			if (optWorld.isPresent())
			{
				Location<World> spawnLocation = optWorld.get().getSpawnLocation();
				player.transferToWorld(name, spawnLocation.getPosition());
				src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported player to world."));
				player.sendMessage(Texts.of(TextColors.GOLD, src.getName(), TextColors.GRAY, " has teleported you to this world."));
			}
			else
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "World specified does not exist!"));
			}
		}

		return CommandResult.success();
	}
}
