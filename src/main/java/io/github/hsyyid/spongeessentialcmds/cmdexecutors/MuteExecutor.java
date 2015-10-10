package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import io.github.hsyyid.spongeessentialcmds.Main;
import io.github.hsyyid.spongeessentialcmds.utils.Mute;
import io.github.hsyyid.spongeessentialcmds.utils.Utils;
import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.scheduler.TaskBuilder;
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
		Game game = Main.game;
		Player p = ctx.<Player>getOne("player").get();
		Optional<Long> time = ctx.<Long>getOne("time");
		Optional<String> timeUnit = ctx.<String>getOne("time unit");

		for (Mute mute : Main.muteList)
		{
			if(mute.getUUID().equals(p.getUniqueId().toString()))
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "This player has already been muted."));
				return CommandResult.success();
			}
		}

		if(time.isPresent() && timeUnit.isPresent())
		{
			TimeUnit unit;

			if(timeUnit.get().toLowerCase().equals("m"))
			{
				unit = TimeUnit.MINUTES;
			} else if(timeUnit.get().toLowerCase().equals("h"))
			{
				unit = TimeUnit.HOURS;
			} else if(timeUnit.get().toLowerCase().equals("s"))
			{
				unit = TimeUnit.SECONDS;
			} else if(timeUnit.get().toLowerCase().equals("d"))
			{
				unit = TimeUnit.DAYS;
			} else {
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! Invalid time unit."));
				return CommandResult.empty();
			}

			final Mute mute = new Mute(p.getUniqueId().toString());

			TaskBuilder taskBuilder = game.getScheduler().createTaskBuilder();
			taskBuilder.execute(() -> Main.muteList.remove(mute)).delay(time.get(), unit).name("SpongeEssentialCmds removes mute").submit(game.getPluginManager().getPlugin("SpongeEssentialCmds").get().getInstance());

			Main.muteList.add(mute);
		} else
		{
			Main.muteList.add(new Mute(p.getUniqueId().toString()));
		}

		Utils.saveMutes();

		src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Player muted."));

		return CommandResult.success();
	}
}