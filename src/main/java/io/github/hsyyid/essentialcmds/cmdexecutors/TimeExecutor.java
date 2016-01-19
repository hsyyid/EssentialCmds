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
import org.spongepowered.api.block.tileentity.CommandBlock;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Optional;

import javax.annotation.Nonnull;

public class TimeExecutor extends CommandExecutorBase
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<String> timeString = ctx.<String> getOne("time");
		Optional<Integer> timeTicks = ctx.<Integer> getOne("ticks");

		if (src instanceof Player)
		{
			Player player = (Player) src;

			if (timeTicks.isPresent())
			{
				player.getWorld().getProperties().setWorldTime(timeTicks.get());
				player.sendMessage(Text.of(TextColors.GOLD, "Set time to ", TextColors.GRAY, timeTicks.get()));
			}
			else if (timeString.isPresent())
			{
				if (timeString.get().toLowerCase().equals("dawn") || timeString.get().toLowerCase().equals("morning"))
				{
					player.sendMessage(Text.of(TextColors.GOLD, "Set time to ", TextColors.GRAY, timeString.get().toLowerCase()));
					player.getWorld().getProperties().setWorldTime(0);
				}
				else if (timeString.get().toLowerCase().equals("day"))
				{
					player.sendMessage(Text.of(TextColors.GOLD, "Set time to ", TextColors.GRAY, timeString.get().toLowerCase()));
					player.getWorld().getProperties().setWorldTime(1000);
				}
				else if (timeString.get().toLowerCase().equals("afternoon") || timeString.get().toLowerCase().equals("noon"))
				{
					player.sendMessage(Text.of(TextColors.GOLD, "Set time to ", TextColors.GRAY, timeString.get().toLowerCase()));
					player.getWorld().getProperties().setWorldTime(6000);
				}
				else if (timeString.get().toLowerCase().equals("dusk"))
				{
					player.sendMessage(Text.of(TextColors.GOLD, "Set time to ", TextColors.GRAY, timeString.get().toLowerCase()));
					player.getWorld().getProperties().setWorldTime(12000);
				}
				else if (timeString.get().toLowerCase().equals("night"))
				{
					player.sendMessage(Text.of(TextColors.GOLD, "Set time to ", TextColors.GRAY, timeString.get().toLowerCase()));
					player.getWorld().getProperties().setWorldTime(14000);
				}
				else if (timeString.get().toLowerCase().equals("midnight"))
				{
					player.sendMessage(Text.of(TextColors.GOLD, "Set time to ", TextColors.GRAY, timeString.get().toLowerCase()));
					player.getWorld().getProperties().setWorldTime(18000);
				}
				else
				{
					player.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Could not understand input."));
				}
			}

			return CommandResult.success();
		}
		else if(src instanceof CommandBlock)
		{
			CommandBlock commandBlock = (CommandBlock) src;
			
			if (timeTicks.isPresent())
			{
				commandBlock.getWorld().getProperties().setWorldTime(timeTicks.get());
				commandBlock.sendMessage(Text.of(TextColors.GOLD, "Set time to ", TextColors.GRAY, timeTicks.get()));
			}
			else if (timeString.isPresent())
			{
				if (timeString.get().toLowerCase().equals("dawn") || timeString.get().toLowerCase().equals("morning"))
				{
					commandBlock.sendMessage(Text.of(TextColors.GOLD, "Set time to ", TextColors.GRAY, timeString.get().toLowerCase()));
					commandBlock.getWorld().getProperties().setWorldTime(0);
				}
				else if (timeString.get().toLowerCase().equals("day"))
				{
					commandBlock.sendMessage(Text.of(TextColors.GOLD, "Set time to ", TextColors.GRAY, timeString.get().toLowerCase()));
					commandBlock.getWorld().getProperties().setWorldTime(1000);
				}
				else if (timeString.get().toLowerCase().equals("afternoon") || timeString.get().toLowerCase().equals("noon"))
				{
					commandBlock.sendMessage(Text.of(TextColors.GOLD, "Set time to ", TextColors.GRAY, timeString.get().toLowerCase()));
					commandBlock.getWorld().getProperties().setWorldTime(6000);
				}
				else if (timeString.get().toLowerCase().equals("dusk"))
				{
					commandBlock.sendMessage(Text.of(TextColors.GOLD, "Set time to ", TextColors.GRAY, timeString.get().toLowerCase()));
					commandBlock.getWorld().getProperties().setWorldTime(12000);
				}
				else if (timeString.get().toLowerCase().equals("night"))
				{
					commandBlock.sendMessage(Text.of(TextColors.GOLD, "Set time to ", TextColors.GRAY, timeString.get().toLowerCase()));
					commandBlock.getWorld().getProperties().setWorldTime(14000);
				}
				else if (timeString.get().toLowerCase().equals("midnight"))
				{
					commandBlock.sendMessage(Text.of(TextColors.GOLD, "Set time to ", TextColors.GRAY, timeString.get().toLowerCase()));
					commandBlock.getWorld().getProperties().setWorldTime(18000);
				}
				else
				{
					commandBlock.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Could not understand input."));
				}
			}

			return CommandResult.success();
		}
		else
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be a in-game player to do /time!"));
			return CommandResult.success();
		}
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "time" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec
				.builder()
				.description(Text.of("Set Time Command"))
				.permission("essentialcmds.time.set")
				.arguments(
						GenericArguments.firstParsing(
								GenericArguments.integer(Text.of("ticks")),
								GenericArguments.string(Text.of("time"))))
				.executor(this).build();
	}
}
