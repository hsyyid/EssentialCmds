package io.github.hsyyid.spongeessentialcmds.listeners;

import io.github.hsyyid.spongeessentialcmds.EssentialCmds;
import io.github.hsyyid.spongeessentialcmds.utils.AFK;
import io.github.hsyyid.spongeessentialcmds.utils.Mail;
import io.github.hsyyid.spongeessentialcmds.utils.Utils;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class PlayerJoinListener
{
	@Listener
	public void onPlayerJoin(ClientConnectionEvent.Join event)
	{
		Player player = event.getTargetEntity();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		Utils.setLastTimePlayerJoined(player.getUniqueId(), format.format(cal.getTime()));

		String message = Utils.getJoinMsg().replaceAll("&", "\u00A7");
		player.sendMessage(Texts.of(message));

		ArrayList<Mail> newMail =
			(ArrayList<Mail>) Utils.getMail().stream().filter(mail -> mail.getRecipientName().equals(player.getName()))
				.collect(Collectors.toList());

		if (newMail.size() > 0)
		{
			player.sendMessage(Texts.of(TextColors.GOLD, "[Mail]: ", TextColors.GRAY, "While you were away, you received new mail to view it do ",
				TextColors.RED, "/listmail"));
		}

		EssentialCmds.recentlyJoined.add(event.getTargetEntity());

		AFK afkToRemove = null;

		for (AFK afk : EssentialCmds.movementList)
		{
			if (afk.getPlayer().equals(player))
			{
				afkToRemove = afk;
				break;
			}
		}

		if (afkToRemove != null)
		{
			EssentialCmds.movementList.remove(afkToRemove);
		}

		// Not working in Sponge yet
		// Subject subject = player.getContainingCollection().get(player.getIdentifier());
		//
		// if (subject instanceof OptionSubject)
		// {
		// OptionSubject optionSubject = (OptionSubject) subject;
		// String prefix = optionSubject.getOption("prefix").orElse("");
		// Text textPrefix = null;
		//
		// try
		// {
		// textPrefix = Texts.legacy('&').from(prefix + " ");
		// }
		// catch (TextMessageException e)
		// {
		// System.out.println("Error! A TextMessageException was caught when trying to format the prefix!");
		// }
		//
		// DisplayNameData data = player.getOrCreate(DisplayNameData.class).get();
		// Optional<Text> name = data.get(Keys.DISPLAY_NAME);
		//
		// if (name.isPresent())
		// {
		// data.set(Keys.DISPLAY_NAME, Texts.of(textPrefix, name.get()));
		// }
		// else
		// {
		// data.set(Keys.DISPLAY_NAME, Texts.of(textPrefix, player.getName()));
		// }
		//
		// player.offer(data);
		// }
		// else
		// {
		// System.out.println("Player is not an instance of OptionSubject!");
		// }
	}
}
