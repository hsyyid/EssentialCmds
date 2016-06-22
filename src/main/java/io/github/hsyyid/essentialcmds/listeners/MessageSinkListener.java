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
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.option.OptionSubject;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.ClickAction;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public class MessageSinkListener
{
	@Listener(order = Order.PRE)
	public void onMessage(MessageChannelEvent.Chat event)
	{
		Optional<ClickAction.OpenUrl> clickAction = Optional.empty();

		if (event.getCause().first(Player.class).isPresent())
		{
			Player player = event.getCause().first(Player.class).get();
			String message = event.getMessage().toPlain();

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
						clickAction = Optional.of(TextActions.openUrl(new URL(foundLink)));
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
			String message = event.getMessage().toPlain();

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
					Text newMessage = Text.builder().append(event.getMessage()).onClick(TextActions.openUrl(new URL(foundLink))).build();
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

				if (EssentialCmds.afkList.containsKey(player.getUniqueId()))
				{
					EssentialCmds.afkList.remove(player.getUniqueId());
				}
			}
			else
			{
				AFK afk = new AFK(System.currentTimeMillis());

				if (EssentialCmds.afkList.containsKey(player.getUniqueId()))
				{
					AFK removeAFK = EssentialCmds.afkList.get(player.getUniqueId());

					if (removeAFK.getAFK())
					{
						if (removeAFK.getAFK())
						{
							if (Utils.shouldAnnounceAFK())
							{
								MessageChannel.TO_ALL.send(Text.of(TextColors.BLUE, player.getName(), TextColors.GOLD, " is no longer AFK."));
							}
						}
					}

					EssentialCmds.afkList.remove(removeAFK);
				}

				EssentialCmds.afkList.put(player.getUniqueId(), afk);
			}

			if (EssentialCmds.muteList.contains(player.getUniqueId()))
			{
				player.sendMessage(Text.of(TextColors.RED, "You have been muted."));
				event.setCancelled(true);
				return;
			}

			StringBuilder original = new StringBuilder(event.getMessage().toPlain());

			Subject subject = player.getContainingCollection().get(player.getIdentifier());
			String prefix = "";
			String prefix2 = "";
			String prefix3 = "";
			String prefix4 = "";
			String prefix5 = "";
			String suffix = "";
			TextColor nameColor = TextColors.WHITE;

			if (subject instanceof OptionSubject)
			{
				OptionSubject optionSubject = (OptionSubject) subject;

				prefix = optionSubject.getOption("prefix").orElse("");
				prefix2 = optionSubject.getOption("prefix2").orElse("");
				prefix3 = optionSubject.getOption("prefix3").orElse("");
				prefix4 = optionSubject.getOption("prefix4").orElse("");
				prefix5 = optionSubject.getOption("prefix5").orElse("");
				suffix = optionSubject.getOption("suffix").orElse("");
				nameColor = Sponge.getRegistry().getType(TextColor.class, optionSubject.getOption("namecolor").orElse("")).orElse(TextColors.WHITE);
			}

			String restOfOriginal = original.substring(original.indexOf(">") + 1, original.length());

			original = original.replace(0, 1, ("<" + prefix));
			String prefixInOriginal = original.substring(0, prefix.length() + 1);

			original = original.replace(original.indexOf(player.getName()) + player.getName().length(), original.indexOf(player.getName()) + player.getName().length() + 1, suffix + ">");
			String suffixInOriginal = original.substring(original.indexOf(player.getName()) + player.getName().length(), original.indexOf(restOfOriginal));

			String nick = Utils.getNick(player);
			original = original.replace(original.indexOf(player.getName()) - 1, original.indexOf(player.getName()) + player.getName().length(), nick);
			String playerName = original.substring(prefixInOriginal.length() - 1, original.indexOf(nick) + nick.length());

			prefixInOriginal = prefixInOriginal.replaceFirst("<", Utils.getFirstChatCharReplacement());

			if (suffixInOriginal.length() != 0)
				suffixInOriginal = suffixInOriginal.substring(0, suffixInOriginal.length() - 1) + Utils.getLastChatCharReplacement();

			if (!player.hasPermission("essentialcmds.color.chat.use"))
			{
				event.setMessage(Text.builder()
					.append(TextSerializers.formattingCode('&').deserialize(prefixInOriginal))
						.append(TextSerializers.formattingCode('&').deserialize(prefix2))
						.append(TextSerializers.formattingCode('&').deserialize(prefix3))
						.append(TextSerializers.formattingCode('&').deserialize(prefix4))
						.append(TextSerializers.formattingCode('&').deserialize(prefix5))
					.append(Text.builder().append(TextSerializers.formattingCode('&').deserialize(playerName)).color(nameColor).build())
					.append(TextSerializers.formattingCode('&').deserialize(suffixInOriginal))
					.append(Text.of(TextColors.RESET))
					.append(Text.of(restOfOriginal))
					.onClick(clickAction.isPresent() ? clickAction.get() : event.getMessage().getClickAction().orElse(null))
					.style(event.getMessage().getStyle())
					.onHover(event.getMessage().getHoverAction().orElse(null))
					.build());
			}
			else
			{
				event.setMessage(Text.builder()
					.append(TextSerializers.formattingCode('&').deserialize(prefixInOriginal))
						.append(TextSerializers.formattingCode('&').deserialize(prefix2))
						.append(TextSerializers.formattingCode('&').deserialize(prefix3))
						.append(TextSerializers.formattingCode('&').deserialize(prefix4))
						.append(TextSerializers.formattingCode('&').deserialize(prefix5))
					.append(Text.builder().append(TextSerializers.formattingCode('&').deserialize(playerName)).color(nameColor).build())
					.append(TextSerializers.formattingCode('&').deserialize(suffixInOriginal))
					.append(Text.of(TextColors.RESET))
					.append(TextSerializers.formattingCode('&').deserialize(restOfOriginal))
					.onClick(clickAction.isPresent() ? clickAction.get() : event.getMessage().getClickAction().orElse(null))
					.style(event.getMessage().getStyle())
					.onHover(event.getMessage().getHoverAction().orElse(null))
					.build());
			}
		}
	}
}
