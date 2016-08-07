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
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnCause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;

import javax.annotation.Nonnull;

public class MobSpawnExecutor extends CommandExecutorBase
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		int amount = ctx.<Integer> getOne("amount").get();
		EntityType type = ctx.<EntityType> getOne("mob").get();

		if (src instanceof Player)
		{
			Player player = (Player) src;

			if (!Living.class.isAssignableFrom(type.getEntityClass()))
			{
				player.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "The mob type you inputted is not a living entity."));
				return CommandResult.success();
			}

			this.spawnEntity(getSpawnLocFromPlayerLoc(player).add(0, 1, 0), player, type, amount);
			player.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Spawned mob(s)!"));
		}
		else
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /mobspawn!"));
		}

		return CommandResult.success();
	}

	public void spawnEntity(Location<World> location, Player player, EntityType type, int amount)
	{
		for (int i = 1; i <= amount; i++)
		{
			Entity entity = location.getExtent().createEntity(type, location.getPosition());
			location.getExtent().spawnEntity(entity, Cause.of(NamedCause.source(SpawnCause.builder().type(SpawnTypes.CUSTOM).build())));
		}
	}

	public Location<World> getSpawnLocFromPlayerLoc(Player player)
	{
		BlockRay<World> playerBlockRay = BlockRay.from(player).blockLimit(350).build();
		BlockRayHit<World> finalHitRay = null;

		while (playerBlockRay.hasNext())
		{
			BlockRayHit<World> currentHitRay = playerBlockRay.next();

			if (!player.getWorld().getBlockType(currentHitRay.getBlockPosition()).equals(BlockTypes.AIR))
			{
				finalHitRay = currentHitRay;
				break;
			}
		}

		Location<World> spawnLocation = null;

		if (finalHitRay == null)
		{
			spawnLocation = player.getLocation();
		}
		else
		{
			spawnLocation = finalHitRay.getLocation();
		}

		return spawnLocation;
	}

	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "mobspawn", "entityspawn" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder().description(Text.of("Mob Spawn Command")).permission("essentialcmds.mobspawn.use").arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.integer(Text.of("amount")))), GenericArguments.onlyOne(GenericArguments.catalogedElement(Text.of("mob"), EntityType.class))).executor(this).build();
	}
}
