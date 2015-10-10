package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import io.github.hsyyid.spongeessentialcmds.Main;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.TextMessageException;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class BroadcastExecutor implements CommandExecutor
{
	@SuppressWarnings("deprecation")
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		String message = ctx.<String> getOne("message").get();
		Game game = Main.game;
		Server server = game.getServer();

		try
		{
			Text msg = Texts.legacy('&').from(message);
			Text broadcast = Texts.of(TextColors.DARK_GRAY, "[", TextColors.DARK_RED, "Broadcast", TextColors.DARK_GRAY, "]", TextColors.GREEN, " ");
			Text finalBroadcast = Texts.builder().append(broadcast).append(msg).build();

			for (Player player : server.getOnlinePlayers())
			{
				player.sendMessage(finalBroadcast);
			}

			server.getConsole().sendMessage(finalBroadcast);
		}
		catch (TextMessageException e)
		{
			for (Player player : server.getOnlinePlayers())
			{
				player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.DARK_RED, "Broadcast", TextColors.DARK_GRAY, "]", TextColors.GREEN, " " + message));
			}

			server.getConsole().sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.DARK_RED, "Broadcast", TextColors.DARK_GRAY, "]", TextColors.GREEN, " " + message));
		}

		return CommandResult.success();
	}
}
