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

import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;
import org.spongepowered.api.world.World;

public class EntityInfoExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		if (src instanceof Player)
		{
			Player player = (Player) src;

			BlockRay<World> playerBlockRay = BlockRay.from(player).blockLimit(5).build();
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

			if (finalHitRay != null)
			{
				Entity entityFound = null;

				for (Entity entity : player.getWorld().getEntities())
				{
					if (entity.getLocation().getBlockPosition().equals(finalHitRay.getBlockPosition().add(new Vector3i(0, 1, 0))))
					{
						entityFound = entity;
						break;
					}
				}

				if (entityFound != null)
				{
					player.sendMessage(Texts.of(TextColors.GOLD, "The name of the entity you're looking at is: ", TextColors.GRAY, entityFound.getType().getName()));
				}
				else
				{
					player.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "No entity found!"));
				}
			}
			else
			{
				player.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You're not looking at any block within range."));
			}
		}
		else
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be an in-game player to use this command."));
		}

		return CommandResult.success();
	}
}
