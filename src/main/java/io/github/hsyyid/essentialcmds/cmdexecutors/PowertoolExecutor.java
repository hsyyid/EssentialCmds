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
import io.github.hsyyid.essentialcmds.utils.Powertool;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nonnull;
import java.util.Optional;

public class PowertoolExecutor extends CommandExecutorBase
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		if (src instanceof Player)
		{
			Optional<String> optionalCommand = ctx.<String> getOne("command");
			Player player = (Player) src;

			// TODO: Allow players not holding an item to remove their powertool
			if (player.getItemInHand(HandTypes.MAIN_HAND).isPresent())
			{
				if (optionalCommand.isPresent())
				{
					String command = optionalCommand.get();

					// If there is a powertool for the player, remove it.
					EssentialCmds.powertools.stream()
						.filter(p -> p.getPlayer().equals(player) && p.getItemID().equals(player.getItemInHand(HandTypes.MAIN_HAND).get().getItem().getName()))
						.findFirst().ifPresent(x -> EssentialCmds.powertools.remove(x));
					Powertool powertool = new Powertool(player, player.getItemInHand(HandTypes.MAIN_HAND).get().getItem().getName(), command);
					EssentialCmds.powertools.add(powertool);

					player.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Successfully bound command ", TextColors.BLUE, command, TextColors.YELLOW, " to ", TextColors.RED, player.getItemInHand(HandTypes.MAIN_HAND).get().getItem().getName(), TextColors.YELLOW, "!"));
				}
				else
				{
					Optional<Powertool> powertoolToRemove = EssentialCmds.powertools.stream()
						.filter(p -> p.getPlayer().equals(player) && p.getItemID().equals(player.getItemInHand(HandTypes.MAIN_HAND).get().getItem().getName()))
						.findFirst();

					if (powertoolToRemove.isPresent())
					{
						EssentialCmds.powertools.remove(powertoolToRemove.get());
						player.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Removed command from this powertool!"));
					}
					else
					{
						player.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "There is no command assigned to this!"));
					}
				}
			}
			else
			{
				player.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be holding something to use /powertool!"));
			}

		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /powertool!"));
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /powertool!"));
		}
		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "powertool", "pt" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder().description(Text.of("Powertool Command")).permission("essentialcmds.powertool.use")
			.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Text.of("command")))))
			.executor(this).build();
	}
}
