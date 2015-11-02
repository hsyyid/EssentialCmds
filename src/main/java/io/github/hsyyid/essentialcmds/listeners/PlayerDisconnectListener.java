package io.github.hsyyid.essentialcmds.listeners;

import org.spongepowered.api.entity.living.player.Player;
import io.github.hsyyid.essentialcmds.utils.Utils;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.TextMessageException;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

public class PlayerDisconnectListener
{
	@SuppressWarnings("deprecation")
	@Listener
	public void onPlayerDisconnect(ClientConnectionEvent.Disconnect event)
	{
		Player player = event.getTargetEntity();
		String disconnectMessage = Utils.getDisconnectMessage();

		if (disconnectMessage != null && !disconnectMessage.equals(""))
		{
			disconnectMessage = disconnectMessage.replaceAll("@p", player.getName());
			Text newMessage = null;
			
			try
			{
				newMessage = Texts.legacy('&').from(disconnectMessage);
			}
			catch (TextMessageException e)
			{
				System.out.println("Error! A TextMessageException was caught when trying to format the login message!");
			}
			
			if (newMessage != null)
			{
				event.setMessage(newMessage);
			}
		}
	}
}
