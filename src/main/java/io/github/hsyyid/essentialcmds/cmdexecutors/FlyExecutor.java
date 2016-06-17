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
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Optional;

import javax.annotation.Nonnull;

public class FlyExecutor extends CommandExecutorBase
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Player> targetPlayer = ctx.<Player> getOne("player");

		if (!targetPlayer.isPresent())
		{
			if (src instanceof Player)
			{
				Player player = (Player) src;

				if (player.get(Keys.CAN_FLY).isPresent())
				{
					boolean canFly = player.get(Keys.CAN_FLY).get();
					player.offer(Keys.CAN_FLY, !canFly);

					if (canFly)
					{
						player.offer(Keys.IS_FLYING, !canFly);
						player.sendMessage(Text.of(TextColors.GOLD, "Toggled flying: ", TextColors.GRAY, "off."));

						if (EssentialCmds.flyingPlayers.contains(player.getUniqueId()))
						{
							EssentialCmds.flyingPlayers.remove(player.getUniqueId());
						}
					}
					else
					{
						player.sendMessage(Text.of(TextColors.GOLD, "Toggled flying: ", TextColors.GRAY, "on."));

						if (!EssentialCmds.flyingPlayers.contains(player.getUniqueId()))
						{
							EssentialCmds.flyingPlayers.add(player.getUniqueId());
						}
					}
				}
				else
				{
					player.offer(Keys.CAN_FLY, true);

					if (!EssentialCmds.flyingPlayers.contains(player.getUniqueId()))
					{
						EssentialCmds.flyingPlayers.add(player.getUniqueId());
					}
				}
			}
			else
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /fly!"));
			}
		}
		else if (src.hasPermission("essentialcmds.fly.others"))
		{
			Player player = targetPlayer.get();

			if (player.get(Keys.CAN_FLY).isPresent())
			{
				boolean canFly = player.get(Keys.CAN_FLY).get();
				player.offer(Keys.CAN_FLY, !canFly);

				if (canFly)
				{
					src.sendMessage(Text.of(TextColors.GOLD, "Toggled flying: ", TextColors.GRAY, "off."));
					player.sendMessage(Text.of(TextColors.GOLD, "Toggled flying: ", TextColors.GRAY, "off."));

					if (EssentialCmds.flyingPlayers.contains(player.getUniqueId()))
					{
						EssentialCmds.flyingPlayers.remove(player.getUniqueId());
					}
				}
				else
				{
					src.sendMessage(Text.of(TextColors.GOLD, "Toggled flying: ", TextColors.GRAY, "on."));
					player.sendMessage(Text.of(TextColors.GOLD, "Toggled flying: ", TextColors.GRAY, "on."));

					if (!EssentialCmds.flyingPlayers.contains(player.getUniqueId()))
					{
						EssentialCmds.flyingPlayers.add(player.getUniqueId());
					}
				}
			}
		}
		else
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You do not have permission to change others ability to fly."));
		}

		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "fly" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("Fly Command"))
			.permission("essentialcmds.fly.use")
			.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
			.executor(this)
			.build();
	}
}
