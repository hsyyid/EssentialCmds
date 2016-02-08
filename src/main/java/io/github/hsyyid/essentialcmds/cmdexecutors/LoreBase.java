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

import com.google.common.collect.Lists;
import io.github.hsyyid.essentialcmds.internal.CommandExecutorBase;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.item.LoreData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

public class LoreBase extends CommandExecutorBase
{
	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "lore" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("Lore Command"))
			.permission("essentialcmds.lore.use")
			.children(getChildrenList(new Set(), new Add(), new Remove()))
			.build();
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
	{
		return CommandResult.empty();
	}

	static class Set extends CommandExecutorBase
	{
		public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
		{
			String lore = ctx.<String> getOne("lore").get();

			if (src instanceof Player)
			{
				Player player = (Player) src;

				if (player.getItemInHand().isPresent())
				{
					ItemStack stack = player.getItemInHand().get();
					List<String> loreList = Lists.newArrayList();
					loreList.addAll(Arrays.asList(lore.split("\\s*,\\s*")));
					List<Text> loreTextList = Lists.newArrayList();

					for (String l : loreList)
					{
						loreTextList.add(TextSerializers.FORMATTING_CODE.deserialize(l));
					}

					DataTransactionResult dataTransactionResult = stack.offer(Keys.ITEM_LORE, loreTextList);

					if (dataTransactionResult.isSuccessful())
					{
						player.setItemInHand(stack);
						src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Set lore on item."));
					}
					else
					{
						src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Could not set lore on item."));
					}
				}
				else
				{
					src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be holding an item!"));
				}
			}
			else
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be a player to name items."));
			}
			return CommandResult.success();
		}

		@Nonnull
		@Override
		public String[] getAliases()
		{
			return new String[] { "set" };
		}

		@Nonnull
		@Override
		public CommandSpec getSpec()
		{
			return CommandSpec
				.builder()
				.description(Text.of("Set Lore Command"))
				.permission("essentialcmds.lore.set")
				.arguments(GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Text.of("lore"))))
				.executor(this)
				.build();
		}
	}

	static class Add extends CommandExecutorBase
	{
		public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
		{
			String lore = ctx.<String> getOne("lore").get();

			if (src instanceof Player)
			{
				Player player = (Player) src;

				if (player.getItemInHand().isPresent())
				{
					ItemStack stack = player.getItemInHand().get();
					LoreData loreData = stack.getOrCreate(LoreData.class).get();
					Text textLore = TextSerializers.FORMATTING_CODE.deserialize(lore);
					List<Text> newLore = loreData.lore().get();
					newLore.add(textLore);
					DataTransactionResult dataTransactionResult = stack.offer(Keys.ITEM_LORE, newLore);

					if (dataTransactionResult.isSuccessful())
					{
						player.setItemInHand(stack);
						src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Added lore to item."));
					}
					else
					{
						src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Could not add lore to item."));
					}
				}
				else
				{
					src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be holding an item!"));
				}
			}
			else
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be a player to name items."));
			}
			return CommandResult.success();
		}

		@Nonnull
		@Override
		public String[] getAliases()
		{
			return new String[] { "add" };
		}

		@Nonnull
		@Override
		public CommandSpec getSpec()
		{
			return CommandSpec
				.builder()
				.description(Text.of("Add Lore Command"))
				.permission("essentialcmds.lore.add")
				.arguments(GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Text.of("lore"))))
				.executor(this)
				.build();
		}
	}
	
	static class Remove extends CommandExecutorBase
	{
		public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
		{
			int number = ctx.<Integer> getOne("number").get();
			
			if (src instanceof Player)
			{
				Player player = (Player) src;

				if (player.getItemInHand().isPresent())
				{
					ItemStack stack = player.getItemInHand().get();
					LoreData loreData = stack.getOrCreate(LoreData.class).get();
					List<Text> newLore = loreData.lore().get();
					newLore.remove(number - 1);
					DataTransactionResult dataTransactionResult = stack.offer(Keys.ITEM_LORE, newLore);

					if (dataTransactionResult.isSuccessful())
					{
						player.setItemInHand(stack);
						src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Removed lore from item."));
					}
					else
					{
						src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Could not remove lore from item."));
					}
				}
				else
				{
					src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be holding an item!"));
				}
			}
			else
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be a player to name items."));
			}
			return CommandResult.success();
		}

		@Nonnull
		@Override
		public String[] getAliases()
		{
			return new String[] { "remove" };
		}

		@Nonnull
		@Override
		public CommandSpec getSpec()
		{
			return CommandSpec
				.builder()
				.description(Text.of("Remove Lore Command"))
				.permission("essentialcmds.lore.remove")
				.arguments(GenericArguments.onlyOne(GenericArguments.integer(Text.of("number"))))
				.executor(this)
				.build();
		}
	}
}
