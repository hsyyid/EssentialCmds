package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class SpeedExecutor implements CommandExecutor
{
	@Override
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		int multiplier = ctx.<Integer> getOne("speed").get();

		if (src instanceof Player)
		{
			Player player = (Player) src;
			multiplier = Math.min(multiplier, 20);
			double flySpeed = 0.05d * multiplier;
			double walkSpeed = 0.1d * multiplier;
			player.offer(Keys.WALKING_SPEED, walkSpeed);
			player.offer(Keys.FLYING_SPEED, flySpeed);
			src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Your speed has been updated."));
		}
		else
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error!", TextColors.RED, "You must be a player to do /speed"));
		}

		return CommandResult.success();
	}

}
