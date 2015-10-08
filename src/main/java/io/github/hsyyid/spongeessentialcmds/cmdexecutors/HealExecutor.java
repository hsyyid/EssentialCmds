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

public class HealExecutor implements CommandExecutor
{

	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Player> p = ctx.<Player>getOne("player");
		
		if(src instanceof Player)
		{
			Player player = (Player) src;
			
			if(player.hasPermission("heal.others") && p.isPresent())
			{
				Player recipient = p.get();
				recipient.offer(Keys.HEALTH, player.get(Keys.MAX_HEALTH).get());
				recipient.sendMessage(Texts.of(TextColors.GREEN, "Success: ", TextColors.YELLOW, "You've been healed by " + player.getName()));
				src.sendMessage(Texts.of(TextColors.GREEN,"Success: ", TextColors.YELLOW, "You've healed " + recipient.getName()));
			}
			else if(p.isPresent())
			{
				player.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You do not have permission to heal other players!"));
			}
			else
			{
				player.offer(Keys.HEALTH, player.get(Keys.MAX_HEALTH).get());
				src.sendMessage(Texts.of(TextColors.GREEN,"Success: ", TextColors.YELLOW, "You've been healed."));
			}
		}
		else if(src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use /heal!"));
		}
		else if(src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use /heal!"));
		}

		return CommandResult.success();
	}
}