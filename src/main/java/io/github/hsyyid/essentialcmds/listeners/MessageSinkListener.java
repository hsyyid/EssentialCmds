package io.github.hsyyid.essentialcmds.listeners;

import io.github.hsyyid.essentialcmds.utils.Mute;
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

			for (Mute mute : EssentialCmds.muteList)
			{
				if (mute.getUUID().equals(player.getUniqueId().toString()))
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

					if (!(player.hasPermission("color.chat.use")))
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

			if (!(player.hasPermission("color.chat.use")))
			{
				event.setMessage(Texts.builder()
					.append(Texts.of(original))
					.style(event.getMessage().getStyle())
					.onClick(event.getMessage().getClickAction().orElse(null))
					.onHover(event.getMessage().getHoverAction().orElse(null))
					.build());
			}

			if (player.hasPermission("color.chat.use"))
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
