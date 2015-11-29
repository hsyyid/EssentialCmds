package io.github.hsyyid.essentialcmds.cmdexecutors;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class ItemInfoExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		if (src instanceof Player)
		{
			Player player = (Player) src;

			if (player.getItemInHand().isPresent())
			{
				ItemStack itemInHand = player.getItemInHand().get();
				player.sendMessage(Texts.of(TextColors.GOLD, "The ID of the item in your hand is: ", TextColors.GRAY, itemInHand.getItem().getName()));
				player.sendMessage(Texts.of(TextColors.GOLD, "The meta of the item in your hand is: ", TextColors.GRAY, itemInHand.toContainer().get(new DataQuery("UnsafeDamage")).get().toString()));
			}
			else
			{
				player.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must hold an item ."));
			}
		}
		else
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be an in-game player to use this command."));
		}

		return CommandResult.success();
	}
}
