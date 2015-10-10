package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.entity.FoodData;
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

public class FeedExecutor implements CommandExecutor
{

	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Player> p = ctx.<Player> getOne("player");

		if (src instanceof Player)
		{
			Player player = (Player) src;

			if (player.hasPermission("feed.others") && p.isPresent())
			{
				Player recipient = p.get();
				Optional<FoodData> foodData = recipient.getOrCreate(FoodData.class);

				if (foodData.isPresent())
				{
					FoodData newData = foodData.get().set(Keys.FOOD_LEVEL, 20);
					recipient.offer(newData);
					player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Fed " + recipient.getName()));
					recipient.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "You have been fed by " + player.getName()));
				}
				else
				{
					System.out.println("Error! Player " + recipient.getName() + " does not have FoodData!");
				}
			}
			else if (p.isPresent())
			{
				player.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You do not have permission to feed other players!"));
			}
			else
			{
				Optional<FoodData> foodData = player.getOrCreate(FoodData.class);

				if (foodData.isPresent())
				{
					FoodData newData = foodData.get().set(Keys.FOOD_LEVEL, 20);
					player.offer(newData);
					player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "You have been fed."));
				}
				else
				{
					System.out.println("Error! Player " + player.getName() + " does not have FoodData!");
				}
			}
		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /feed!"));
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /feed!"));
		}

		return CommandResult.success();
	}
}
