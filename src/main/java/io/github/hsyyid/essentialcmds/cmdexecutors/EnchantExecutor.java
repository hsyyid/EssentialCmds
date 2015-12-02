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

import io.github.hsyyid.essentialcmds.utils.Utils;

import org.spongepowered.api.data.manipulator.mutable.item.EnchantmentData;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.Enchantment;
import org.spongepowered.api.item.Enchantments;
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

public class EnchantExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		String enchantmentName = ctx.<String> getOne("enchantment").get();
		int level = ctx.<Integer> getOne("level").get();

		if (src instanceof Player)
		{
			Player player = (Player) src;

			Enchantment enchantment = null;

			switch (enchantmentName.toLowerCase())
			{
				case "aqua affinity":
					enchantment = Enchantments.AQUA_AFFINITY;
					break;
				case "bane of arthropods":
					enchantment = Enchantments.BANE_OF_ARTHROPODS;
					break;
				case "blast protection":
					enchantment = Enchantments.BLAST_PROTECTION;
					break;
				case "depth strider":
					enchantment = Enchantments.DEPTH_STRIDER;
					break;
				case "efficiency":
					enchantment = Enchantments.EFFICIENCY;
					break;
				case "feather falling":
					enchantment = Enchantments.FEATHER_FALLING;
					break;
				case "fire aspect":
					enchantment = Enchantments.FIRE_ASPECT;
					break;
				case "fire protection":
					enchantment = Enchantments.FIRE_PROTECTION;
					break;
				case "flame":
					enchantment = Enchantments.FLAME;
					break;
				case "fortune":
					enchantment = Enchantments.FORTUNE;
					break;
				case "infinity":
					enchantment = Enchantments.INFINITY;
					break;
				case "knockback":
					enchantment = Enchantments.KNOCKBACK;
					break;
				case "looting":
					enchantment = Enchantments.LOOTING;
					break;
				case "luck of the sea":
					enchantment = Enchantments.LUCK_OF_THE_SEA;
					break;
				case "lure":
					enchantment = Enchantments.LURE;
					break;
				case "power":
					enchantment = Enchantments.POWER;
					break;
				case "projectile protection":
					enchantment = Enchantments.PROJECTILE_PROTECTION;
					break;
				case "protection":
					enchantment = Enchantments.PROTECTION;
					break;
				case "punch":
					enchantment = Enchantments.PUNCH;
					break;
				case "respiration":
					enchantment = Enchantments.RESPIRATION;
					break;
				case "sharpness":
					enchantment = Enchantments.SHARPNESS;
					break;
				case "silk touch":
					enchantment = Enchantments.SILK_TOUCH;
					break;
				case "smite":
					enchantment = Enchantments.SMITE;
					break;
				case "thorns":
					enchantment = Enchantments.THORNS;
					break;
				case "unbreaking":
					enchantment = Enchantments.UNBREAKING;
					break;
				default:
					src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Enchantment specified not found!"));
					return CommandResult.success();
			}

			if (!Utils.unsafeEnchanmentsEnabled())
			{
				if (enchantment.getMaximumLevel() < level)
				{
					src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Enchantment level too high!"));
					return CommandResult.success();
				}

			}

			if (player.getItemInHand().isPresent())
			{
				ItemStack itemInHand = player.getItemInHand().get();

				if (!enchantment.canBeAppliedToStack(itemInHand))
				{
					src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Enchantment cannot be applied to this item!"));
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
				player.setItemInHand(itemInHand);
				player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Enchanted item(s) in your hand."));
			}
			else
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be holding something to enchant!"));
			}
		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /enchant!"));
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /enchant!"));
		}

		return CommandResult.success();
	}
}
