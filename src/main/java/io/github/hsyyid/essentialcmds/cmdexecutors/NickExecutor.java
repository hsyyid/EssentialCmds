package io.github.hsyyid.essentialcmds.cmdexecutors;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.option.OptionSubject;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class NickExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Player player = ctx.<Player> getOne("player").get();
		String nick = ctx.<String> getOne("nick").get();
		nick = nick.replace("&", "\u00A7");

		if (src instanceof Player && ((Player) src).getUniqueId() != player.getUniqueId() && src.hasPermission("essentialcmds.nick.others"))
		{
			Subject subject = player.getContainingCollection().get(player.getIdentifier());

			if (subject instanceof OptionSubject)
			{
				OptionSubject optionSubject = (OptionSubject) subject;
				optionSubject.getTransientSubjectData().setOption(player.getActiveContexts(), "nick", nick);
				src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Nick successfully set!"));
			}
			else
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Your server doesn't currently support /nick!"));
			}
		}
		else if(src instanceof Player && ((Player) src).getUniqueId() != player.getUniqueId())
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You do not have permission to make changes to other player's nicknames.!"));
		}
		else
		{
			Subject subject = player.getContainingCollection().get(player.getIdentifier());

			if (subject instanceof OptionSubject)
			{
				OptionSubject optionSubject = (OptionSubject) subject;
				optionSubject.getTransientSubjectData().setOption(player.getActiveContexts(), "nick", nick);
				src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Nick successfully set!"));
			}
			else
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Your server doesn't currently support /nick!"));
			}
		}

		return CommandResult.success();
	}
}
