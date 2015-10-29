package io.github.hsyyid.essentialcmds.listeners;

import io.github.hsyyid.essentialcmds.utils.Utils;

import io.github.hsyyid.essentialcmds.events.MailSendEvent;
import io.github.hsyyid.essentialcmds.EssentialCmds;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

public class MailListener
{
	@Listener
	public void onMailSend(MailSendEvent event)
	{
		String recipientName = event.getRecipientName();

		if (EssentialCmds.game.getServer().getPlayer(recipientName).isPresent())
		{
			Utils.addMail(event.getSender().getName(), recipientName, event.getMessage());
			EssentialCmds.game.getServer()
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
