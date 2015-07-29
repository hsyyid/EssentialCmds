package io.github.hsyyid.spongeessentialcmds.commandexecutors;

import io.github.hsyyid.spongeessentialcmds.Main;
import io.github.hsyyid.spongeessentialcmds.events.TPAEvent;

import org.spongepowered.api.Game;
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

public class TPAExecutor  implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Game game = Main.game;
		Player recipient = ctx.<Player>getOne("player").get();

		if(src instanceof Player)
		{
			Player player = (Player) src;
			if(recipient == player)
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "You cannot teleport to yourself!"));
			}
			else
			{
				game.getEventManager().post(new TPAEvent(player, recipient));
			}
		}
		else if(src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use /tpa!"));
		}
		else if(src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use /tpa!"));
		}
		return CommandResult.success();
	}
}