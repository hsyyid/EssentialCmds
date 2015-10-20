package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import io.github.hsyyid.spongeessentialcmds.SpongeEssentialCmds;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class MotdExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Game game = SpongeEssentialCmds.game;
		Server server = game.getServer();
		src.sendMessage(Texts.of(TextColors.GOLD, "[MOTD]: ", server.getMotd()));
		return CommandResult.success();
	}
}
