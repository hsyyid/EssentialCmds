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
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.option.OptionSubject;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class MessageSinkListener
{
	@Listener
	public void onMessage(MessageChannelEvent.Chat event)
	{
		if (event.getCause().first(Player.class).isPresent())
		{
			Player player = event.getCause().first(Player.class).get();
			String message = event.getMessage().orElse(Text.of()).toPlain();

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
						Text newMessage = Text.builder().append(event.getMessage().orElse(Text.of())).onClick(TextActions.openUrl(new URL(foundLink))).build();

						event.setMessage(newMessage);
					}
					catch (MalformedURLException e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					player.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You don't have permission to send links."));
					event.setCancelled(true);
					return;
				}
			}
		}
		else
		{
			String message = event.getMessage().orElse(Text.of()).toPlain();

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
					Text newMessage = Text.builder().append(event.getMessage().orElse(Text.of())).onClick(TextActions.openUrl(new URL(foundLink))).build();

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
							p.sendMessage(Text.of(TextColors.BLUE, player.getName(), TextColors.GOLD, " is no longer AFK."));
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
					player.sendMessage(Text.of(TextColors.RED, "You have been muted."));
					event.setCancelled(true);
					return;
				}
			}

			String original = event.getMessage().orElse(Text.of()).toPlain();
			original = original.replaceFirst("<", Utils.getFirstChatCharReplacement());
			original = original.replaceFirst(">", Utils.getLastChatCharReplacement());

			Subject subject = player.getContainingCollection().get(player.getIdentifier());
			String prefix = "";
			String suffix = "";

			if (subject instanceof OptionSubject)
			{
				OptionSubject optionSubject = (OptionSubject) subject;

				prefix = optionSubject.getOption("prefix").orElse("");
				suffix = optionSubject.getOption("suffix").orElse("");
			}
			
			String nick = Utils.getNick(player);

			original = original.replaceFirst(Utils.getFirstChatCharReplacement(), (Utils.getFirstChatCharReplacement() + prefix));
			String prefixInOriginal = original.substring(0, prefix.length() + 1);

			original = original.replaceFirst(Utils.getLastChatCharReplacement(), (suffix + Utils.getLastChatCharReplacement()));
			String suffixInOriginal = original.substring(original.indexOf(player.getName()) + player.getName().length(), original.indexOf(Utils.getLastChatCharReplacement()));

			original = original.replaceFirst(player.getName(), nick);
			String playerName = original.substring(prefixInOriginal.length(), original.indexOf(nick) + nick.length());
			
			String restOfOriginal = original.substring(original.indexOf(Utils.getLastChatCharReplacement()), original.length());
			
			if (!(player.hasPermission("essentialcmds.color.chat.use")))
			{
				event.setMessage(Text.builder()
					.append(TextSerializers.formattingCode('&').deserialize(prefixInOriginal))
					.append(TextSerializers.formattingCode('&').deserialize(playerName))
					.append(TextSerializers.formattingCode('&').deserialize(suffixInOriginal))
					.append(Text.of(TextColors.RESET))
					.append(Text.of(restOfOriginal))
					.onClick(event.getMessage().orElse(Text.of()).getClickAction().orElse(null))
					.style(event.getMessage().orElse(Text.of()).getStyle())
					.onHover(event.getMessage().orElse(Text.of()).getHoverAction().orElse(null))
					.build());
			}
			else
			{
				event.setMessage(Text.builder()
					.append(TextSerializers.formattingCode('&').deserialize(prefixInOriginal))
					.append(TextSerializers.formattingCode('&').deserialize(playerName))
					.append(TextSerializers.formattingCode('&').deserialize(suffixInOriginal))
					.append(Text.of(TextColors.RESET))
					.append(TextSerializers.formattingCode('&').deserialize(restOfOriginal))
					.onClick(event.getMessage().orElse(Text.of()).getClickAction().orElse(null))
					.style(event.getMessage().orElse(Text.of()).getStyle())
					.onHover(event.getMessage().orElse(Text.of()).getHoverAction().orElse(null))
					.build());
			}
		}
	}
}
