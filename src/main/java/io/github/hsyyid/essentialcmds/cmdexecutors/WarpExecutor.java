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
import io.github.hsyyid.essentialcmds.PluginInfo;
import io.github.hsyyid.essentialcmds.internal.CommandExecutorBase;
import io.github.hsyyid.essentialcmds.utils.Utils;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

public class WarpExecutor extends CommandExecutorBase
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		String warpName = ctx.<String> getOne("warp name").get();
		Optional<Player> optionalPlayer = ctx.<Player> getOne("player");

		if (!optionalPlayer.isPresent())
		{
			if (src instanceof Player)
			{
				Player player = (Player) src;

				if (Utils.isWarpInConfig(warpName))
				{
					Transform<World> warpLocation = Utils.getWarp(warpName);

					if (player.hasPermission("essentialcmds.warp.use." + warpName))
					{
						if (Utils.isTeleportCooldownEnabled() && !player.hasPermission("essentialcmds.teleport.cooldown.override"))
						{
							EssentialCmds.teleportingPlayers.add(player.getUniqueId());
							src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleporting to last location. Please wait " + Utils.getTeleportCooldown() + " seconds."));
							Sponge.getScheduler().createTaskBuilder().execute(() -> {
								if (EssentialCmds.teleportingPlayers.contains(player.getUniqueId()))
								{
									Utils.setLastTeleportOrDeathLocation(player.getUniqueId(), player.getLocation());

									if (!Objects.equals(player.getWorld().getUniqueId(), warpLocation.getExtent().getUniqueId()))
									{
										player.transferToWorld(warpLocation.getExtent().getUniqueId(), warpLocation.getPosition());
										player.setTransform(warpLocation);
									}
									else
									{
										player.setTransform(warpLocation);
									}

									src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported to warp " + Utils.getConfigWarpName(warpName)));
									EssentialCmds.teleportingPlayers.remove(player.getUniqueId());
								}
							}).delay(Utils.getTeleportCooldown(), TimeUnit.SECONDS).name("EssentialCmds - Back Timer").submit(Sponge.getGame().getPluginManager().getPlugin(PluginInfo.ID).get().getInstance().get());
						}
						else
						{
							Utils.setLastTeleportOrDeathLocation(player.getUniqueId(), player.getLocation());

							if (!Objects.equals(player.getWorld().getUniqueId(), warpLocation.getExtent().getUniqueId()))
							{
								player.transferToWorld(warpLocation.getExtent().getUniqueId(), warpLocation.getPosition());
								player.setTransform(warpLocation);
							}
							else
							{
								player.setTransform(warpLocation);
							}

							src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported to warp " + warpName));
						}
					}
					else
					{
						src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You do not have permission to use this warp!"));
					}
				}
				else
				{
					src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Warp " + warpName + " does not exist!"));
				}
			}
			else
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /warp!"));
			}
		}
		else
		{
			Player player = optionalPlayer.get();
			Transform<World> warpLocation = Utils.getWarp(warpName);

			if (Utils.isWarpInConfig(warpName) && warpLocation != null)
			{
				if (src.hasPermission("essentialcmds.warp.use." + warpName) && src.hasPermission("essentialcmds.warp.others"))
				{
					Utils.setLastTeleportOrDeathLocation(player.getUniqueId(), player.getLocation());

					if (!Objects.equals(player.getWorld().getUniqueId(), warpLocation.getExtent().getUniqueId()))
					{
						player.transferToWorld(warpLocation.getExtent().getUniqueId(), warpLocation.getPosition());
						player.setTransform(warpLocation);
					}
					else
					{
						player.setTransform(warpLocation);
					}

					src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported to warp " + warpName));
				}
				else
				{
					src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You do not have permission to use this warp!"));
				}
			}
			else
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Warp " + warpName + " does not exist!"));
			}
		}

		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "warp" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("Warp Command"))
			.permission("essentialcmds.warp.use")
			.arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.string(Text.of("warp name"))), GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))))))
			.executor(this)
			.build();
	}
}
