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
import io.github.hsyyid.essentialcmds.utils.Message;
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

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MessageExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Player recipient = ctx.<Player> getOne("recipient").get();
		String message = ctx.<String> getOne("message").get();

		ArrayList<Player> socialSpies = (ArrayList<Player>) EssentialCmds.getEssentialCmds().getGame().getServer().getOnlinePlayers().stream().filter(player -> EssentialCmds.socialSpies.contains(player.getUniqueId())).collect(Collectors.toList());

		if (recipient.equals(src))
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You cannot send a private message to yourself!"));
			return CommandResult.success();
		}

		if (src instanceof Player)
		{
			Player player = (Player) src;
			src.sendMessage(Texts.of(TextColors.GOLD, "[", TextColors.RED, player.getName(), TextColors.GOLD, " > ", TextColors.RED, recipient.getName(), TextColors.GOLD, "]: ", TextColors.GRAY, message));
			recipient.sendMessage(Texts.of(TextColors.GOLD, "[", TextColors.RED, player.getName(), TextColors.GOLD, " > ", TextColors.RED, recipient.getName(), TextColors.GOLD, "]: ", TextColors.GRAY, message));

			Message messageToRemove = null;

			for (Message m : EssentialCmds.recentlyMessaged)
			{
				if (m.getRecipient().getUniqueId().equals(recipient.getUniqueId()))
				{
					messageToRemove = m;
					break;
				}
			}

			if (messageToRemove != null)
			{
				EssentialCmds.recentlyMessaged.remove(messageToRemove);
			}

			Message msg = new Message(player, recipient, message);
			EssentialCmds.recentlyMessaged.add(msg);

			for (Player socialspy : socialSpies)
			{
				socialspy.sendMessage(Texts.of(TextColors.GOLD, "[", TextColors.RED, player.getName(), TextColors.GOLD, " > ", TextColors.RED, recipient.getName(), TextColors.GOLD, "]: ", TextColors.GRAY, message));
			}
		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.RED, "Sent message to " + recipient.getName()));
			recipient.sendMessage(Texts.of(TextColors.GOLD, "[", TextColors.RED, "Console", TextColors.GOLD, " > ", TextColors.RED, recipient.getName(), TextColors.GOLD, "]: ", TextColors.GRAY, message));

			for (Player socialspy : socialSpies)
			{
				socialspy.sendMessage(Texts.of(TextColors.GOLD, "[", TextColors.RED, "Console", TextColors.GOLD, " > ", TextColors.RED, recipient.getName(), TextColors.GOLD, "]: ", TextColors.GRAY, message));
			}
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.RED, "Sent message to " + recipient.getName()));
			recipient.sendMessage(Texts.of(TextColors.GOLD, "[", TextColors.RED, "CommandBlock", TextColors.GOLD, " > ", TextColors.RED, recipient.getName(), TextColors.GOLD, "]: ", TextColors.GRAY, message));

			for (Player socialspy : socialSpies)
			{
				socialspy.sendMessage(Texts.of(TextColors.GOLD, "[", TextColors.RED, "CommandBlock", TextColors.GOLD, " > ", TextColors.RED, recipient.getName(), TextColors.GOLD, "]: ", TextColors.GRAY, message));
			}
		}

		return CommandResult.success();
	}
}
