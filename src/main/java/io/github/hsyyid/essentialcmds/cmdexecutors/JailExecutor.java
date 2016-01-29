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
import io.github.hsyyid.essentialcmds.utils.Utils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nonnull;

public class JailExecutor extends CommandExecutorBase
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Player target = ctx.<Player> getOne("target").get();
		int jailNumber = ctx.<Integer> getOne("number").get();

		if (!EssentialCmds.jailedPlayers.contains(target.getUniqueId()))
		{
			if (Utils.getNumberOfJails() >= jailNumber)
			{
				EssentialCmds.jailedPlayers.add(target.getUniqueId());
				Utils.teleportPlayerToJail(target, jailNumber);
				src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Jailed " + target.getName() + "."));
			}
			else
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Jail does not exist."));
			}
		}
		else
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Player is already jailed!"));
		}

		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "jail" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("Jail Command"))
			.arguments(GenericArguments.seq(GenericArguments.player(Text.of("target")), GenericArguments.integer(Text.of("number"))))
			.children(getChildrenList(new AddExecutor(), new RemoveExecutor()))
			.permission("essentialcmds.jail.use")
			.executor(this)
			.build();
	}

	private static class RemoveExecutor extends CommandExecutorBase
	{

		@Nonnull
		@Override
		public String[] getAliases()
		{
			return new String[] { "remove" };
		}

		@Nonnull
		@Override
		public CommandSpec getSpec()
		{
			return CommandSpec.builder()
				.description(Text.of("Jail Remove Command"))
				.permission("essentialcmds.jail.remove.use")
				.arguments(GenericArguments.integer(Text.of("number")))
				.executor(this)
				.build();
		}

		@Override
		public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
		{
			int number = ctx.<Integer> getOne("number").get();
			boolean jailRemoved = Utils.removeJail(number);
			
			if (jailRemoved)
				src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Removed jail " + number + "."));
			else
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Jail " + number + "doesn't exist."));

			return CommandResult.success();
		}
	}

	private static class AddExecutor extends CommandExecutorBase
	{

		@Nonnull
		@Override
		public String[] getAliases()
		{
			return new String[] { "add" };
		}

		@Nonnull
		@Override
		public CommandSpec getSpec()
		{
			return CommandSpec.builder()
				.description(Text.of("Jail Add Command"))
				.permission("essentialcmds.jail.add.use")
				.executor(this)
				.build();
		}

		@Override
		public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
		{
			if (src instanceof Player)
			{
				Player player = (Player) src;
				Utils.addJail(player.getLocation());
				src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Set your location as a jail."));
			}
			else
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be a player to use this command."));
			}

			return CommandResult.success();
		}
	}
}
