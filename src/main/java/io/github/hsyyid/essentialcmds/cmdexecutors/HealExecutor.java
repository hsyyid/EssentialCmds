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

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.source.CommandBlockSource;
import org.spongepowered.api.util.command.source.ConsoleSource;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import java.util.Optional;

public class HealExecutor implements CommandExecutor
{

	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Player> p = ctx.<Player> getOne("player");

		if (src instanceof Player)
		{
			Player player = (Player) src;

			if (player.hasPermission("heal.others") && p.isPresent())
			{
				Player recipient = p.get();
				recipient.offer(Keys.HEALTH, player.get(Keys.MAX_HEALTH).get());
				recipient.sendMessage(Texts.of(TextColors.GREEN, "Success: ", TextColors.YELLOW, "You've been healed by " + player.getName()));
				src.sendMessage(Texts.of(TextColors.GREEN, "Success: ", TextColors.YELLOW, "You've healed " + recipient.getName()));
			}
			else if (p.isPresent())
			{
				player.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You do not have permission to heal other players!"));
			}
			else
			{
				player.offer(Keys.HEALTH, player.get(Keys.MAX_HEALTH).get());
				src.sendMessage(Texts.of(TextColors.GREEN, "Success: ", TextColors.YELLOW, "You've been healed."));
			}
		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /heal!"));
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /heal!"));
		}

		return CommandResult.success();
	}
}
