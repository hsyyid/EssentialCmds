package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class SpeedExecutor implements CommandExecutor
{

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
	{
		if(!(src instanceof Player))
			return CommandResult.empty();
		Player player = (Player) src;
		double multiplier = (Integer) args.getOne("speed").get();
		multiplier = Math.min(multiplier, 20);
		double flySpeed = 0.05d * multiplier;
		double walkSpeed = 0.1d * multiplier;
		player.offer(Keys.WALKING_SPEED, walkSpeed);
		player.offer(Keys.FLYING_SPEED, flySpeed);
		return CommandResult.success();
	}

}
