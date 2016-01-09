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

import io.github.hsyyid.essentialcmds.internal.CommandExecutorBase;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nonnull;
import java.util.Optional;

public class IgniteExecutor extends CommandExecutorBase
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Integer ticks = ctx.<Integer> getOne("ticks").get();
		Optional<Player> target = ctx.<Player> getOne("player");

		if (!target.isPresent())
		{
			if (src instanceof Player)
			{
				Player player = (Player) src;
				player.offer(Keys.FIRE_TICKS, ticks);
				player.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "You are now on fire."));
			}
			else if (src instanceof ConsoleSource)
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /ignite!"));
			}
			else if (src instanceof CommandBlockSource)
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /ignite!"));
			}
		}
		else if (target.isPresent() && src.hasPermission("ignite.others"))
		{
			Player player = target.get();
			player.offer(Keys.FIRE_TICKS, ticks);
			src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Set player on fire."));
			player.sendMessage(Text.of(TextColors.GOLD, src.getName(), TextColors.RED, " has set you on fire."));
		}

		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "ignite", "burn", "fire" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder().description(Text.of("Ignite Command")).permission("essentialcmds.ignite.use")
				.arguments(GenericArguments.seq(
						GenericArguments.onlyOne(GenericArguments.integer(Text.of("ticks"))),
						GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))))))
				.executor(this).build();
	}
}
