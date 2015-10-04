package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.entity.FlyingData;
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

public class FlyExecutor  implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		if(src instanceof Player)
		{
			Player player = (Player) src;
			Optional<FlyingData> optionalFlyingData = player.getOrCreate(FlyingData.class);
			if(optionalFlyingData.isPresent())
			{
				FlyingData data = optionalFlyingData.get().fill(player).get();
				
				if(data.get(Keys.IS_FLYING).isPresent() && !(data.get(Keys.IS_FLYING).get()))
				{
				    FlyingData updatedData = data.set(Keys.IS_FLYING, true);
				    player.offer(updatedData);
				    player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, " You can now fly!"));
				}
				else
				{
				    player.remove(FlyingData.class);
				    player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, " You can no longer fly!"));
				}
			}
		}
		else if(src instanceof ConsoleSource) {
			src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use /fly!"));
		}
		else if(src instanceof CommandBlockSource) {
			src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use /fly!"));
		}
		return CommandResult.success();
	}
}
