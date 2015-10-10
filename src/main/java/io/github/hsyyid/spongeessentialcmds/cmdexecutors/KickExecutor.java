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

public class KickExecutor implements CommandExecutor
{

	@SuppressWarnings("deprecation")
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Game game = Main.game;
		Server server = game.getServer();
		Player player = ctx.<Player> getOne("player").get();
		String reason = ctx.<String> getOne("reason").get();

		if (server.getPlayer(player.getUniqueId()).isPresent())
		{
			try
			{
				Text reas = Texts.legacy('&').from(reason);
				Text kickMessage = Texts.of(TextColors.GOLD, src.getName() + " kicked " + player.getName() + " for ", TextColors.RED);
				Text finalKickMessage = Texts.builder().append(kickMessage).append(reas).build();

				for (Player p : server.getOnlinePlayers())
				{
					p.sendMessage(finalKickMessage);
				}

				player.kick(reas);
			}
			catch (TextMessageException e)
			{
				Text kickMessage = Texts.of(TextColors.GOLD, src.getName() + " kicked " + player.getName() + " for ", TextColors.RED);
				Text finalKickMessage = Texts.builder().append(kickMessage).append(Texts.of(reason)).build();

				for (Player p : server.getOnlinePlayers())
				{
					p.sendMessage(finalKickMessage);
				}

				player.kick(Texts.of(reason));
			}

			src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Player kicked."));
		}
		else
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Player doesn't appear to be online!"));
		}

		return CommandResult.success();
	}
}
