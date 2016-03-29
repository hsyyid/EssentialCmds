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
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.manipulator.mutable.MobSpawnerData;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Optional;

import javax.annotation.Nonnull;

public class MobSpawnerExecutor extends CommandExecutorBase
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		EntityType entityType = ctx.<EntityType> getOne("mob").get();

		if (src instanceof Player)
		{
			Player player = (Player) src;

			ItemStack mobSpawnerStack = ItemStack.builder().itemType(ItemTypes.MOB_SPAWNER).quantity(1).build();
			Optional<MobSpawnerData> mobSpawnerData = mobSpawnerStack.getOrCreate(MobSpawnerData.class);

			if (mobSpawnerData.isPresent())
			{
				MobSpawnerData data = mobSpawnerData.get();
				data.nextEntityToSpawn().set(entityType, null);

				if (mobSpawnerStack.offer(data).isSuccessful())
				{
					player.setItemInHand(mobSpawnerStack);
					player.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Created mob spawner."));
				}
				else
				{
					player.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Failed to set mob spawner entity."));
				}
			}
			else
			{
				player.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Failed to create mob spawner."));
			}
		}
		else
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /mobspawner!"));
		}

		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "spawner", "mobspawner" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("Mob Spawner Command"))
			.permission("essentialcmds.mobspawner.use")
			.arguments(GenericArguments.onlyOne(GenericArguments.catalogedElement(Text.of("mob"), EntityType.class)))
			.executor(this).build();
	}
}
