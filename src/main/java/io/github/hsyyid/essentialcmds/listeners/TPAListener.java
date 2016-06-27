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

import java.util.concurrent.TimeUnit;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import io.github.hsyyid.essentialcmds.EssentialCmds;
import io.github.hsyyid.essentialcmds.events.TPAAcceptEvent;
import io.github.hsyyid.essentialcmds.events.TPAEvent;
import io.github.hsyyid.essentialcmds.events.TPAHereAcceptEvent;
import io.github.hsyyid.essentialcmds.events.TPAHereEvent;
import io.github.hsyyid.essentialcmds.utils.PendingInvitation;
import io.github.hsyyid.essentialcmds.utils.Utils;

public class TPAListener
{
	private EssentialCmds plugin;

	public TPAListener(EssentialCmds plugin)
	{
		this.plugin = plugin;
	}

	@Listener
	public void tpaEventHandler(TPAEvent event)
	{
		String senderName = event.getSender().getName();
		event.getRecipient().sendMessage(Text.of(TextColors.BLUE, "TPA Request From: ", TextColors.GOLD, senderName + ".", TextColors.RED, " You have 10 seconds to do /tpaccept to accept the request"));

		// Adds Invite to List
		final PendingInvitation invite = new PendingInvitation(event.getSender(), event.getRecipient());
		EssentialCmds.pendingInvites.add(invite);

		// Removes Invite after 10 Seconds
		Sponge.getScheduler().createTaskBuilder().execute(() -> {
			if (EssentialCmds.pendingInvites.contains(invite))
			{
				EssentialCmds.pendingInvites.remove(invite);
			}
		}).delay(10, TimeUnit.SECONDS).name("EssentialCmds - Remove Pending Invite").submit(plugin);
	}

	@Listener
	public void tpaAcceptEventHandler(TPAAcceptEvent event)
	{
		String senderName = event.getSender().getName();

		if (Utils.isTeleportCooldownEnabled() && !event.getRecipient().hasPermission("essentialcmds.teleport.cooldown.override"))
		{
			EssentialCmds.teleportingPlayers.add(event.getRecipient().getUniqueId());
			event.getRecipient().sendMessage(Text.of(TextColors.GREEN, senderName, TextColors.WHITE, " accepted your TPA Request. Please wait " + Utils.getTeleportCooldown() + " seconds."));

			Sponge.getScheduler().createTaskBuilder().execute(() -> {
				try
				{
					EssentialCmds.timings.teleportCooldown().startTiming();

					if (EssentialCmds.teleportingPlayers.contains(event.getRecipient().getUniqueId()))
					{
						Utils.setLastTeleportOrDeathLocation(event.getRecipient().getUniqueId(), event.getRecipient().getLocation());
						event.getRecipient().setLocation(event.getSender().getLocation());
						EssentialCmds.teleportingPlayers.remove(event.getRecipient().getUniqueId());
					}
				}
				finally
				{
					EssentialCmds.timings.teleportCooldown().stopTiming();
				}
			}).delay(Utils.getTeleportCooldown(), TimeUnit.SECONDS).name("EssentialCmds - TPA Timer").submit(plugin);
		}
		else
		{
			try
			{
				EssentialCmds.timings.teleportPlayer().startTiming();

				event.getRecipient().sendMessage(Text.of(TextColors.GREEN, senderName, TextColors.WHITE, " accepted your TPA Request."));
				Utils.setLastTeleportOrDeathLocation(event.getRecipient().getUniqueId(), event.getRecipient().getLocation());
				event.getRecipient().setLocation(event.getSender().getLocation());
			}
			finally
			{
				EssentialCmds.timings.teleportPlayer().stopTiming();
			}
		}
	}

	@Listener
	public void tpaHereAcceptEventHandler(TPAHereAcceptEvent event)
	{
		String recipientName = event.getRecipient().getName();

		if (Utils.isTeleportCooldownEnabled() && !event.getSender().hasPermission("essentialcmds.teleport.cooldown.override"))
		{
			EssentialCmds.teleportingPlayers.add(event.getSender().getUniqueId());
			event.getSender().sendMessage(Text.of(TextColors.GREEN, recipientName, TextColors.WHITE, " accepted your TPA Here Request. Please wait " + Utils.getTeleportCooldown() + " seconds."));

			Sponge.getScheduler().createTaskBuilder().execute(() -> {
				try
				{
					EssentialCmds.timings.teleportCooldown().startTiming();

					if (EssentialCmds.teleportingPlayers.contains(event.getSender().getUniqueId()))
					{
						Utils.setLastTeleportOrDeathLocation(event.getSender().getUniqueId(), event.getSender().getLocation());
						event.getSender().setLocation(event.getRecipient().getLocation());
						EssentialCmds.teleportingPlayers.remove(event.getSender().getUniqueId());
					}
				}
				finally
				{
					EssentialCmds.timings.teleportCooldown().stopTiming();
				}
			}).delay(Utils.getTeleportCooldown(), TimeUnit.SECONDS).name("EssentialCmds - TPA Timer").submit(plugin);
		}
		else
		{
			try
			{
				EssentialCmds.timings.teleportPlayer().startTiming();

				event.getSender().sendMessage(Text.of(TextColors.GREEN, recipientName, TextColors.WHITE, " accepted your TPA Here Request."));
				Utils.setLastTeleportOrDeathLocation(event.getSender().getUniqueId(), event.getSender().getLocation());
				event.getSender().setLocation(event.getRecipient().getLocation());
			}
			finally
			{
				EssentialCmds.timings.teleportPlayer().stopTiming();
			}
		}
	}

	@Listener
	public void tpaHereEventHandler(TPAHereEvent event)
	{
		String senderName = event.getSender().getName();
		event.getRecipient().sendMessage(Text.of(TextColors.BLUE, senderName, TextColors.GOLD, " has requested for you to teleport to them.", TextColors.RED, " You have 10 seconds to do /tpaccept to accept the request"));

		// Adds Invite to List
		final PendingInvitation invite = new PendingInvitation(event.getSender(), event.getRecipient());
		invite.isTPAHere = true;
		EssentialCmds.pendingInvites.add(invite);

		// Removes Invite after 10 Seconds
		Sponge.getScheduler().createTaskBuilder().execute(() -> {
			if (EssentialCmds.pendingInvites.contains(invite))
			{
				EssentialCmds.pendingInvites.remove(invite);
			}
		}).delay(10, TimeUnit.SECONDS).name("EssentialCmds - Remove Pending Invite").submit(plugin);
	}
}
