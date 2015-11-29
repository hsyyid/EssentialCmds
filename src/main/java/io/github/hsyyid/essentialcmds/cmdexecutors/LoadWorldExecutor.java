package io.github.hsyyid.essentialcmds.cmdexecutors;

import io.github.hsyyid.essentialcmds.EssentialCmds;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class LoadWorldExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		String name = ctx.<String> getOne("name").get();

		if (EssentialCmds.game.getServer().loadWorld(name).isPresent())
		{
			src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.GOLD, "World ", TextColors.GRAY, name, TextColors.GOLD, " has been imported."));
		}
		else
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "The world could not be loaded."));
		}

		return CommandResult.success();
	}
}
