package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import java.util.Optional;
import org.spongepowered.api.data.manipulator.mutable.entity.FoodData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

/**
 * Created by aaa801 on 30/09/2015.
 */
public class SlapExecutor implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
		Optional<Player> target = ctx.getOne("player");
		if(!target.isPresent()) {
			src.sendMessage(Texts.of("Unable to find target"));
			return CommandResult.empty();
		}

		//TODO NO_OP



		return CommandResult.success();
	}

}
