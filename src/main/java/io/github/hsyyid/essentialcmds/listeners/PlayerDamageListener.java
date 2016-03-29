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
import io.github.hsyyid.essentialcmds.utils.Utils;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class PlayerDamageListener
{
	@Listener
	public void onPlayerDamaged(DamageEntityEvent event)
	{
		if (event.getTargetEntity() instanceof Player)
		{
			Player player = (Player) event.getTargetEntity();

			if (Utils.isTeleportCooldownEnabled() && EssentialCmds.teleportingPlayers.contains(player.getUniqueId()))
			{
				EssentialCmds.teleportingPlayers.remove(player.getUniqueId());
				player.sendMessage(Text.of(TextColors.RED, "Teleportation canceled due to damage."));
			}

			if (EssentialCmds.godPlayers.contains(player.getUniqueId()))
			{
				event.setBaseDamage(0);
				event.setCancelled(true);
			}
		}
	}
}
