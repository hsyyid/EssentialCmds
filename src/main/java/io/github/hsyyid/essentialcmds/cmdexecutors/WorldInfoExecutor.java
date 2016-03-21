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
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

import java.util.Optional;

import javax.annotation.Nonnull;

public class WorldInfoExecutor extends CommandExecutorBase
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<String> worldName = ctx.<String> getOne("world name");

		if (!worldName.isPresent())
		{
			if (src instanceof Player)
			{
				Player player = (Player) src;
				World world = player.getWorld();

				player.sendMessage(Text.of(TextColors.GOLD, "World UUID: ", TextColors.GRAY, world.getProperties().getUniqueId()));
				player.sendMessage(Text.of(TextColors.GOLD, "Difficulty: ", TextColors.GRAY, world.getProperties().getDifficulty()));
				player.sendMessage(Text.of(TextColors.GOLD, "Dimension Type: ", TextColors.GRAY, world.getProperties().getDimensionType().getName()));
				player.sendMessage(Text.of(TextColors.GOLD, "Gamemode: ", TextColors.GRAY, world.getProperties().getGameMode()));
				player.sendMessage(Text.of(TextColors.GOLD, "Seed: ", TextColors.GRAY, world.getProperties().getSeed()));
				player.sendMessage(Text.of(TextColors.GOLD, "World Time: ", TextColors.GRAY, world.getProperties().getWorldTime()));
				player.sendMessage(Text.of(TextColors.GOLD, "Are commands allowed: ", TextColors.GRAY, world.getProperties().areCommandsAllowed()));
			}
			else
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be an in-game player to use this command."));
			}
		}
		else
		{
			World world = Sponge.getServer().getWorld(worldName.get()).orElse(null);

			if (world != null)
			{
				src.sendMessage(Text.of(TextColors.GOLD, "World UUID: ", TextColors.GRAY, world.getProperties().getUniqueId()));
				src.sendMessage(Text.of(TextColors.GOLD, "Difficulty: ", TextColors.GRAY, world.getProperties().getDifficulty()));
				src.sendMessage(Text.of(TextColors.GOLD, "Dimension Type: ", TextColors.GRAY, world.getProperties().getDimensionType()));
				src.sendMessage(Text.of(TextColors.GOLD, "Gamemode: ", TextColors.GRAY, world.getProperties().getGameMode()));
				src.sendMessage(Text.of(TextColors.GOLD, "Seed: ", TextColors.GRAY, world.getProperties().getSeed()));
				src.sendMessage(Text.of(TextColors.GOLD, "World Time: ", TextColors.GRAY, world.getProperties().getWorldTime()));
				src.sendMessage(Text.of(TextColors.GOLD, "Are commands allowed: ", TextColors.GRAY, world.getProperties().areCommandsAllowed()));
			}
			else
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "World not found!"));
			}
		}

		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "worldinfo" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("WorldInfo Command"))
			.permission("essentialcmds.worldinfo.use")
			.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Text.of("world name")))))
			.executor(this)
			.build();
	}
}
