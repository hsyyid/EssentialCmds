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
import io.github.hsyyid.essentialcmds.internal.CommandExecutorBase;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.Optional;

import javax.annotation.Nonnull;

public class KickExecutor extends CommandExecutorBase
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Game game = EssentialCmds.getEssentialCmds().getGame();
		Server server = game.getServer();
		Player player = ctx.<Player> getOne("player").get();
		Optional<String> reason = ctx.<String> getOne("reason");

		if (server.getPlayer(player.getUniqueId()).isPresent())
		{
			Text finalKickMessage = Text.of(TextColors.GOLD, src.getName() + " kicked " + player.getName());

			if (reason.isPresent())
			{
				Text reas = TextSerializers.formattingCode('&').deserialize(reason.get());
				Text kickMessage = Text.of(TextColors.GOLD, src.getName() + " kicked " + player.getName() + " for ", TextColors.RED);
				finalKickMessage = Text.builder().append(kickMessage).append(reas).build();
				player.kick(reas);
			}
			else
			{
				player.kick();
			}

			MessageChannel.TO_ALL.send(finalKickMessage);
			src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Player kicked."));
		}
		else
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Player doesn't appear to be online!"));
		}

		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "kick" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec
			.builder()
			.description(Text.of("Kick Command"))
			.permission("essentialcmds.kick.use")
			.arguments(GenericArguments.seq(
				GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))), 
				GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Text.of("reason")))))
			.executor(this)
			.build();
	}
}
