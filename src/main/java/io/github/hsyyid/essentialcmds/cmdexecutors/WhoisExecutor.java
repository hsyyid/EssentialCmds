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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.option.OptionSubject;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import io.github.hsyyid.essentialcmds.internal.AsyncCommandExecutorBase;

public class WhoisExecutor extends AsyncCommandExecutorBase
{
	@Override
	public void executeAsync(CommandSource src, CommandContext ctx)
	{
		Optional<Player> optPlayer = ctx.<Player> getOne("player");
		Optional<String> optPlayerName = ctx.<String> getOne("player name");

		if (optPlayer.isPresent())
		{
			Player player = optPlayer.get();
			src.sendMessage(Text.of(TextColors.GOLD, "Real Name: ", TextColors.GRAY, player.getName()));
			src.sendMessage(Text.of(TextColors.GOLD, "UUID: ", TextColors.GRAY, player.getUniqueId().toString()));

			if (Sponge.getServer().getPlayer(player.getUniqueId()).isPresent())
				src.sendMessage(Text.of(TextColors.GOLD, "Current Ontime: ", TextColors.GRAY, getCurrentOnTime(player)));
			else
				src.sendMessage(Text.of(TextColors.GOLD, "Last Time Online: ", TextColors.GRAY, player.getJoinData().lastPlayed().get().toString()));

			src.sendMessage(Text.of(TextColors.GOLD, "Current World: ", TextColors.GRAY, player.getWorld().getName()));
		}
		else if (optPlayerName.isPresent())
		{
			Optional<Player> optionalPlayer = Sponge.getServer().getPlayer(optPlayerName.get());

			if (optionalPlayer.isPresent())
			{
				Player player = optionalPlayer.get();
				src.sendMessage(Text.of(TextColors.GOLD, "Real Name: ", TextColors.GRAY, player.getName()));
				src.sendMessage(Text.of(TextColors.GOLD, "UUID: ", TextColors.GRAY, player.getUniqueId().toString()));

				if (Sponge.getServer().getPlayer(player.getUniqueId()).isPresent())
					src.sendMessage(Text.of(TextColors.GOLD, "Current Ontime: ", TextColors.GRAY, getCurrentOnTime(player)));
				else
					src.sendMessage(Text.of(TextColors.GOLD, "Last Time Online: ", TextColors.GRAY, player.getJoinData().lastPlayed().get().toString()));

				src.sendMessage(Text.of(TextColors.GOLD, "Current World: ", TextColors.GRAY, player.getWorld().getName()));
			}
			else
			{
				Player foundPlayer = null;

				for (Player player : Sponge.getServer().getOnlinePlayers())
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
				}

				if (foundPlayer == null)
				{
					src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Player not found."));
				}
				else
				{
					src.sendMessage(Text.of(TextColors.GOLD, "Real Name: ", TextColors.GRAY, foundPlayer.getName()));
					src.sendMessage(Text.of(TextColors.GOLD, "UUID: ", TextColors.GRAY, foundPlayer.getUniqueId().toString()));

					if (Sponge.getServer().getPlayer(foundPlayer.getUniqueId()).isPresent())
					{
						src.sendMessage(Text.of(TextColors.GOLD, "Current Ontime: ", TextColors.GRAY, getCurrentOnTime(foundPlayer)));
					}
					else
					{
						src.sendMessage(Text.of(TextColors.GOLD, "Last Time Online: ", TextColors.GRAY, foundPlayer.getJoinData().lastPlayed().get().toString()));
					}

					src.sendMessage(Text.of(TextColors.GOLD, "Current World: ", TextColors.GRAY, foundPlayer.getWorld().getName()));
				}
			}
		}
		else
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You did not specify any arguments."));
		}
	}

	public String getCurrentOnTime(Player player)
	{
		Instant currentTime = Instant.now();
		Instant lastJoined = player.getJoinData().lastPlayed().get();

		long diffInHours = lastJoined.until(currentTime, ChronoUnit.HOURS);
		long diffInMinutes = lastJoined.until(currentTime, ChronoUnit.MINUTES);
		long diffInSeconds = lastJoined.until(currentTime, ChronoUnit.SECONDS);

		return ("In Hours: | " + diffInHours + " | In Minutes: | " + diffInMinutes + " | In Seconds: | " + diffInSeconds + " |");
	}

	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "whois", "realname", "seen" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder().description(Text.of("WhoIs Command")).permission("essentialcmds.whois.use").arguments(GenericArguments.firstParsing(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))), GenericArguments.onlyOne(GenericArguments.string(Text.of("player name"))))).executor(this).build();
	}
}
