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
				prefix = prefix.replaceAll("&", "\u00A7");
				original = original.replaceFirst("<", ("<" + prefix + " "));

				if (!(player.hasPermission("color.chat.use")))
				{
					event.setMessage(Texts.of(original));
				}
			}

			original = original.replaceFirst("<", Utils.getFirstChatCharReplacement());
			original = original.replaceFirst(">", "\u00A7f" + Utils.getLastChatCharReplacement());

			if (!(player.hasPermission("color.chat.use")))
			{
				event.setMessage(Texts.of(original));
			}

			// OptionSubject optionSubject = (OptionSubject) subject;
			// String prefix = optionSubject.getOption("prefix").or("");
			//
			// if(!(original.contains(prefix)))
			// {
			// Text textPrefix = null;
			//
			// try
			// {
			// textPrefix = Texts.legacy('&').from(prefix + " ");
			// }
			// catch (TextMessageException e)
			// {
			// getLogger().warn("Error! A TextMessageException was caught when trying to format the prefix!");
			// }
			//
			// DisplayNameData data =
			// player.getOrCreate(DisplayNameData.class).get();
			// Optional<Text> name = data.get(Keys.DISPLAY_NAME);
			//
			// if(name.isPresent())
			// {
			// data.set(Keys.DISPLAY_NAME, Texts.of(textPrefix, name.get()));
			// }
			// else
			// {
			// data.set(Keys.DISPLAY_NAME, Texts.of(textPrefix,
			// player.getName()));
			// }
			//
			// player.offer(data);
			// }
			// }

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
