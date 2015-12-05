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

import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;

import java.util.Optional;

public class FireballExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Player> optionalTarget = ctx.<Player> getOne("player");

		if (!optionalTarget.isPresent())
		{
			if (src instanceof Player)
			{
				Player player = (Player) src;
				BlockRay<World> playerBlockRay = BlockRay.from(player).blockLimit(350).build();

				Location<World> spawnLocation = null;
				int i = 0;

				while (playerBlockRay.hasNext())
				{
					// TODO: Come up with a better way of making sure it doesn't hit the player.
					i++;

					BlockRayHit<World> currentHitRay = playerBlockRay.next();

					if (spawnLocation == null && i > 5)
					{
						spawnLocation = currentHitRay.getLocation();
						break;
					}
				}

				Vector3d velocity = player.getTransform().getRotationAsQuaternion().getDirection();
				spawnEntity(spawnLocation, velocity, player);
				player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Created Fireball!"));
			}
			else if (src instanceof ConsoleSource)
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /fireball!"));
			}
			else if (src instanceof CommandBlockSource)
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /fireball!"));
			}
		}
		else
		{
			Player player = optionalTarget.get();
			Location<World> playerLocation = player.getLocation();

			Vector3d velocity = player.getTransform().getRotationAsQuaternion().getDirection();
			spawnEntity(playerLocation, velocity, src);
			player.sendMessage(Texts.of(TextColors.GRAY, src.getName(), TextColors.GOLD, " has struck you with a fireball."));
			src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Struck " + player.getName() + " with fireball."));
		}

		return CommandResult.success();
	}

	public void spawnEntity(Location<World> location, Vector3d velocity, CommandSource src)
	{
		Extent extent = location.getExtent();
		Optional<Entity> optional = extent.createEntity(EntityTypes.FIREBALL, location.getPosition());

		Entity fireball = optional.get();
		fireball.offer(Keys.VELOCITY, velocity);
		extent.spawnEntity(fireball, Cause.of(src));
	}
}
