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
import io.github.hsyyid.essentialcmds.utils.Mail;
import io.github.hsyyid.essentialcmds.utils.Utils;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

import java.text.SimpleDateFormat;
import java.time.Instant;
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

		if (this.compareInstants(player.getJoinData().firstPlayed().get(), player.getJoinData().lastPlayed().get()))
		{
			Transform<World> spawn = Utils.getFirstSpawn();

			if (spawn != null)
			{
				if (!Objects.equals(player.getWorld().getUniqueId(), spawn.getExtent().getUniqueId()))
				{
					player.transferToWorld(spawn.getExtent().getUniqueId(), spawn.getPosition());
					player.setTransform(spawn);
				}
				else
				{
					player.setTransform(spawn);
				}
			}
			else
			{
				spawn = Utils.getSpawn();

				if (spawn != null)
				{
					if (!Objects.equals(player.getWorld().getUniqueId(), spawn.getExtent().getUniqueId()))
					{
						player.transferToWorld(spawn.getExtent().getUniqueId(), spawn.getPosition());
						player.setTransform(spawn);
					}
					else
					{
						player.setTransform(spawn);
					}
				}
			}

			Text firstJoinMsg = Utils.getFirstJoinMsg(player.getName());
			MessageChannel.TO_ALL.send(firstJoinMsg);
		}

		if (EssentialCmds.flyingPlayers.contains(player.getUniqueId()))
		{
			player.offer(Keys.CAN_FLY, true);

			if (player.getLocation().sub(0, 1, 0).getBlockType() == BlockTypes.AIR)
			{
				player.offer(Keys.IS_FLYING, true);
			}
		}

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		Utils.setLastTimePlayerJoined(player.getUniqueId(), format.format(cal.getTime()));
		player.sendMessage(Utils.getJoinMsg());

		ArrayList<Mail> newMail = (ArrayList<Mail>) Utils.getMail().stream().filter(mail -> mail.getRecipientName().equals(player.getName())).collect(Collectors.toList());

		if (newMail.size() > 0)
		{
			player.sendMessage(Text.of(TextColors.GOLD, "[Mail]: ", TextColors.GRAY, "While you were away, you received new mail to view it do ", TextColors.RED, "/listmail"));
		}

		EssentialCmds.recentlyJoined.add(event.getTargetEntity());

		// Remove previous AFK, so player does not join as AFK.
		if (EssentialCmds.afkList.containsKey(player.getUniqueId()))
		{
			EssentialCmds.afkList.remove(player.getUniqueId());
		}

		Text loginMessage = Utils.getLoginMessage(player.getName());

		if (loginMessage != null && !loginMessage.equals(""))
		{
			if (loginMessage.isEmpty())
			{
				event.setMessageCancelled(true);
			}
			else
			{
				event.setMessage(loginMessage);
			}
		}

		Utils.savePlayerInventory(player, player.getWorld().getUniqueId());
	}

	public boolean compareInstants(Instant o1, Instant o2)
	{
		int cmp = Long.compare(o1.getEpochSecond(), o2.getEpochSecond());

		if (cmp == 0)
		{
			return true;
		}

		return false;
	}
}
