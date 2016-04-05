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

import io.github.hsyyid.essentialcmds.internal.AsyncCommandExecutorBase;
import io.github.hsyyid.essentialcmds.utils.Utils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

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
				src.sendMessage(Text.of(TextColors.GOLD, "Current Ontime: ", TextColors.GRAY, getCurrentOnTime(player.getUniqueId())));
			else
				src.sendMessage(Text.of(TextColors.GOLD, "Last Time Online: ", TextColors.GRAY, Utils.getLastTimePlayerJoined(player.getUniqueId())));
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
					src.sendMessage(Text.of(TextColors.GOLD, "Current Ontime: ", TextColors.GRAY, getCurrentOnTime(player.getUniqueId())));
				else
					src.sendMessage(Text.of(TextColors.GOLD, "Last Time Online: ", TextColors.GRAY, Utils.getLastTimePlayerJoined(player.getUniqueId())));
				src.sendMessage(Text.of(TextColors.GOLD, "Current World: ", TextColors.GRAY, player.getWorld().getName()));
			}
			else
			{
				Optional<Player> foundPlayer = Sponge.getServer().getOnlinePlayers().stream().filter(p -> p.get(Keys.DISPLAY_NAME).isPresent() && p.get(Keys.DISPLAY_NAME).get().toPlain().equalsIgnoreCase(optPlayerName.get())).findAny();

				if (!foundPlayer.isPresent())
				{
					src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Player not found."));
				}
				else
				{
					src.sendMessage(Text.of(TextColors.GOLD, "Real Name: ", TextColors.GRAY, foundPlayer.get().getName()));
					src.sendMessage(Text.of(TextColors.GOLD, "UUID: ", TextColors.GRAY, foundPlayer.get().getUniqueId().toString()));
					src.sendMessage(Text.of(TextColors.GOLD, "Current Ontime: ", TextColors.GRAY, getCurrentOnTime(foundPlayer.get().getUniqueId())));
					src.sendMessage(Text.of(TextColors.GOLD, "Current World: ", TextColors.GRAY, foundPlayer.get().getWorld().getName()));
				}
			}
		}
		else
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You did not specify any arguments."));
		}
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
		return CommandSpec.builder().description(Text.of("WhoIs Command")).permission("essentialcmds.whois.use")
			.arguments(GenericArguments.firstParsing(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))), GenericArguments.onlyOne(GenericArguments.string(Text.of("player name")))))
			.executor(this).build();
	}
}
