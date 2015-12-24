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

import static io.github.hsyyid.essentialcmds.EssentialCmds.getEssentialCmds;

import io.github.hsyyid.essentialcmds.EssentialCmds;
import io.github.hsyyid.essentialcmds.events.TPAAcceptEvent;
import io.github.hsyyid.essentialcmds.events.TPAEvent;
import io.github.hsyyid.essentialcmds.events.TPAHereAcceptEvent;
import io.github.hsyyid.essentialcmds.events.TPAHereEvent;
import io.github.hsyyid.essentialcmds.utils.PendingInvitation;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import java.util.concurrent.TimeUnit;

public class TPAListener
{

	private Game game = getEssentialCmds().getGame();

	@Listener
	public void tpaEventHandler(TPAEvent event)
	{
		String senderName = event.getSender().getName();
		event.getRecipient().sendMessage(Texts.of(TextColors.BLUE, "TPA Request From: ", 
			TextColors.GOLD, senderName + ".", TextColors.RED, " You have 10 seconds to do /tpaccept to accept the request"));

		// Adds Invite to List
		final PendingInvitation invite = new PendingInvitation(event.getSender(), event.getRecipient());
		EssentialCmds.pendingInvites.add(invite);

		// Removes Invite after 10 Seconds
		Scheduler scheduler = game.getScheduler();
		Task.Builder taskBuilder = scheduler.createTaskBuilder();

		taskBuilder.execute(() -> {
			if (EssentialCmds.pendingInvites.contains(invite))
			{
				EssentialCmds.pendingInvites.remove(invite);
			}
		}).delay(10, TimeUnit.SECONDS).name("EssentialCmds - Remove Pending Invite").submit(game.getPluginManager().getPlugin("EssentialCmds").get().getInstance().get());
	}

	@Listener
	public void tpaAcceptEventHandler(TPAAcceptEvent event)
	{
		String senderName = event.getSender().getName();
		event.getRecipient().sendMessage(Texts.of(TextColors.GREEN, senderName, TextColors.WHITE, " accepted your TPA Request."));
		event.getRecipient().setLocation(event.getSender().getLocation());
	}

	@Listener
	public void tpaHereAcceptEventHandler(TPAHereAcceptEvent event)
	{
		String recipientName = event.getRecipient().getName();
		event.getSender().sendMessage(Texts.of(TextColors.GREEN, recipientName, TextColors.WHITE, " accepted your TPA Here Request."));
		event.getSender().setLocation(event.getRecipient().getLocation());
	}

	@Listener
	public void tpaHereEventHandler(TPAHereEvent event)
	{
		String senderName = event.getSender().getName();
		event.getRecipient().sendMessage(Texts.of(TextColors.BLUE, senderName, TextColors.GOLD, " has requested for you to teleport to them.", TextColors.RED, " You have 10 seconds to do /tpaccept to accept the request"));

		// Adds Invite to List
		final PendingInvitation invite = new PendingInvitation(event.getSender(), event.getRecipient());
		invite.isTPAHere = true;
		EssentialCmds.pendingInvites.add(invite);

		// Removes Invite after 10 Seconds
		Scheduler scheduler = game.getScheduler();
		Task.Builder taskBuilder = scheduler.createTaskBuilder();

		taskBuilder.execute(() -> {
			if (EssentialCmds.pendingInvites.contains(invite))
			{
				EssentialCmds.pendingInvites.remove(invite);
			}
		}).delay(10, TimeUnit.SECONDS).name("EssentialCmds - Remove Pending Invite").submit(game.getPluginManager().getPlugin("EssentialCmds").get().getInstance());
	}
}
