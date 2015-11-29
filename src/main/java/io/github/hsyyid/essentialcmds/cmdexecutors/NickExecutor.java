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

import java.util.Optional;

public class NickExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Player> target = ctx.getOne("player");

		String nick = ctx.<String> getOne("nick").get();
		nick = nick.replace("&", "\u00A7");

		if (!target.isPresent())
		{
			if (src instanceof Player)
			{
				Player player = (Player) src;

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
			else
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be a in-game player to do /nick!"));
			}
		}
		else
		{
			if (src.hasPermission("essentialcmds.nick.others"))
			{
				Player player = target.get();
				Subject subject = player.getContainingCollection().get(player.getIdentifier());

				if (subject instanceof OptionSubject)
				{
					OptionSubject optionSubject = (OptionSubject) subject;
					optionSubject.getTransientSubjectData().setOption(player.getActiveContexts(), "nick", nick);
				}
				else
				{
					src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Your server doesn't currently support /nick!"));
				}
			}
			else
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You do not have permission to make changes to other player's nicknames.!"));
			}
		}

		return CommandResult.success();
	}
}
