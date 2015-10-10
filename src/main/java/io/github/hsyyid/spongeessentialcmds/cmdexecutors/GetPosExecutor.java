package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.source.CommandBlockSource;
import org.spongepowered.api.util.command.source.ConsoleSource;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import java.util.Optional;

public class GetPosExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Player> optionalPlayer = ctx.<Player>getOne("player");

		if(!optionalPlayer.isPresent())
		{
			if(src instanceof Player)
			{
				Player player = (Player) src;
				player.sendMessage(Texts.of(TextColors.GOLD, "Your current position is: ", TextColors.GRAY, player.getLocation().getX() + ", " + player.getLocation().getY() + ", " + player.getLocation().getZ()));

			} else if(src instanceof ConsoleSource)
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /getpos!"));
			} else if(src instanceof CommandBlockSource)
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /getpos!"));
			}
		} else if(src.hasPermission("getpos.others"))
		{
			Player player = optionalPlayer.get();
			src.sendMessage(Texts.of(TextColors.GOLD, player.getName() + "'s current position is: ", TextColors.GRAY, player.getLocation().getX() + ", " + player.getLocation().getY() + ", " + player.getLocation().getZ()));
		} else
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You don't have permission to get the positon of others!"));
		}

		return CommandResult.success();
	}
}
