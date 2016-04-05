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

import com.flowpowered.math.vector.Vector3i;
import io.github.hsyyid.essentialcmds.internal.CommandExecutorBase;
import io.github.hsyyid.essentialcmds.utils.Utils;
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
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import javax.annotation.Nonnull;

public class TPChunkExecutor extends CommandExecutorBase
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		int x = ctx.<Integer> getOne("x").get();
		int z = ctx.<Integer> getOne("z").get();

		if (src instanceof Player)
		{
			Player player = (Player) src;
			Utils.setLastTeleportOrDeathLocation(player.getUniqueId(), player.getLocation());

			if (Sponge.getServer().getChunkLayout().isValidChunk(new Vector3i(x, 0, z)))
			{
				Location<World> location = new Location<World>(player.getWorld(), Sponge.getServer().getChunkLayout().forceToWorld(new Vector3i(x, 0, z)));
				player.setLocation(location);
				src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported to chunk."));
			}
			else
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Invalid chunk!"));
			}
		}
		else
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You cannot teleport, you are not a player!"));
		}

		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "tpchunk" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec
			.builder()
			.description(Text.of("Teleport Chunk Command"))
			.permission("essentialcmds.teleport.chunk.use")
			.arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.integer(Text.of("x"))), GenericArguments.onlyOne(GenericArguments.integer(Text.of("z")))))
			.executor(this).build();
	}
}
