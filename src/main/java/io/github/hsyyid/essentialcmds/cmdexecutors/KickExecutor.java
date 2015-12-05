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
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.TextMessageException;

public class KickExecutor implements CommandExecutor
{

	@SuppressWarnings("deprecation")
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Game game = EssentialCmds.getEssentialCmds().getGame();
		Server server = game.getServer();
		Player player = ctx.<Player> getOne("player").get();
		String reason = ctx.<String> getOne("reason").get();

		if (server.getPlayer(player.getUniqueId()).isPresent())
		{
			try
			{
				Text reas = Texts.legacy('&').from(reason);
				Text kickMessage = Texts.of(TextColors.GOLD, src.getName() + " kicked " + player.getName() + " for ", TextColors.RED);
				Text finalKickMessage = Texts.builder().append(kickMessage).append(reas).build();

				for (Player p : server.getOnlinePlayers())
				{
					p.sendMessage(finalKickMessage);
				}

				player.kick(reas);
			}
			catch (TextMessageException e)
			{
				Text kickMessage = Texts.of(TextColors.GOLD, src.getName() + " kicked " + player.getName() + " for ", TextColors.RED);
				Text finalKickMessage = Texts.builder().append(kickMessage).append(Texts.of(reason)).build();

				for (Player p : server.getOnlinePlayers())
				{
					p.sendMessage(finalKickMessage);
				}

				player.kick(Texts.of(reason));
			}

			src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Player kicked."));
		}
		else
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Player doesn't appear to be online!"));
		}

		return CommandResult.success();
	}
}
