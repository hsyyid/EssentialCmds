package io.github.hsyyid.spongeessentialcmds.commandexecutors;

import org.spongepowered.api.data.manipulator.entity.FoodData;
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

import com.google.common.base.Optional;

public class FeedExecutor implements CommandExecutor
{

	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		if(src instanceof Player)
		{
			Player player = (Player) src;
			Optional<FoodData> data = player.getData(FoodData.class);
			FoodData food = data.get();
			food.setFoodLevel(20);
			player.offer(food);
			
			src.sendMessage(Texts.of(TextColors.GREEN,"Success: ", TextColors.YELLOW, "You've been fed."));
		}
		else if(src instanceof ConsoleSource) {
			src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use /feed!"));
		}
		else if(src instanceof CommandBlockSource) {
			src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use /feed!"));
		}

		return CommandResult.success();
	}
}
