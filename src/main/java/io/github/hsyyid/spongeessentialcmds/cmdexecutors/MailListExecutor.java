package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import io.github.hsyyid.spongeessentialcmds.utils.Mail;
import io.github.hsyyid.spongeessentialcmds.utils.PaginatedList;
import io.github.hsyyid.spongeessentialcmds.utils.Utils;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextBuilder;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.source.CommandBlockSource;
import org.spongepowered.api.util.command.source.ConsoleSource;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class MailListExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		if(src instanceof Player)
		{
			Player player = (Player) src;
			ArrayList<Mail> mail = Utils.getMail();

			ArrayList<Mail> myMail = (ArrayList<Mail>) mail.stream().filter(m -> m.getRecipientName().equals(player.getName())).collect(Collectors.toList());

			if(myMail.isEmpty())
			{
				player.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You have no new mail!"));
				return CommandResult.success();
			}

			Optional<Integer> arguments = ctx.<Integer>getOne("page no");
			int pgNo;

			if (arguments.isPresent())
			{
				pgNo = arguments.get();
			} else
			{
				pgNo = 1;
			}

			//Add List
			PaginatedList pList = new PaginatedList("/listmail");
			for (Mail newM : myMail)
			{
				String name = "New mail from " + newM.getSenderName();

				Text item = Texts.builder(name)
						.onClick(TextActions.runCommand("/readmail " + (myMail.indexOf(newM))))
						.onHover(TextActions.showText(Texts.of(TextColors.WHITE, "Read mail from ", TextColors.GOLD, newM.getSenderName())))
						.color(TextColors.DARK_AQUA)
						.style(TextStyles.UNDERLINE)
						.build();

				pList.add(item);
			}
			pList.setItemsPerPage(10);
			//Header
			TextBuilder header = Texts.builder();
			header.append(Texts.of(TextColors.GREEN, "------------"));
			header.append(Texts.of(TextColors.GREEN, " Showing Mail page " + pgNo + " of " + pList.getTotalPages() + " "));
			header.append(Texts.of(TextColors.GREEN, "------------"));

			pList.setHeader(header.build());
			//Send List
			src.sendMessage(pList.getPage(pgNo));
		} else if(src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /maillist!"));
		} else if(src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /mailist!"));
		}

		return CommandResult.success();
	}
}