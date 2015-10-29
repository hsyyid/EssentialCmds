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

public class VanishExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Player> target = ctx.<Player> getOne("player");

		if (!target.isPresent())
		{
			if (src instanceof Player)
			{
				Player player = (Player) src;

				if (player.get(Keys.INVISIBLE).isPresent() && !player.get(Keys.INVISIBLE).get())
				{
					player.offer(Keys.INVISIBLE, true);
					player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "You are now invisible."));
				}
				else if (player.get(Keys.INVISIBLE).isPresent() && player.get(Keys.INVISIBLE).get())
				{
					player.offer(Keys.INVISIBLE, false);
					player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "You are now visible."));
				}
			}
			else if (src instanceof ConsoleSource)
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /vanish!"));
			}
			else if (src instanceof CommandBlockSource)
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /vanish!"));
			}
		}
		else if (target.isPresent() && src.hasPermission("vanish.others"))
		{
			Player player = target.get();

			if (player.get(Keys.INVISIBLE).isPresent() && !player.get(Keys.INVISIBLE).get())
			{
				player.offer(Keys.INVISIBLE, true);
				src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Set player invisible."));
				player.sendMessage(Texts.of(TextColors.GOLD, src.getName(), " has set you ", TextColors.GRAY, "invisible."));
			}
			else if (player.get(Keys.INVISIBLE).isPresent() && player.get(Keys.INVISIBLE).get())
			{
				player.offer(Keys.INVISIBLE, false);
				src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Set player visible."));
				player.sendMessage(Texts.of(TextColors.GOLD, src.getName(), " has set you ", TextColors.GRAY, "visible."));
			}
		}

		return CommandResult.success();
	}
}
