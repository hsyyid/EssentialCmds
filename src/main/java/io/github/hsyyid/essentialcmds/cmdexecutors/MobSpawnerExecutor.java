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
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.manipulator.mutable.MobSpawnerData;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nonnull;
import java.util.Optional;

public class MobSpawnerExecutor extends CommandExecutorBase
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		String entityType = ctx.<String> getOne("mob name").get();

		if (src instanceof Player)
		{
			Player player = (Player) src;
			EntityType type = Sponge.getRegistry().getType(EntityType.class, entityType).orElse(null);
			if (type != null)
			{
				if (!Living.class.isAssignableFrom(type.getEntityClass())) {
					src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "The mob type you inputted is not a living entity."));
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
					player.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Spawned mob spawner."));
				}
				else
				{
					player.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Could not apply mob spawner data."));
				}
			}
			else
			{
				player.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "EntityType not recognized."));
			}
		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /mobspawner!"));
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /mobspawner!"));
		}
		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "spawner", "mobspawner" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder().description(Text.of("Mob Spawner Command")).permission("essentialcmds.mobspawner.use")
				.arguments(GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Text.of("mob name")))).executor(this).build();
	}
}
