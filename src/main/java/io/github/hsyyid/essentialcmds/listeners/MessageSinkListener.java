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

import io.github.hsyyid.essentialcmds.utils.AFK;
import io.github.hsyyid.essentialcmds.utils.Utils;

import io.github.hsyyid.essentialcmds.EssentialCmds;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.MessageSinkEvent;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.option.OptionSubject;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.TextMessageException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class MessageSinkListener
{
	@SuppressWarnings("deprecation")
	@Listener
	public void onMessage(MessageSinkEvent.Chat event)
	{
		if (event.getCause().first(Player.class).isPresent())
		{
			Player player = event.getCause().first(Player.class).get();
			String message = Texts.toPlain(event.getMessage());

			if (message.contains("http://") || message.contains("https://"))
			{
				if (player.hasPermission("essentialcmds.link.chat"))
				{
					String foundLink;

					if (message.substring(message.indexOf("http")).contains(" "))
					{
						foundLink = message.substring(message.indexOf("http"), message.indexOf(" ", message.indexOf("http")));
					}
					else
					{
						foundLink = message.substring(message.indexOf("http"));
					}

					try
					{
						Text newMessage = Texts.builder()
							.append(event.getMessage())
							.onClick(TextActions.openUrl(new URL(foundLink)))
							.build();

						event.setMessage(newMessage);
					}
					catch (MalformedURLException e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					player.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You don't have permission to send links."));
					event.setCancelled(true);
					return;
				}
			}
		}
		else
		{
			String message = Texts.toPlain(event.getMessage());

			if (message.contains("http://") || message.contains("https://"))
			{
				String foundLink;

				if (message.substring(message.indexOf("http")).contains(" "))
				{
					foundLink = message.substring(message.indexOf("http"), message.indexOf(" ", message.indexOf("http")));
				}
				else
				{
					foundLink = message.substring(message.indexOf("http"));
				}

				try
				{
					Text newMessage = Texts.builder()
						.append(event.getMessage())
						.onClick(TextActions.openUrl(new URL(foundLink)))
						.build();

					event.setMessage(newMessage);
				}
				catch (MalformedURLException e)
				{
					e.printStackTrace();
				}
			}
		}

		if (event.getCause().first(Player.class).isPresent())
		{
			Player player = event.getCause().first(Player.class).get();

			if (EssentialCmds.recentlyJoined.contains(player))
			{
				EssentialCmds.recentlyJoined.remove(player);
				AFK removeAFK = null;

				for (AFK a : EssentialCmds.afkList)
				{
					if (player.getUniqueId().equals(a.getPlayer().getUniqueId()))
					{
						removeAFK = a;
						break;
					}
				}

				if (removeAFK != null)
				{
					EssentialCmds.afkList.remove(removeAFK);
				}
			}
			else
			{
				AFK afk = new AFK(player, System.currentTimeMillis());
				AFK removeAFK = null;

				for (AFK a : EssentialCmds.afkList)
				{
					if (player.getUniqueId().equals(a.getPlayer().getUniqueId()))
					{
						removeAFK = a;
						break;
					}
				}

				if (removeAFK != null)
				{
					if (removeAFK.getAFK())
					{
						for (Player p : EssentialCmds.getEssentialCmds().getGame().getServer().getOnlinePlayers())
						{
							p.sendMessage(Texts.of(TextColors.BLUE, player.getName(), TextColors.GOLD, " is no longer AFK."));
						}
					}

					EssentialCmds.afkList.remove(removeAFK);
				}

				EssentialCmds.afkList.add(afk);
			}
			
			for (UUID mutedUUID : EssentialCmds.muteList)
			{
				if (mutedUUID.equals(player.getUniqueId()))
				{
					player.sendMessage(Texts.of(TextColors.RED, "You have been muted."));
					event.setCancelled(true);
					return;
				}
			}

			String original = Texts.toPlain(event.getMessage());
			Subject subject = player.getContainingCollection().get(player.getIdentifier());

			if (subject instanceof OptionSubject)
			{
				OptionSubject optionSubject = (OptionSubject) subject;

				String prefix = optionSubject.getOption("prefix").orElse("");

				if (!prefix.equals(""))
				{
					prefix = prefix.replaceAll("&", "\u00A7");
					original = original.replaceFirst("<", ("<" + prefix + " "));

					if (!(player.hasPermission("essentialcmds.color.chat.use")))
					{
						event.setMessage(Texts.builder()
							.append(Texts.of(original))
							.onClick(event.getMessage().getClickAction().orElse(null))
							.style(event.getMessage().getStyle())
							.onHover(event.getMessage().getHoverAction().orElse(null))
							.build());
					}
				}
				
				String suffix = optionSubject.getOption("suffix").orElse("");

				if (!suffix.equals(""))
				{
					suffix = suffix.replaceAll("&", "\u00A7");
					original = original.replaceFirst(">", (suffix + ">"));

					if (!(player.hasPermission("essentialcmds.color.chat.use")))
					{
						event.setMessage(Texts.builder()
							.append(Texts.of(original))
							.onClick(event.getMessage().getClickAction().orElse(null))
							.style(event.getMessage().getStyle())
							.onHover(event.getMessage().getHoverAction().orElse(null))
							.build());
					}
				}

				String nick = optionSubject.getOption("nick").orElse("");

				if (!nick.equals(""))
				{
					original = original.replaceFirst(player.getName(), nick);
					event.setMessage(Texts.builder()
						.append(Texts.of(original))
						.style(event.getMessage().getStyle())
						.onClick(event.getMessage().getClickAction().orElse(null))
						.onHover(event.getMessage().getHoverAction().orElse(null))
						.build());
				}
			}

			original = original.replaceFirst("<", Utils.getFirstChatCharReplacement());
			original = original.replaceFirst(">", "\u00A7f" + Utils.getLastChatCharReplacement());

			if (!(player.hasPermission("essentialcmds.color.chat.use")))
			{
				event.setMessage(Texts.builder()
					.append(Texts.of(original))
					.style(event.getMessage().getStyle())
					.onClick(event.getMessage().getClickAction().orElse(null))
					.onHover(event.getMessage().getHoverAction().orElse(null))
					.build());
			}

			if (player.hasPermission("essentialcmds.color.chat.use"))
			{
				try
				{
					Text newMessage = Texts.legacy('&').from(original);
					event.setMessage(Texts.builder()
						.append(newMessage)
						.style(event.getMessage().getStyle())
						.onClick(event.getMessage().getClickAction().orElse(null))
						.onHover(event.getMessage().getHoverAction().orElse(null))
						.build());
				}
				catch (TextMessageException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
