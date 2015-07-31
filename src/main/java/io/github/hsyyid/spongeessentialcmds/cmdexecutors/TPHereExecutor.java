package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

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

public class TPHereExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
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
				player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.WHITE, recipient.getName() + " has been teleported to your location!"));
				recipient.sendMessage(Texts.of(TextColors.GREEN, player.getName(), TextColors.WHITE, " has teleported you to their location!"));
				recipient.setLocation(player.getLocation());
			}
			
		}
		else if(src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use /tphere!"));
		}
		else if(src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use /tphere!"));
		}
		return CommandResult.success();
	}
}