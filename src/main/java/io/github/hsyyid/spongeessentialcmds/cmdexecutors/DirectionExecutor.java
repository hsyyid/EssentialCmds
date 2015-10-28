package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class DirectionExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		if (src instanceof Player)
		{
			Player player = (Player) src;
			
			String direction = "";
			
			direction = String.valueOf(player.getRotation().getMaxAxis()) + "," + String.valueOf(player.getRotation().getMinAxis());
			
//			if(player.getRotation().g)
//			{
//				direction = "Forward";
//			}
//			else if(player.getRotation().equals(Vector3d.RIGHT))
//			{
//				direction = "Right";
//			}
//			else if(player.getRotation().equals(Vector3d.UP))
//			{
//				direction = "Up";
//			}
//			else
//			{
//				direction = "NaN";
//			}
			
			player.sendMessage(Texts.of(TextColors.GOLD, "You are facing: ", TextColors.GRAY, direction));
		}
		else
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be an in-game player to send mail!"));
		}
		return CommandResult.success();
	}
}
