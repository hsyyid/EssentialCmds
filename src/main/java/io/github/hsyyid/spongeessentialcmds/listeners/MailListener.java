package io.github.hsyyid.spongeessentialcmds.listeners;

import io.github.hsyyid.spongeessentialcmds.SpongeEssentialCmds;
import io.github.hsyyid.spongeessentialcmds.events.MailSendEvent;
import io.github.hsyyid.spongeessentialcmds.utils.Utils;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

public class MailListener
{
	@Listener
	public void onMailSend(MailSendEvent event)
	{
		String recipientName = event.getRecipientName();

		if (SpongeEssentialCmds.game.getServer().getPlayer(recipientName).isPresent())
		{
			Utils.addMail(event.getSender().getName(), recipientName, event.getMessage());
			SpongeEssentialCmds.game.getServer()
				.getPlayer(recipientName)
				.get()
				.sendMessage(
					Texts.of(TextColors.GOLD, "[Mail]: ", TextColors.GRAY, "You have received new mail from " + event.getSender().getName()
						+ " do ", TextColors.RED, "/listmail!"));
		}
		else
		{
			Utils.addMail(event.getSender().getName(), recipientName, event.getMessage());
		}
	}
}
