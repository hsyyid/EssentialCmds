package io.github.hsyyid.essentialcmds.cmdexecutors;

import io.github.hsyyid.essentialcmds.events.MailSendEvent;

import io.github.hsyyid.essentialcmds.EssentialCmds;
import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class MailExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		String message = ctx.<String> getOne("message").get();
		String playerName = ctx.<String> getOne("player").get();

		if (src instanceof Player)
		{
			Player p = (Player) src;
			
			if(p.getName().equalsIgnoreCase(playerName))
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You cannot send mail to yourself!"));
				return CommandResult.success();
			}
			
			Game game = EssentialCmds.game;

			MailSendEvent event = new MailSendEvent(p, playerName, message);
			game.getEventManager().post(event);

			p.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Sent Mail to " + playerName));
		}
		else
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be an in-game player to send mail!"));
		}
		
		return CommandResult.success();
	}
}
