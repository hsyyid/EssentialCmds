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
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.RepresentedPlayerData;
import org.spongepowered.api.data.type.SkullTypes;
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

public class SkullExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Player> optionalTarget = ctx.<Player> getOne("player");

		if (!optionalTarget.isPresent())
		{
			if (src instanceof Player)
			{
				Player player = (Player) src;

				// Create the Skull
				ItemStack.Builder builder = EssentialCmds.game.getRegistry().createBuilder(ItemStack.Builder.class);
				ItemStack skullStack = builder.itemType(ItemTypes.SKULL).quantity(1).build();

				// Set it to player skull type
				skullStack.offer(Keys.SKULL_TYPE, SkullTypes.PLAYER);

				// Set the owner to the command executor
				RepresentedPlayerData playerData = skullStack.getOrCreate(RepresentedPlayerData.class).get();
				playerData = playerData.set(playerData.owner().set(player.getProfile()));
				skullStack.offer(playerData);

				// Put it in inventory
				player.setItemInHand(skullStack);

				player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Created skull of your head. Enjoy!"));
			}
			else if (src instanceof ConsoleSource)
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /skull!"));
			}
			else if (src instanceof CommandBlockSource)
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /skull!"));
			}
		}
		else
		{
			if (src instanceof Player)
			{
				Player source = (Player) src;
				Player player = optionalTarget.get();

				// Create the Skull
				ItemStack.Builder builder = EssentialCmds.game.getRegistry().createBuilder(ItemStack.Builder.class);
				ItemStack skullStack = builder.itemType(ItemTypes.SKULL).quantity(1).build();

				// Set it to player skull type
				skullStack.offer(Keys.SKULL_TYPE, SkullTypes.PLAYER);

				// Set the owner to the specified player
				RepresentedPlayerData playerData = skullStack.getOrCreate(RepresentedPlayerData.class).get();
				playerData = playerData.set(playerData.owner().set(player.getProfile()));
				skullStack.offer(playerData);

				// Put it in inventory
				source.setItemInHand(skullStack);

				source.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Created skull of player's head. Enjoy!"));
			}
			else if (src instanceof ConsoleSource)
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /skull!"));
			}
			else if (src instanceof CommandBlockSource)
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /skull!"));
			}
		}

		return CommandResult.success();
	}
}
