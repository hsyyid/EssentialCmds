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

import io.github.hsyyid.essentialcmds.utils.PaginatedList;
import io.github.hsyyid.essentialcmds.utils.Utils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextBuilder;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.List;
import java.util.Optional;

public class BlacklistListExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Integer> arguments = ctx.<Integer> getOne("page no");
		
		if (src instanceof Player)
		{
			Player player = (Player) src;
			List<String> blacklistItems = Utils.getBlacklistItems();
			
			if(blacklistItems.size() == 0)
			{
				player.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "There are no blacklisted items!"));
				return CommandResult.success();
			}

			int pgNo = arguments.orElse(1);
			
			PaginatedList pList = new PaginatedList("/blacklist list");
			
			for (String name : blacklistItems)
			{
				Text item = Texts.builder(name)
					.color(TextColors.DARK_AQUA)
					.style(TextStyles.UNDERLINE)
					.build();

				pList.add(item);
			}
			
			pList.setItemsPerPage(10);
			
			TextBuilder header = Texts.builder();
			header.append(Texts.of(TextColors.GREEN, "------------"));
			header.append(Texts.of(TextColors.GREEN, " Showing Blacklist page " + pgNo + " of " + pList.getTotalPages() + " "));
			header.append(Texts.of(TextColors.GREEN, "------------"));
			
			pList.setHeader(header.build());
			
			if(pgNo > pList.getTotalPages())
				pgNo = 1;
				
			src.sendMessage(pList.getPage(pgNo));
		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /blacklist list!"));
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /blacklist list!"));
		}

		return CommandResult.success();
	}
}
