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
package io.github.hsyyid.essentialcmds.listeners;

import io.github.hsyyid.essentialcmds.EssentialCmds;
import io.github.hsyyid.essentialcmds.utils.AFK;
import io.github.hsyyid.essentialcmds.utils.Utils;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DisplaceEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

public class PlayerMoveListener
{
	@Listener
	public void onPlayerMove(DisplaceEntityEvent event, @First Player player)
	{
		if (Utils.isTeleportCooldownEnabled() && EssentialCmds.teleportingPlayers.contains(player.getUniqueId()))
		{
			EssentialCmds.teleportingPlayers.remove(player.getUniqueId());
			player.sendMessage(Text.of(TextColors.RED, "Teleportation canceled due to movement."));
		}

		if (EssentialCmds.frozenPlayers.contains(player.getUniqueId()))
		{
			player.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You cannot move while frozen."));
			event.setCancelled(true);
			return;
		}

		if (EssentialCmds.recentlyJoined.contains(player))
		{
			EssentialCmds.recentlyJoined.remove(player);

			if (EssentialCmds.afkList.containsKey(player.getUniqueId()))
			{
				EssentialCmds.afkList.remove(player.getUniqueId());
			}
		}
		else
		{
			if (EssentialCmds.afkList.containsKey(player.getUniqueId()))
			{
				AFK removeAFK = EssentialCmds.afkList.get(player.getUniqueId());

				if (removeAFK.getAFK())
				{
					for (Player p : EssentialCmds.getEssentialCmds().getGame().getServer().getOnlinePlayers())
					{
						p.sendMessage(Text.of(TextColors.BLUE, player.getName(), TextColors.GOLD, " is no longer AFK."));
					}
				}

				EssentialCmds.afkList.remove(removeAFK);
			}

			AFK afk = new AFK(System.currentTimeMillis());
			EssentialCmds.afkList.put(player.getUniqueId(), afk);
		}

		if (!event.getFromTransform().getExtent().getUniqueId().equals(event.getToTransform().getExtent().getUniqueId()))
		{
			World oldWorld = event.getFromTransform().getExtent();
			World newWorld = event.getToTransform().getExtent();

			Utils.savePlayerInventory(player, oldWorld.getUniqueId());
			Utils.updatePlayerInventory(player, newWorld.getUniqueId());

			player.offer(Keys.GAME_MODE, newWorld.getProperties().getGameMode());
		}
	}
}
