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
import io.github.hsyyid.essentialcmds.internal.CommandExecutorBase;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.TeleportHelper;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.WorldBorder;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Random;

public class RTPExecutor extends CommandExecutorBase
{
	@Override
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		int tryTimes = 10;

		if (src instanceof Player)
		{
			Player player = (Player) src;
			//Why 3999? why hard code it?
			Optional<Location<World>> optionalLocation = randomLocation(player, 3999);
			if (optionalLocation.isPresent())
			{
				//Why check danger? shouldn't safe teleport have already checked this?
				if (isDangerous(optionalLocation))
				{
					//restrict the number of times we check, as every time we do this we are loading chunks...
					for(int i = 0; i < tryTimes; i++)
					{
						//Why 10000? are we supposed to be "less fussy" or something?
						// we only checked a width of 9 by default with safe teleport
						optionalLocation = randomLocation(player, 10000);
						//repeated isDanger check, when we have already checked it in safeTeleport
						if (optionalLocation.isPresent() && !isDangerous(optionalLocation))
						{
							teleportPlayer(player, optionalLocation);
							return CommandResult.success();
						}
					}
					player.sendMessage(Text.of(TextColors.DARK_RED, "A safe random location was not found, please try again"));
					return CommandResult.empty();
				} else {
					teleportPlayer(player, optionalLocation);
					return CommandResult.success();
				}
			}
			else
			{
				for(int i = 0; i < tryTimes; i++)
				{
					optionalLocation = randomLocation(player, 10000);
					//swapping to isWet? but the danger check is already done in safe teleport
					if (optionalLocation.isPresent() && !isWet(optionalLocation))
					{
						teleportPlayer(player, optionalLocation);
						return CommandResult.success();
					}
				}
				player.sendMessage(Text.of(TextColors.DARK_RED, "A safe random location was not found, please try again"));
				return CommandResult.empty();
			}
		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /rtp!"));
			return CommandResult.empty();
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /rtp!"));
			return CommandResult.empty();
		}

		return CommandResult.success();
	}

	private void teleportPlayer(Player player, Optional<Location<World>> optionalLocation) {
		player.setLocation(optionalLocation.get());
		player.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleporting you to nearest safe location!"));
	}

	private boolean isWet(Optional<Location<World>> optionalLocation) {
		return optionalLocation.get().getBlock().getType().equals(BlockTypes.WATER);
	}

	private boolean isDangerous(Optional<Location<World>> optionalLocation) {
		return optionalLocation.get().getBlock().getType().equals(BlockTypes.WATER)
		    || optionalLocation.get().getBlock().getType().equals(BlockTypes.FLOWING_WATER)
		    || optionalLocation.get().getBlock().getType().equals(BlockTypes.LAVA)
		    || optionalLocation.get().getBlock().getType().equals(BlockTypes.FLOWING_LAVA)
		    || optionalLocation.get().getBlock().getType().equals(BlockTypes.FIRE);
	}

	private Optional<Location<World>> randomLocation(Player player, int searchDiameter){
		Location<World> playerLocation = player.getLocation();
		//Adding world border support, otherwise you could murder players by using a location within the border.
		WorldBorder border = player.getWorld().getWorldBorder();
		Vector3d center = border.getCenter();
		double diameter = Math.min(border.getDiameter(), searchDiameter);
		double radius = border.getDiameter() / 2;
		Random rand = new Random();
		int x = (int) (rand.nextInt((int) (center.getX()+diameter)) - radius);
		int y = rand.nextInt(256);
		int z = rand.nextInt((int) (rand.nextInt((int) (center.getZ()+diameter)) - radius));

		Location<World> randLocation = new Location<World>(playerLocation.getExtent(), x, y, z);
		TeleportHelper teleportHelper = Sponge.getGame().getTeleportHelper();
		return teleportHelper.getSafeLocation(randLocation);
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "randomtp", "rtp" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder().description(Text.of("RTP Command")).permission("essentialcmds.rtp.use")
				.executor(this).build();
	}
}
