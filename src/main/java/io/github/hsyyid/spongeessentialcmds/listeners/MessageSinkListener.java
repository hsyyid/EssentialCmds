package io.github.hsyyid.spongeessentialcmds.listeners;

import io.github.hsyyid.spongeessentialcmds.Main;
import io.github.hsyyid.spongeessentialcmds.utils.Mute;
import io.github.hsyyid.spongeessentialcmds.utils.Utils;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.MessageSinkEvent;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.option.OptionSubject;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.TextMessageException;

public class MessageSinkListener
{
	@SuppressWarnings("deprecation")
	@Listener
	public void onMessage(MessageSinkEvent event)
	{
		if (event.getCause().first(Player.class).isPresent())
		{
			Player player = event.getCause().first(Player.class).get();

			for (Mute mute : Main.muteList)
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
				
				if(!prefix.equals(""))
				{
					prefix = prefix.replaceAll("&", "\u00A7");
					original = original.replaceFirst("<", ("<" + prefix + " "));

					if (!(player.hasPermission("color.chat.use")))
					{
						event.setMessage(Texts.of(original));
					}
				}
				
				String nick = optionSubject.getOption("nick").orElse("");

				if(!nick.equals(""))
				{
					System.out.println("test: " + nick);
					original = original.replaceFirst(player.getName(), nick);
					event.setMessage(Texts.of(original));
				}
			}

			original = original.replaceFirst("<", Utils.getFirstChatCharReplacement());
			original = original.replaceFirst(">", "\u00A7f" + Utils.getLastChatCharReplacement());

			if (!(player.hasPermission("color.chat.use")))
			{
				event.setMessage(Texts.of(original));
			}

			if (player.hasPermission("color.chat.use"))
			{
				try
				{
					Text newMessage = Texts.legacy('&').from(original);
					event.setMessage(newMessage);
				}
				catch (TextMessageException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
