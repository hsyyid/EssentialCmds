package io.github.hsyyid.essentialcmds.cmdexecutors;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import java.util.Optional;

public class SpeedExecutor implements CommandExecutor
{
	@Override
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Player> optionalTarget = ctx.<Player> getOne("player");
		int multiplier = ctx.<Integer> getOne("speed").get();

		if (!optionalTarget.isPresent())
		{
			if (src instanceof Player)
			{
				Player player = (Player) src;
				multiplier = Math.min(multiplier, 20);

				if (player.get(Keys.IS_FLYING).isPresent() && player.get(Keys.IS_FLYING).get())
				{
					double flySpeed = 0.05d * multiplier;
					player.offer(Keys.FLYING_SPEED, flySpeed);
					src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Your flying speed has been updated."));
				}
				else
				{
					double walkSpeed = 0.1d * multiplier;
					player.offer(Keys.WALKING_SPEED, walkSpeed);
					src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Your walking speed has been updated."));
				}
			}
			else
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error!", TextColors.RED, "You must be a player to do /speed"));
			}
		}
		else if(src.hasPermission("speed.others"))
		{
			Player player = optionalTarget.get();
			multiplier = Math.min(multiplier, 20);

			if (player.get(Keys.IS_FLYING).isPresent() && player.get(Keys.IS_FLYING).get())
			{
				double flySpeed = 0.05d * multiplier;
				player.offer(Keys.FLYING_SPEED, flySpeed);
				player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Your flying speed has been updated."));
			}
			else
			{
				double walkSpeed = 0.1d * multiplier;
				player.offer(Keys.WALKING_SPEED, walkSpeed);
				player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Your walking speed has been updated."));
			}
			
			src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Updated player's speed."));
		}

		return CommandResult.success();
	}
}
