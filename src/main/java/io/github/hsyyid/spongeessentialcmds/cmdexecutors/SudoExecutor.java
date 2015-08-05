package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import io.github.hsyyid.spongeessentialcmds.Main;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.service.command.CommandService;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.source.CommandBlockSource;
import org.spongepowered.api.util.command.source.ConsoleSource;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class SudoExecutor implements CommandExecutor
{
	Game game = Main.game;

	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Player p = ctx.<Player> getOne("player").get();
		String command = ctx.<String> getOne("command").get();
		if (src instanceof Player)
		{
			Player player = (Player) src;
			CommandService cmdService = game.getCommandDispatcher();
			if(!(p.hasPermission("sudo.exempt")))
			{
				cmdService.process(p, command);
				player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.GOLD, "Forcing " + p.getName() + " to run /" + command));
				p.sendMessage(Texts.of(TextColors.GOLD, "[Sudo]: ", TextColors.WHITE, player.getName() + " has forced you to run /" + command));
			}
			else
			{
				player.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "This player cannot is exempt from sudo!"));
			}
		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /sudo!"));
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /sudo!"));
		}
		return CommandResult.success();
	}
}
