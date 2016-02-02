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
import io.github.hsyyid.essentialcmds.utils.Powertool;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.action.InteractEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class PlayerClickListener
{
	@Listener
	public void onPlayerClick(InteractEvent event, @First Player player)
	{
		if (EssentialCmds.frozenPlayers.contains(player.getUniqueId()))
		{
			player.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You cannot click while frozen."));
			event.setCancelled(true);
			return;
		}

		if (EssentialCmds.jailedPlayers.contains(player.getUniqueId()))
		{
			player.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You cannot click while jailed."));
			event.setCancelled(true);
			return;
		}

		Powertool foundTool = null;

		for (Powertool powertool : EssentialCmds.powertools)
		{
			if (powertool.getPlayer().equals(player))
			{
				if (player.getItemInHand().isPresent() && powertool.getItemID().equals(player.getItemInHand().get().getItem().getName()))
				{
					foundTool = powertool;
					break;
				}
			}
		}

		if (foundTool != null)
		{
			Sponge.getGame().getCommandManager().process(player, foundTool.getCommand());
		}
	}

	@Listener
	public void onPlayerClickEntity(InteractEntityEvent event, @First Player player)
	{
		if (EssentialCmds.frozenPlayers.contains(player.getUniqueId()))
		{
			player.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You cannot interact with entities while frozen."));
			event.setCancelled(true);
			return;
		}
	}
}
