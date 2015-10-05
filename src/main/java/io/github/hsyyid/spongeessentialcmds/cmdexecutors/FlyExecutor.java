package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import org.spongepowered.api.data.key.Keys;
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

public class FlyExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Player> targetPlayer = ctx.<Player> getOne("player");

		if (!targetPlayer.isPresent())
		{
			if (src instanceof Player)
			{
				Player player = (Player) src;
				
				if(player.get(Keys.CAN_FLY).isPresent())
				{
					boolean canFly = player.get(Keys.CAN_FLY).get();
					player.offer(Keys.CAN_FLY, !canFly);
					
					if(canFly)
					{
						player.sendMessage(Texts.of(TextColors.GOLD, "Toggled flying: ", TextColors.GRAY,  "off."));
					}
					else
					{
						player.sendMessage(Texts.of(TextColors.GOLD, "Toggled flying: ", TextColors.GRAY,  "on."));
					}
				}
			}
			else if (src instanceof ConsoleSource)
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /fly!"));
			}
			else if (src instanceof CommandBlockSource)
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /fly!"));
			}
		}
		else
		{
			Player player = targetPlayer.get();
			
			if(player.get(Keys.CAN_FLY).isPresent())
			{
				boolean canFly = player.get(Keys.CAN_FLY).get();
				player.offer(Keys.CAN_FLY, !canFly);
				
				if(canFly)
				{
					src.sendMessage(Texts.of(TextColors.GOLD, "Toggled flying: ", TextColors.GRAY,  "off."));
					player.sendMessage(Texts.of(TextColors.GOLD, "Toggled flying: ", TextColors.GRAY,  "off."));
				}
				else
				{
					src.sendMessage(Texts.of(TextColors.GOLD, "Toggled flying: ", TextColors.GRAY,  "on."));
					player.sendMessage(Texts.of(TextColors.GOLD, "Toggled flying: ", TextColors.GRAY,  "on."));
				}
			}
		}
		
		return CommandResult.success();
	}
}
