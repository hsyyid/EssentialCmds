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

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;

import java.util.Optional;

public class MobSpawnExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		int amount = ctx.<Integer> getOne("amount").get();
		String entityType = ctx.<String> getOne("mob name").get();

		if (src instanceof Player)
		{
			Player player = (Player) src;

			EntityType type = Sponge.getRegistry().getType(EntityType.class, entityType).orElse(null);

			if (type == null)
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "The mob you inputed was not recognized."));
				return CommandResult.success();
			}

			spawnEntity(getSpawnLocFromPlayerLoc(player).add(0, 1, 0), type, amount);
			player.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Spawned mob(s)!"));
		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /lightning!"));
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /lightning!"));
		}

		return CommandResult.success();
	}

	public void spawnEntity(Location<World> location, EntityType type, int amount)
	{
		for (int i = 1; i <= amount; i++)
		{
			Extent extent = location.getExtent();
			Optional<Entity> optional = extent.createEntity(type, location.getPosition());
			Entity entity = optional.get();
			extent.spawnEntity(entity, Cause.of(this));
		}
	}

	public Location<World> getSpawnLocFromPlayerLoc(Player player)
	{
		BlockRay<World> playerBlockRay = BlockRay.from(player).blockLimit(350).build();

		BlockRayHit<World> finalHitRay = null;

		while (playerBlockRay.hasNext())
		{
			BlockRayHit<World> currentHitRay = playerBlockRay.next();

			if (player.getWorld().getBlockType(currentHitRay.getBlockPosition()).equals(BlockTypes.AIR))
			{
				continue;
			}
			else
			{
				finalHitRay = currentHitRay;
				break;
			}
		}

		Location<World> lightningLocation = null;

		if (finalHitRay == null)
		{
			lightningLocation = player.getLocation();
		}
		else
		{
			lightningLocation = finalHitRay.getLocation();
		}

		return lightningLocation;
	}
}
