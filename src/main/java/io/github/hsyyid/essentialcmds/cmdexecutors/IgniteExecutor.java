package io.github.hsyyid.essentialcmds.cmdexecutors;

import org.spongepowered.api.data.key.Keys;
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

public class IgniteExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Integer ticks = ctx.<Integer> getOne("ticks").get();
		Optional<Player> target = ctx.<Player> getOne("player");

		if (!target.isPresent())
		{
			if (src instanceof Player)
			{
				Player player = (Player) src;
				player.offer(Keys.FIRE_TICKS, ticks);
				player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "You are now on fire."));
			}
			else if (src instanceof ConsoleSource)
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /ignite!"));
			}
			else if (src instanceof CommandBlockSource)
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /ignite!"));
			}
		}
		else if (target.isPresent() && src.hasPermission("ignite.others"))
		{
			Player player = target.get();
			player.offer(Keys.FIRE_TICKS, ticks);
			src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Set player on fire."));
			player.sendMessage(Texts.of(TextColors.GOLD, src.getName(), TextColors.RED, " has set you on fire."));
		}

		return CommandResult.success();
	}
}
