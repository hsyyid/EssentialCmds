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

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.entity.GameModeData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Optional;

public class GamemodeExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		String gamemode = ctx.<String> getOne("gamemode").get();
		Optional<Player> optionalPlayer = ctx.<Player> getOne("player");

		if (src instanceof Player)
		{
			Player player = (Player) src;

			if (optionalPlayer.isPresent())
			{
				try
				{
					Player targetPlayer = optionalPlayer.get();
					if (gamemode.equals("creative") || gamemode.equals("c") || Integer.parseInt(gamemode) == 1)
					{
						GameModeData data = targetPlayer.getGameModeData().set(Keys.GAME_MODE, GameModes.CREATIVE);
						player.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Successfully changed " + targetPlayer.getName() + "'s gamemode."));
						targetPlayer.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Set your gamemode to creative"));
						targetPlayer.offer(data);
						return CommandResult.success();
					}
					else if (gamemode.equals("survival") || gamemode.equals("s") || Integer.parseInt(gamemode) == 0)
					{
						GameModeData data = targetPlayer.getGameModeData().set(Keys.GAME_MODE, GameModes.SURVIVAL);
						targetPlayer.offer(data);
						player.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Successfully changed " + targetPlayer.getName() + "'s gamemode."));
						targetPlayer.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Set your gamemode to survival"));
						return CommandResult.success();
					}
					else if (gamemode.equals("adventure") || gamemode.equals("a") || Integer.parseInt(gamemode) == 2)
					{
						GameModeData data = targetPlayer.getGameModeData().set(Keys.GAME_MODE, GameModes.ADVENTURE);
						targetPlayer.offer(data);
						player.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Successfully changed " + targetPlayer.getName() + "'s gamemode."));
						targetPlayer.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Set your gamemode to adventure"));
						return CommandResult.success();
					}
					else if (gamemode.equals("spectator") || gamemode.equals("spec") || Integer.parseInt(gamemode) == 3)
					{
						GameModeData data = targetPlayer.getGameModeData().set(Keys.GAME_MODE, GameModes.SPECTATOR);
						targetPlayer.offer(data);
						player.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Successfully changed " + targetPlayer.getName() + "'s gamemode."));
						targetPlayer.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Set your gamemode to spectator"));
						return CommandResult.success();
					}
					else
					{
						player.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, gamemode + " does not appear to be a gamemode!"));
					}
				}
				catch (NumberFormatException e)
				{
					src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, gamemode + " does not appear to be a gamemode!"));
				}
			}
			else
			{
				if (gamemode.equals("creative") || gamemode.equals("c") || Integer.parseInt(gamemode) == 1)
				{
					GameModeData data = player.getGameModeData().set(Keys.GAME_MODE, GameModes.CREATIVE);
					player.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Set your gamemode to creative"));
					player.offer(data);
					return CommandResult.success();
				}
				else if (gamemode.equals("survival") || gamemode.equals("s") || Integer.parseInt(gamemode) == 0)
				{
					GameModeData data = player.getGameModeData().set(Keys.GAME_MODE, GameModes.SURVIVAL);
					player.offer(data);
					player.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Set your gamemode to survival"));
					return CommandResult.success();
				}
				else if (gamemode.equals("adventure") || gamemode.equals("a") || Integer.parseInt(gamemode) == 2)
				{
					GameModeData data = player.getGameModeData().set(Keys.GAME_MODE, GameModes.ADVENTURE);
					player.offer(data);
					player.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Set your gamemode to adventure"));
					return CommandResult.success();
				}
				else if (gamemode.equals("spectator") || gamemode.equals("spec") || Integer.parseInt(gamemode) == 3)
				{
					GameModeData data = player.getGameModeData().set(Keys.GAME_MODE, GameModes.SPECTATOR);
					player.offer(data);
					player.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Set your gamemode to spectator"));
					return CommandResult.success();
				}
				else
				{
					player.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, gamemode + " does not appear to be a gamemode!"));
				}
			}
		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /gamemode!"));
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /gamemode!"));
		}

		return CommandResult.success();
	}
}
