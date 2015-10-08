package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import io.github.hsyyid.spongeessentialcmds.utils.PaginatedList;
import io.github.hsyyid.spongeessentialcmds.utils.Utils;

import java.util.ArrayList;

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

import java.util.Optional;

public class ListHomeExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		if (src instanceof Player)
		{
			Player player = (Player) src;
			
			ArrayList<String> homes = null;
			try
			{
				homes = Utils.getHomes(player.getUniqueId());
			}
			catch (NullPointerException e)
			{
				player.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must first set a home to list your homes!"));
				return CommandResult.success();
			}
			
			Optional<Integer> arguments = ctx.<Integer> getOne("page no");

			int pgNo = 1;
			
			if (arguments != Optional.<Integer> empty())
			{
				pgNo = arguments.get();
			}
			else
			{
				pgNo = 1;
			}
			
			// Add List
			PaginatedList pList = new PaginatedList("/homes");
			for (String name : homes)
			{
				Text item = Texts.builder(name)
					.onClick(TextActions.runCommand("/home " + name))
					.onHover(TextActions.showText(Texts.of(TextColors.WHITE, "Teleport to home ", TextColors.GOLD, name)))
					.color(TextColors.DARK_AQUA)
					.style(TextStyles.UNDERLINE)
					.build();

				pList.add(item);
			}
			pList.setItemsPerPage(10);
			// Header
			TextBuilder header = Texts.builder();
			header.append(Texts.of(TextColors.GREEN, "------------"));
			header.append(Texts.of(TextColors.GREEN, " Showing Homes page " + pgNo + " of " + pList.getTotalPages() + " "));
			header.append(Texts.of(TextColors.GREEN, "------------"));

			pList.setHeader(header.build());
			// Send List
			src.sendMessage(pList.getPage(pgNo));

		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /homes!"));
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /homes!"));
		}

		return CommandResult.success();
	}
}
