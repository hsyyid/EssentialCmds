package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import io.github.hsyyid.spongeessentialcmds.SpongeEssentialCmds;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.RepresentedPlayerData;
import org.spongepowered.api.data.type.SkullTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackBuilder;
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

public class SkullExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Player> optionalTarget = ctx.<Player> getOne("player");

		if (!optionalTarget.isPresent())
		{
			if (src instanceof Player)
			{
				Player player = (Player) src;

				// Create the Skull
				ItemStackBuilder builder = SpongeEssentialCmds.game.getRegistry().createItemBuilder();
				ItemStack skullStack = builder.itemType(ItemTypes.SKULL).quantity(1).build();

				// Set it to player skull type
				skullStack.offer(Keys.SKULL_TYPE, SkullTypes.PLAYER);

				// Set the owner to the command executor
				RepresentedPlayerData playerData = skullStack.getOrCreate(RepresentedPlayerData.class).get();
				playerData = playerData.set(playerData.owner().set(player.getProfile()));
				skullStack.offer(playerData);

				// Put it in inventory
				player.setItemInHand(skullStack);

				player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Created skull of your head. Enjoy!"));
			}
			else if (src instanceof ConsoleSource)
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /skull!"));
			}
			else if (src instanceof CommandBlockSource)
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /skull!"));
			}
		}
		else
		{
			if (src instanceof Player)
			{
				Player source = (Player) src;
				Player player = optionalTarget.get();

				// Create the Skull
				ItemStackBuilder builder = SpongeEssentialCmds.game.getRegistry().createItemBuilder();
				ItemStack skullStack = builder.itemType(ItemTypes.SKULL).quantity(1).build();

				// Set it to player skull type
				skullStack.offer(Keys.SKULL_TYPE, SkullTypes.PLAYER);

				// Set the owner to the specified player
				RepresentedPlayerData playerData = skullStack.getOrCreate(RepresentedPlayerData.class).get();
				playerData = playerData.set(playerData.owner().set(player.getProfile()));
				skullStack.offer(playerData);

				// Put it in inventory
				source.setItemInHand(skullStack);

				source.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Created skull of player's head. Enjoy!"));
			}
			else if (src instanceof ConsoleSource)
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /skull!"));
			}
			else if (src instanceof CommandBlockSource)
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /skull!"));
			}
		}

		return CommandResult.success();
	}
}
