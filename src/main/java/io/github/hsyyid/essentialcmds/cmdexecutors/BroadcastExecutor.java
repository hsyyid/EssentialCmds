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

import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.TextMessageException;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class BroadcastExecutor implements CommandExecutor
{
	@SuppressWarnings("deprecation")
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		String message = ctx.<String> getOne("message").get();
		Game game = EssentialCmds.getEssentialCmds().getGame();
		Server server = game.getServer();

		try
		{
			Text msg = Texts.legacy('&').from(message);
			Text broadcast = Texts.of(TextColors.DARK_GRAY, "[", TextColors.DARK_RED, "Broadcast", TextColors.DARK_GRAY, "]", TextColors.GREEN, " ");
			Text finalBroadcast = Texts.builder().append(broadcast).append(msg).build();

			for (Player player : server.getOnlinePlayers())
			{
				player.sendMessage(finalBroadcast);
			}

			server.getConsole().sendMessage(finalBroadcast);
		}
		catch (TextMessageException e)
		{
			for (Player player : server.getOnlinePlayers())
			{
				player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.DARK_RED, "Broadcast", TextColors.DARK_GRAY, "]", TextColors.GREEN, " " + message));
			}

			server.getConsole().sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.DARK_RED, "Broadcast", TextColors.DARK_GRAY, "]", TextColors.GREEN, " " + message));
		}

		return CommandResult.success();
	}
}
