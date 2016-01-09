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
import io.github.hsyyid.essentialcmds.internal.AsyncCommandExecutorBase;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nonnull;

/* (non-Javadoc)
 *
 * This is async as this is only adding/removing a player from a list in EssentialCmds. This does not touch the
 * SpongeAPI itself.
 */
public class PlayerFreezeExecutor extends AsyncCommandExecutorBase
{
	@Override
	public void executeAsync(CommandSource src, CommandContext ctx)
	{
		Player targetPlayer = ctx.<Player> getOne("player").get();
		
		if(targetPlayer.equals(src))
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You cannot freeze yourself!"));
			return;
		}
		
		if(EssentialCmds.frozenPlayers.contains(targetPlayer.getUniqueId()))
		{
			EssentialCmds.frozenPlayers.remove(targetPlayer.getUniqueId());
			src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Un-froze player."));
		}
		else
		{
			EssentialCmds.frozenPlayers.add(targetPlayer.getUniqueId());
			src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Froze player."));
		}
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "playerfreeze", "freezeplayer" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder().description(Text.of("Player Freeze Command")).permission("essentialcmds.playerfreeze.use")
				.arguments(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))).executor(this).build();
	}
}
