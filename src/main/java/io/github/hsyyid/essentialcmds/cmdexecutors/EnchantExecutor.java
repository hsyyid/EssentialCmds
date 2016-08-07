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

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.manipulator.mutable.item.EnchantmentData;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.Enchantment;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import io.github.hsyyid.essentialcmds.internal.CommandExecutorBase;
import io.github.hsyyid.essentialcmds.utils.Utils;

public class EnchantExecutor extends CommandExecutorBase
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Player> target = ctx.<Player> getOne("target");
		String enchantmentName = ctx.<String> getOne("enchantment").get();
		int level = ctx.<Integer> getOne("level").get();

		Enchantment enchantment = Sponge.getRegistry().getType(Enchantment.class, enchantmentName).orElse(null);

		if (enchantment == null)
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Enchantment specified not found!"));
			return CommandResult.success();
		}

		if (!Utils.unsafeEnchanmentsEnabled())
		{
			if (enchantment.getMaximumLevel() < level)
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Enchantment level too high!"));
				return CommandResult.success();
			}

		}

		if (!target.isPresent())
		{
			if (src instanceof Player)
			{
				Player player = (Player) src;

				if (player.getItemInHand(HandTypes.MAIN_HAND).isPresent())
				{
					ItemStack itemInHand = player.getItemInHand(HandTypes.MAIN_HAND).get();

					if (!enchantment.canBeAppliedToStack(itemInHand))
					{
						src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Enchantment cannot be applied to this item!"));
						return CommandResult.success();
					}

					EnchantmentData enchantmentData = itemInHand.getOrCreate(EnchantmentData.class).get();
					ItemEnchantment itemEnchantment = new ItemEnchantment(enchantment, level);
					ItemEnchantment sameEnchantment = null;

					for (ItemEnchantment ench : enchantmentData.enchantments())
					{
						if (ench.getEnchantment().getId().equals(enchantment.getId()))
						{
							sameEnchantment = ench;
							break;
						}
					}

					if (sameEnchantment == null)
					{
						enchantmentData.set(enchantmentData.enchantments().add(itemEnchantment));
					}
					else
					{
						enchantmentData.set(enchantmentData.enchantments().remove(sameEnchantment));
						enchantmentData.set(enchantmentData.enchantments().add(itemEnchantment));
					}

					itemInHand.offer(enchantmentData);
					player.setItemInHand(HandTypes.MAIN_HAND, itemInHand);
					player.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Enchanted item(s) in your hand."));
				}
				else
				{
					src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be holding something to enchant!"));
				}
			}
			else if (src instanceof ConsoleSource)
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /enchant!"));
			}
			else if (src instanceof CommandBlockSource)
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /enchant!"));
			}
		}
		else if (target.isPresent() && src.hasPermission("essentialcmds.enchant.others"))
		{
			Player player = target.get();

			if (player.getItemInHand(HandTypes.MAIN_HAND).isPresent())
			{
				ItemStack itemInHand = player.getItemInHand(HandTypes.MAIN_HAND).get();

				if (!enchantment.canBeAppliedToStack(itemInHand))
				{
					src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Enchantment cannot be applied to this item!"));
					return CommandResult.success();
				}

				EnchantmentData enchantmentData = itemInHand.getOrCreate(EnchantmentData.class).get();
				ItemEnchantment itemEnchantment = new ItemEnchantment(enchantment, level);
				ItemEnchantment sameEnchantment = null;

				for (ItemEnchantment ench : enchantmentData.enchantments())
				{
					if (ench.getEnchantment().getId().equals(enchantment.getId()))
					{
						sameEnchantment = ench;
						break;
					}
				}

				if (sameEnchantment == null)
				{
					enchantmentData.set(enchantmentData.enchantments().add(itemEnchantment));
				}
				else
				{
					enchantmentData.set(enchantmentData.enchantments().remove(sameEnchantment));
					enchantmentData.set(enchantmentData.enchantments().add(itemEnchantment));
				}

				itemInHand.offer(enchantmentData);
				player.setItemInHand(HandTypes.MAIN_HAND, itemInHand);
				player.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Enchanted item(s) in your hand."));
			}
			else
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be holding something to enchant!"));
			}
		}

		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "enchant", "ench" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec
			.builder()
			.description(Text.of("Enchant Command"))
			.permission("essentialcmds.enchant.use")
			.arguments(GenericArguments.seq(GenericArguments.optional(GenericArguments.player(Text.of("target"))), GenericArguments.onlyOne(GenericArguments.integer(Text.of("level")))), GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Text.of("enchantment"))))
			.executor(this).build();
	}
}
