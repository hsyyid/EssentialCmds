package io.github.hsyyid.spongeessentialcmds;

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
import org.spongepowered.api.world.Location;

public class HomeExecutor implements CommandExecutor
{

	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		String homeName = ctx.<String>getOne("home name").get();
		if(src instanceof Player)
		{
			Player player = (Player) src;
			if(Utils.inConfig(player.getUniqueId(), homeName))
			{
				Location home = new Location(player.getWorld(), Utils.getX(player.getUniqueId(), homeName), Utils.getY(player.getUniqueId(), homeName), Utils.getZ(player.getUniqueId(), homeName));
				player.setLocation(home);
				src.sendMessage(Texts.of(TextColors.GREEN,"Success! ", TextColors.YELLOW, "Teleported to Home " + homeName));
			}
			else
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "You must first set your home!"));
			}
		}
		else if(src instanceof ConsoleSource) {
			src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use /home!"));
		}
		else if(src instanceof CommandBlockSource) {
			src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use /home!"));
		}
		return CommandResult.success();
	}
}
