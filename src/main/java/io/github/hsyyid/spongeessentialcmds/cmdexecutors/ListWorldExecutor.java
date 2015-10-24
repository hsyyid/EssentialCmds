package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import io.github.hsyyid.spongeessentialcmds.EssentialCmds;
import io.github.hsyyid.spongeessentialcmds.utils.PaginatedList;
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
import org.spongepowered.api.util.command.spec.CommandExecutor;
import org.spongepowered.api.world.World;

import java.util.ArrayList;
import java.util.Optional;

public class ListWorldExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		ArrayList<String> worlds = new ArrayList<>();

		for (World world : EssentialCmds.game.getServer().getWorlds())
		{
			if (world.getProperties().isEnabled())
				worlds.add(world.getName());
		}

		Optional<Integer> optionalPageNo = ctx.<Integer> getOne("page no");
		int pageNo;

		if (optionalPageNo.isPresent())
		{
			pageNo = optionalPageNo.get();
		}
		else
		{
			pageNo = 1;
		}

		PaginatedList pList = new PaginatedList("/worlds");

		for (String name : worlds)
		{
			Text item = Texts.builder(name)
				.onClick(TextActions.runCommand("/tpworld " + name))
				.onHover(TextActions.showText(Texts.of(TextColors.WHITE, "Teleport to world ", TextColors.GOLD, name)))
				.color(TextColors.DARK_AQUA)
				.style(TextStyles.UNDERLINE)
				.build();

			pList.add(item);
		}

		pList.setItemsPerPage(10);

		TextBuilder header = Texts.builder();
		header.append(Texts.of(TextColors.GREEN, "------------"));
		header.append(Texts.of(TextColors.GREEN, " Showing Worlds page " + pageNo + " of " + pList.getTotalPages() + " "));
		header.append(Texts.of(TextColors.GREEN, "------------"));

		pList.setHeader(header.build());

		src.sendMessage(pList.getPage(pageNo));

		return CommandResult.success();
	}
}
