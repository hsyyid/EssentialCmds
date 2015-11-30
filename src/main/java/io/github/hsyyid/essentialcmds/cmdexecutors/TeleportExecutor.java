package io.github.hsyyid.essentialcmds.cmdexecutors;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import java.util.Optional;

public class TeleportExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Player player = ctx.<Player> getOne("player").get();
		Optional<Player> target = ctx.<Player> getOne("target");

		if (target.isPresent())
		{
			if (src.hasPermission("teleport.others"))
			{
				target.get().setLocation(player.getLocation());
				src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported player " + target.get().getName() + " to " + player.getName()));
				target.get().sendMessage(Texts.of(TextColors.GOLD, "You have been teleported to " + player.getName() + " by " + src.getName()));
			}
			else
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You do not have permission to teleport other players."));
			}
		}
		else
		{
			if (src instanceof Player)
			{
				Player targ = (Player) src;
				targ.setLocation(player.getLocation());
				src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported to player " + player.getName()));
			}
			else
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You cannot teleport, you are not a player!"));
			}
		}

		return CommandResult.success();
	}
}
