package io.github.hsyyid.essentialcmds.cmdexecutors;

import io.github.hsyyid.essentialcmds.EssentialCmds;
import io.github.hsyyid.essentialcmds.utils.Utils;
import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.scheduler.Task;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class MuteExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Game game = EssentialCmds.game;
		Player p = ctx.<Player> getOne("player").get();
		Optional<Long> time = ctx.<Long> getOne("time");
		Optional<String> timeUnit = ctx.<String> getOne("time unit");

		if (time.isPresent() && timeUnit.isPresent())
		{
			TimeUnit unit;

			if (timeUnit.get().toLowerCase().equals("m"))
			{
				unit = TimeUnit.MINUTES;
			}
			else if (timeUnit.get().toLowerCase().equals("h"))
			{
				unit = TimeUnit.HOURS;
			}
			else if (timeUnit.get().toLowerCase().equals("s"))
			{
				unit = TimeUnit.SECONDS;
			}
			else if (timeUnit.get().toLowerCase().equals("d"))
			{
				unit = TimeUnit.DAYS;
			}
			else
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! Invalid time unit."));
				return CommandResult.empty();
			}

			Task.Builder taskBuilder = game.getScheduler().createTaskBuilder();
			taskBuilder.execute(() -> EssentialCmds.muteList.remove(p.getUniqueId())).delay(time.get(), unit).name("EssentialCmds removes mute").submit(game
					.getPluginManager().getPlugin("EssentialCmds").get().getInstance());

			EssentialCmds.muteList.add(p.getUniqueId());
		}
		else
		{
			EssentialCmds.muteList.add(p.getUniqueId());
		}

		Utils.saveMutes();

		src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Player muted."));

		return CommandResult.success();
	}
}
