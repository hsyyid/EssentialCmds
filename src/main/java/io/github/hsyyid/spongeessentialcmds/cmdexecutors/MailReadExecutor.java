package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import io.github.hsyyid.spongeessentialcmds.utils.Mail;
import io.github.hsyyid.spongeessentialcmds.utils.Utils;

import java.util.ArrayList;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.source.CommandBlockSource;
import org.spongepowered.api.util.command.source.ConsoleSource;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class MailReadExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		int number = ctx.<Integer>getOne("mail no").get();
		if(src instanceof Player)
		{
			Player player = (Player) src;
			
			ArrayList<Mail> mail = Utils.getMail();

			ArrayList<Mail> myMail = new ArrayList<Mail>();

			for(Mail m : mail)
			{
				if(m.getRecipientName().equals(player.getName()))
				{
					myMail.add(m);
				}
			}

			if(myMail.isEmpty())
			{
				player.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "You have no new mail!"));
				return CommandResult.success();
			}
			
			try
			{
				Mail m = myMail.get(number);
				Utils.removeMail(m);
				player.sendMessage(Texts.of(TextColors.GOLD, "[Mail]: Message from ", TextColors.WHITE, m.getSenderName() + ": ", TextColors.GRAY, m.getMessage()));
			}
			catch(Exception e)
			{
				player.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "That mail does not exist!"));
			}
			
		}
		else if(src instanceof ConsoleSource) {
			src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use /readmail!"));
		}
		else if(src instanceof CommandBlockSource) {
			src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use /readmail!"));
		}
		return CommandResult.success();
	}
}