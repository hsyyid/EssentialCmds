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
import io.github.hsyyid.essentialcmds.cmdexecutors.argumentparsers.UserParser;
import io.github.hsyyid.essentialcmds.internal.CommandExecutorBase;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.ban.BanService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.util.ban.Ban;
import org.spongepowered.api.util.ban.BanTypes;

import java.time.Instant;

import javax.annotation.Nonnull;

public class TempBanExecutor extends CommandExecutorBase
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Game game = EssentialCmds.getEssentialCmds().getGame();
		User player = ctx.<User> getOne("player").get();
		String time = ctx.<String> getOne("time").get();
		String reason = ctx.<String> getOne("reason").orElse("The BanHammer has spoken!");

		BanService srv = game.getServiceManager().provide(BanService.class).get();

		if (srv.isBanned(player.getProfile()))
		{
			src.sendMessage(Text.of(TextColors.RED, "That player has already been banned."));
			return CommandResult.empty();
		}

		srv.addBan(Ban.builder()
			.type(BanTypes.PROFILE)
			.source(src).profile(player.getProfile())
			.expirationDate(getInstantFromString(time))
			.reason(TextSerializers.formattingCode('&').deserialize(reason))
			.build());

		if (player.isOnline())
		{
			player.getPlayer().get().kick(Text.builder()
				.append(Text.of(TextColors.DARK_RED, "You have been tempbanned!\n", TextColors.RED, "Reason: "))
				.append(TextSerializers.formattingCode('&').deserialize(reason), Text.of("\n"))
				.append(Text.of(TextColors.GOLD, "Time: ", TextColors.GRAY, getFormattedString(time)))
				.build());
		}

		src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, player.getName() + " has been banned."));
		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "tempban" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec
			.builder()
			.description(Text.of("TempBan Command"))
			.permission("essentialcmds.tempban.use")
			.arguments(GenericArguments.seq(GenericArguments.onlyOne(new UserParser(Text.of("player"))), GenericArguments.onlyOne(GenericArguments.string(Text.of("time"))), GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Text.of("reason"))))))
			.executor(this)
			.build();
	}

	public Instant getInstantFromString(String time)
	{
		if (time.contains(":"))
		{
			String[] tokens = time.split(":");
			int hours = Integer.parseInt(tokens[0]);
			int minutes = Integer.parseInt(tokens[1]);
			int seconds = Integer.parseInt(tokens[2]);
			int durationInSec = 3600 * hours + 60 * minutes + seconds;
			return Instant.ofEpochSecond((System.currentTimeMillis()) / 1000L + durationInSec);
		}
		else
		{
			return Instant.ofEpochSecond((System.currentTimeMillis()) / 1000L + Integer.parseInt(time));
		}
	}
	
	public String getFormattedString(String time)
	{
		if (time.contains(":"))
		{
			String[] tokens = time.split(":");
			int hours = Integer.parseInt(tokens[0]);
			int minutes = Integer.parseInt(tokens[1]);
			int seconds = Integer.parseInt(tokens[2]);
			return (hours + " hours, " + minutes + " minutes, and " + seconds + " seconds.");
		}
		else
		{
			return (Integer.parseInt(time) + " seconds.");
		}
	}
}
