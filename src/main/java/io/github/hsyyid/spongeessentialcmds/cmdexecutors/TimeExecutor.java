package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import java.util.Optional;

public class TimeExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<String> timeString = ctx.<String>getOne("time");
		Optional<Integer> timeTicks = ctx.<Integer>getOne("ticks");

		if(src instanceof Player)
		{
			Player player = (Player) src;

			if(timeTicks.isPresent())
			{
				player.getWorld().getProperties().setWorldTime(timeTicks.get());
				player.sendMessage(Texts.of(TextColors.GOLD, "Set time to ", TextColors.GRAY, timeTicks.get()));
			} else if(timeString.isPresent())
			{
				if(timeString.get().toLowerCase().equals("dawn") || timeString.get().toLowerCase().equals("morning"))
				{
					player.sendMessage(Texts.of(TextColors.GOLD, "Set time to ", TextColors.GRAY, timeString.get().toLowerCase()));
					player.getWorld().getProperties().setWorldTime(0);
				} else if(timeString.get().toLowerCase().equals("day"))
				{
					player.sendMessage(Texts.of(TextColors.GOLD, "Set time to ", TextColors.GRAY, timeString.get().toLowerCase()));
					player.getWorld().getProperties().setWorldTime(1000);
				} else if(timeString.get().toLowerCase().equals("afternoon") || timeString.get().toLowerCase().equals("noon"))
				{
					player.sendMessage(Texts.of(TextColors.GOLD, "Set time to ", TextColors.GRAY, timeString.get().toLowerCase()));
					player.getWorld().getProperties().setWorldTime(6000);
				} else if(timeString.get().toLowerCase().equals("dusk"))
				{
					player.sendMessage(Texts.of(TextColors.GOLD, "Set time to ", TextColors.GRAY, timeString.get().toLowerCase()));
					player.getWorld().getProperties().setWorldTime(12000);
				} else if(timeString.get().toLowerCase().equals("night"))
				{
					player.sendMessage(Texts.of(TextColors.GOLD, "Set time to ", TextColors.GRAY, timeString.get().toLowerCase()));
					player.getWorld().getProperties().setWorldTime(14000);
				} else if(timeString.get().toLowerCase().equals("midnight"))
				{
					player.sendMessage(Texts.of(TextColors.GOLD, "Set time to ", TextColors.GRAY, timeString.get().toLowerCase()));
					player.getWorld().getProperties().setWorldTime(18000);
				} else
				{
					player.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Could not understand input."));
				}
			}

			return CommandResult.success();
		} else
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be a in-game player to do /time!"));
			return CommandResult.success();
		}
	}
}