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
import io.github.hsyyid.essentialcmds.events.TPAAcceptEvent;
import io.github.hsyyid.essentialcmds.events.TPAHereAcceptEvent;
import io.github.hsyyid.essentialcmds.internal.CommandExecutorBase;
import io.github.hsyyid.essentialcmds.utils.PendingInvitation;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nonnull;

import static io.github.hsyyid.essentialcmds.EssentialCmds.getEssentialCmds;

public class TPAAcceptExecutor extends CommandExecutorBase
{
	private Game game = getEssentialCmds().getGame();

	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		if (src instanceof Player)
		{
			Player player = (Player) src;
			Player sender = null;
			boolean tpaHere = false;
			PendingInvitation foundInvitation = null;

			for (PendingInvitation invitation : EssentialCmds.pendingInvites)
			{
				if (!invitation.isTPAHere && invitation.recipient == player)
				{
					sender = invitation.sender;
					foundInvitation = invitation;
					break;
				}
				else if (invitation.isTPAHere && invitation.recipient == player)
				{
					tpaHere = true;
					sender = invitation.sender;
					foundInvitation = invitation;
					break;
				}
			}

			if (sender != null && !tpaHere)
			{
				game.getEventManager().post(new TPAAcceptEvent(player, sender));
				src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.WHITE, "TPA Request Accepted."));
			}
			else if (sender != null)
			{
				game.getEventManager().post(new TPAHereAcceptEvent(player, sender));
				src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.WHITE, "TPA Here Request Accepted."));
			}
			else
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Pending TPA request not found!"));
			}

			if (foundInvitation != null)
				EssentialCmds.pendingInvites.remove(foundInvitation);
		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /tpaccept!"));
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /tpaccept!"));
		}
		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "tpaaccept", "tpaccept", "tpyes" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder().description(Text.of("TPA Accept Command")).permission("essentialcmds.tpa.accept").executor(this).build();
	}
}
