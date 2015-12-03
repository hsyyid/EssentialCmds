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
import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.option.OptionSubject;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static io.github.hsyyid.essentialcmds.EssentialCmds.getEssentialCmds;

public class WhoisExecutor implements CommandExecutor
{
	private static Game game = getEssentialCmds().getGame();

	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Player> optPlayer = ctx.<Player> getOne("player");
		Optional<String> optPlayerName = ctx.<String> getOne("player name");

		if (optPlayer.isPresent())
		{
			Player player = optPlayer.get();
			src.sendMessage(Texts.of(TextColors.GOLD, "Real Name: ", TextColors.GRAY, player.getName()));
			src.sendMessage(Texts.of(TextColors.GOLD, "UUID: ", TextColors.GRAY, player.getUniqueId().toString()));
			if (game.getServer().getPlayer(player.getUniqueId()).isPresent())
				src.sendMessage(Texts.of(TextColors.GOLD, "Current Ontime: ", TextColors.GRAY, getCurrentOnTime(player.getUniqueId())));
			else
				src.sendMessage(Texts.of(TextColors.GOLD, "Last Time Online: ", TextColors.GRAY, Utils.getLastTimePlayerJoined(player.getUniqueId())));
			src.sendMessage(Texts.of(TextColors.GOLD, "Current World: ", TextColors.GRAY, player.getWorld().getName()));
		}
		else if (optPlayerName.isPresent())
		{
			Optional<Player> optionalPlayer = game.getServer().getPlayer(optPlayerName.get());

			if (optionalPlayer.isPresent())
			{
				Player player = optionalPlayer.get();
				src.sendMessage(Texts.of(TextColors.GOLD, "Real Name: ", TextColors.GRAY, player.getName()));
				src.sendMessage(Texts.of(TextColors.GOLD, "UUID: ", TextColors.GRAY, player.getUniqueId().toString()));
				if (game.getServer().getPlayer(player.getUniqueId()).isPresent())
					src.sendMessage(Texts.of(TextColors.GOLD, "Current Ontime: ", TextColors.GRAY, getCurrentOnTime(player.getUniqueId())));
				else
					src.sendMessage(Texts.of(TextColors.GOLD, "Last Time Online: ", TextColors.GRAY, Utils.getLastTimePlayerJoined(player.getUniqueId())));
				src.sendMessage(Texts.of(TextColors.GOLD, "Current World: ", TextColors.GRAY, player.getWorld().getName()));
			}
			else
			{
				Player foundPlayer = null;

				for (Player player : game.getServer().getOnlinePlayers())
				{
					Subject subject = player.getContainingCollection().get(player.getIdentifier());

					if (subject instanceof OptionSubject)
					{
						OptionSubject optionSubject = (OptionSubject) subject;
						
						if (optionSubject.getOption("nick").isPresent())
						{
							if (optionSubject.getOption("nick").get().equals(optPlayerName.get()))
							{
								foundPlayer = player;
								break;
							}
						}
					}
					else
					{
						continue;
					}
				}

				if (foundPlayer == null)
				{
					src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Player not found."));
					return CommandResult.success();
				}
				else
				{
					Player player = foundPlayer;
					src.sendMessage(Texts.of(TextColors.GOLD, "Real Name: ", TextColors.GRAY, player.getName()));
					src.sendMessage(Texts.of(TextColors.GOLD, "UUID: ", TextColors.GRAY, player.getUniqueId().toString()));
					if (game.getServer().getPlayer(player.getUniqueId()).isPresent())
						src.sendMessage(Texts.of(TextColors.GOLD, "Current Ontime: ", TextColors.GRAY, getCurrentOnTime(player.getUniqueId())));
					else
						src.sendMessage(Texts.of(TextColors.GOLD, "Last Time Online: ", TextColors.GRAY, Utils.getLastTimePlayerJoined(player.getUniqueId())));
					src.sendMessage(Texts.of(TextColors.GOLD, "Current World: ", TextColors.GRAY, player.getWorld().getName()));
				}
			}
		}
		else
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You did not specify any arguments."));
		}

		return CommandResult.success();
	}

	public String getCurrentOnTime(UUID uuid)
	{
		String timeJoined = Utils.getLastTimePlayerJoined(uuid);

		if (!timeJoined.equals(""))
		{
			try
			{
				SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
				format.setTimeZone(TimeZone.getTimeZone("GMT"));
				Date date = format.parse(timeJoined);
				Calendar cal = Calendar.getInstance();
				Date currentDate = cal.getTime();
				long duration = currentDate.getTime() - date.getTime();
				long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
				long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
				long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);

				return ("In Hours: | " + diffInHours + " | In Minutes: | " + diffInMinutes + " | In Seconds: | " + diffInSeconds + " |");
			}
			catch (ParseException e)
			{
				return "NaN";
			}
		}
		else
		{
			return "NaN";
		}
	}
}
