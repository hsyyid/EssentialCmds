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
import org.spongepowered.api.data.manipulator.mutable.MobSpawnerData;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.source.CommandBlockSource;
import org.spongepowered.api.util.command.source.ConsoleSource;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import java.util.Optional;

public class MobSpawnerExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
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

			ItemStack.Builder itemBuilder = EssentialCmds.getEssentialCmds().getGame().getRegistry().createBuilder(ItemStack.Builder.class);
			ItemStack mobSpawnerStack = itemBuilder.itemType(ItemTypes.MOB_SPAWNER).quantity(1).build();
			Optional<MobSpawnerData> optionalMobSpawnerData = mobSpawnerStack.getOrCreate(MobSpawnerData.class);

			if (optionalMobSpawnerData.isPresent())
			{
				MobSpawnerData mobSpawnerData = optionalMobSpawnerData.get();
				mobSpawnerData = mobSpawnerData.set(mobSpawnerData.nextEntityToSpawn().set(type, null));
				mobSpawnerStack.offer(mobSpawnerData);
				player.setItemInHand(mobSpawnerStack);
				player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Spawned mob spawner."));
			}
			else
			{
				player.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Could not apply mob spawner data."));
			}
		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /mobspawner!"));
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /mobspawner!"));
		}
		return CommandResult.success();
	}
}
