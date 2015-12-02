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

import io.github.hsyyid.essentialcmds.utils.Mail;
import io.github.hsyyid.essentialcmds.utils.PaginatedList;
import io.github.hsyyid.essentialcmds.utils.Utils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextBuilder;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.source.CommandBlockSource;
import org.spongepowered.api.util.command.source.ConsoleSource;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class MailListExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		if(src instanceof Player)
		{
			Player player = (Player) src;
			ArrayList<Mail> mail = Utils.getMail();

			ArrayList<Mail> myMail = (ArrayList<Mail>) mail.stream().filter(m -> m.getRecipientName().equals(player.getName())).collect(Collectors.toList());

			if(myMail.isEmpty())
			{
				player.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You have no new mail!"));
				return CommandResult.success();
			}

			Optional<Integer> arguments = ctx.<Integer>getOne("page no");
			int pgNo;

			if (arguments.isPresent())
			{
				pgNo = arguments.get();
			} else
			{
				pgNo = 1;
			}

			//Add List
			PaginatedList pList = new PaginatedList("/listmail");
			for (Mail newM : myMail)
			{
				String name = "New mail from " + newM.getSenderName();

				Text item = Texts.builder(name)
						.onClick(TextActions.runCommand("/readmail " + (myMail.indexOf(newM))))
						.onHover(TextActions.showText(Texts.of(TextColors.WHITE, "Read mail from ", TextColors.GOLD, newM.getSenderName())))
						.color(TextColors.DARK_AQUA)
						.style(TextStyles.UNDERLINE)
						.build();

				pList.add(item);
			}
			pList.setItemsPerPage(10);
			//Header
			TextBuilder header = Texts.builder();
			header.append(Texts.of(TextColors.GREEN, "------------"));
			header.append(Texts.of(TextColors.GREEN, " Showing Mail page " + pgNo + " of " + pList.getTotalPages() + " "));
			header.append(Texts.of(TextColors.GREEN, "------------"));

			pList.setHeader(header.build());
			//Send List
			src.sendMessage(pList.getPage(pgNo));
		} else if(src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /maillist!"));
		} else if(src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /mailist!"));
		}

		return CommandResult.success();
	}
}