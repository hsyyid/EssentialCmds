package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

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
		Optional<Player> target = ctx.<Player> getOne("player");

		if (!target.isPresent())
		{
			if (src instanceof Player)
			{
				Player player = (Player) src;

				if (player.get(Keys.FIRE_TICKS).isPresent())
				{
					player.offer(Keys.FIRE_TICKS, 100);
					player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "You are now on fire."));
				}
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

			if (player.get(Keys.FIRE_TICKS).isPresent())
			{
				player.offer(Keys.FIRE_TICKS, 100);
				src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Set player on fire."));
				player.sendMessage(Texts.of(TextColors.GOLD, src.getName(), TextColors.RED, " has set you on fire."));
			}
		}

		return CommandResult.success();
	}
}
