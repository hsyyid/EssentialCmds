package io.github.hsyyid.essentialcmds.cmdexecutors;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class TakeExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Player target = ctx.<Player> getOne("target").get();

		if (src instanceof Player)
		{
			Player player = (Player) src;

			if (target.getItemInHand().isPresent())
			{
				player.getInventory().offer(target.getItemInHand().get());
				player.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Took " + target.getName() + "'s held item."));
			}
			else
			{
				player.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Target is not holding anything!"));
			}
		}
		else
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be an in-game player to take other players items!"));
		}

		return CommandResult.success();
	}
}
