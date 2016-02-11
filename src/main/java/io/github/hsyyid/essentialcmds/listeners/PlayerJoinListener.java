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
import io.github.hsyyid.essentialcmds.utils.Mail;
import io.github.hsyyid.essentialcmds.utils.Utils;
import org.spongepowered.api.data.manipulator.mutable.entity.JoinData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class PlayerJoinListener
{
	@Listener
	public void onPlayerJoin(ClientConnectionEvent.Join event)
	{
		Player player = event.getTargetEntity();

		if (player.get(JoinData.class).isPresent() && player.getJoinData().firstPlayed().get().equals(player.getJoinData().lastPlayed().get()))
		{
			Location<World> spawn = Utils.getSpawn();

			if (spawn != null)
			{
				if (!Objects.equals(player.getWorld().getUniqueId(), spawn.getExtent().getUniqueId()))
				{
					player.transferToWorld(spawn.getExtent().getUniqueId(), spawn.getPosition());
				}
				else
				{
					player.setLocation(spawn);
				}
			}

			Text firstJoinMsg = TextSerializers.formattingCode('&').deserialize(Utils.getFirstJoinMsg().replaceAll("@p", player.getName()));
			MessageChannel.TO_ALL.send(firstJoinMsg);
		}

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		Utils.setLastTimePlayerJoined(player.getUniqueId(), format.format(cal.getTime()));
		player.sendMessage(TextSerializers.formattingCode('&').deserialize(Utils.getJoinMsg()));

		ArrayList<Mail> newMail = (ArrayList<Mail>) Utils.getMail().stream().filter(mail -> mail.getRecipientName().equals(player.getName())).collect(Collectors.toList());

		if (newMail.size() > 0)
		{
			player.sendMessage(Text.of(TextColors.GOLD, "[Mail]: ", TextColors.GRAY, "While you were away, you received new mail to view it do ", TextColors.RED, "/listmail"));
		}

		EssentialCmds.recentlyJoined.add(event.getTargetEntity());

		// Remove previous AFK, so player does not join as AFK.
		AFK afkToRemove = null;

		for (AFK afk : EssentialCmds.afkList)
		{
			if (afk.getPlayer().equals(player))
			{
				afkToRemove = afk;
				break;
			}
		}

		if (afkToRemove != null)
		{
			EssentialCmds.afkList.remove(afkToRemove);
		}

		String loginMessage = Utils.getLoginMessage();

		if (loginMessage != null && !loginMessage.equals(""))
		{
			loginMessage = loginMessage.replaceAll("@p", player.getName());
			Text newMessage = TextSerializers.formattingCode('&').deserialize(loginMessage);
			event.setMessage(newMessage);
		}

		// Not working in Sponge yet
		// Subject subject = player.getContainingCollection().get(player.getIdentifier());
		//
		// if (subject instanceof OptionSubject)
		// {
		// OptionSubject optionSubject = (OptionSubject) subject;
		// String prefix = optionSubject.getOption("prefix").orElse("");
		// Text textPrefix = null;
		//
		// try
		// {
		// textPrefix = Text.legacy('&').from(prefix + " ");
		// }
		// catch (TextMessageException e)
		// {
		// System.out.println("Error! A TextMessageException was caught when trying to format the prefix!");
		// }
		//
		// DisplayNameData data = player.getOrCreate(DisplayNameData.class).get();
		// Optional<Text> name = data.get(Keys.DISPLAY_NAME);
		//
		// if (name.isPresent())
		// {
		// data.set(Keys.DISPLAY_NAME, Text.of(textPrefix, name.get()));
		// }
		// else
		// {
		// data.set(Keys.DISPLAY_NAME, Text.of(textPrefix, player.getName()));
		// }
		//
		// player.offer(data);
		// }
		// else
		// {
		// System.out.println("Player is not an instance of OptionSubject!");
		// }
	}
}
