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

import org.spongepowered.api.entity.EntityTypes;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.source.CommandBlockSource;
import org.spongepowered.api.util.command.source.ConsoleSource;
import org.spongepowered.api.util.command.spec.CommandExecutor;
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

			EntityType type = null;

			switch (entityType.toLowerCase())
			{
				case "bat":
					type = EntityTypes.BAT;
					break;
				case "blaze":
					type = EntityTypes.BLAZE;
					break;
				case "cave spider":
					type = EntityTypes.CAVE_SPIDER;
					break;
				case "chicken":
					type = EntityTypes.CHICKEN;
					break;
				case "cow":
					type = EntityTypes.COW;
					break;
				case "creeper":
					type = EntityTypes.CREEPER;
					break;
				case "ender dragon":
					type = EntityTypes.ENDER_DRAGON;
					break;
				case "enderman":
					type = EntityTypes.ENDERMAN;
					break;
				case "endermite":
					type = EntityTypes.ENDERMITE;
					break;
				case "ghast":
					type = EntityTypes.GHAST;
					break;
				case "giant":
					type = EntityTypes.GIANT;
					break;
				case "guardian":
					type = EntityTypes.GUARDIAN;
					break;
				case "horse":
					type = EntityTypes.HORSE;
					break;
				case "iron golem":
					type = EntityTypes.IRON_GOLEM;
					break;
				case "magma cube":
					type = EntityTypes.MAGMA_CUBE;
					break;
				case "mushroom cow":
					type = EntityTypes.MUSHROOM_COW;
					break;
				case "ocelot":
					type = EntityTypes.OCELOT;
					break;
				case "pig":
					type = EntityTypes.PIG;
					break;
				case "pig zombie":
					type = EntityTypes.PIG_ZOMBIE;
					break;
				case "rabbit":
					type = EntityTypes.RABBIT;
					break;
				case "sheep":
					type = EntityTypes.SHEEP;
					break;
				case "silverfish":
					type = EntityTypes.SILVERFISH;
					break;
				case "skeleton":
					type = EntityTypes.SKELETON;
					break;
				case "slime":
					type = EntityTypes.SLIME;
					break;
				case "snowman":
					type = EntityTypes.SNOWMAN;
					break;
				case "spider":
					type = EntityTypes.SPIDER;
					break;
				case "squid":
					type = EntityTypes.SQUID;
					break;
				case "villager":
					type = EntityTypes.VILLAGER;
					break;
				case "witch":
					type = EntityTypes.WITCH;
					break;
				case "wither":
					type = EntityTypes.WITHER;
					break;
				case "wolf":
					type = EntityTypes.WOLF;
					break;
				case "zombie":
					type = EntityTypes.ZOMBIE;
					break;
				default:
					src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "The mob you inputed was not recognized."));
					return CommandResult.success();
			}

			spawnEntity(getSpawnLocFromPlayerLoc(player).add(0, 1, 0), type, amount);
			player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Spawned mob(s)!"));
		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /lightning!"));
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /lightning!"));
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
			extent.spawnEntity(entity, Cause.empty());
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
