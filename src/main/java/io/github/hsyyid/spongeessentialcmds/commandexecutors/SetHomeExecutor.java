package io.github.hsyyid.spongeessentialcmds.commandexecutors;

import io.github.hsyyid.spongeessentialcmds.utils.Utils;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.source.CommandBlockSource;
import org.spongepowered.api.util.command.source.ConsoleSource;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class SetHomeExecutor implements CommandExecutor
{

	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		String homeName = ctx.<String>getOne("home name").get();
		if(src instanceof Player)
		{
			Player player = (Player) src;
			Utils.setHome(player.getUniqueId(), player.getLocation(), homeName);
			src.sendMessage(Texts.of(TextColors.GREEN,"Success: ", TextColors.YELLOW, "Home set."));
		}
		else if(src instanceof ConsoleSource) {
			src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use /sethome!"));
		}
		else if(src instanceof CommandBlockSource) {
			src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use /sethome!"));
		}

		return CommandResult.success();
	}
}
