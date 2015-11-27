package io.github.hsyyid.essentialcmds.cmdexecutors;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.source.CommandBlockSource;
import org.spongepowered.api.util.command.source.ConsoleSource;
import org.spongepowered.api.util.command.spec.CommandExecutor;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class ThruExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		if (src instanceof Player)
		{
			Player player = (Player) src;

			BlockRay<World> playerBlockRay = BlockRay.from(player).blockLimit(25).build();
			BlockRayHit<World> finalHitRay = null;
			Location<World> location = null;
			
			while (playerBlockRay.hasNext())
			{
				BlockRayHit<World> currentHitRay = playerBlockRay.next();

				if(finalHitRay != null && player.getWorld().getBlockType(currentHitRay.getBlockPosition()).equals(BlockTypes.AIR))
				{
					location = currentHitRay.getLocation();
					break;
				}
				else if (player.getWorld().getBlockType(currentHitRay.getBlockPosition()).equals(BlockTypes.AIR))
				{
					continue;
				}
				else
				{
					finalHitRay = currentHitRay;
				}
			}

			if (finalHitRay != null && location != null)
			{
				if (player.setLocationSafely(location))
				{
					player.sendMessage(Texts.of(TextColors.LIGHT_PURPLE, "Whoosh!"));
				}
				else
				{
					player.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "No free spot ahead of you found."));
				}
			}
			else if (finalHitRay == null)
			{
				player.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You are not facing a wall."));
			}
			else if (location == null)
			{
				player.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "No free spot ahead of you found."));
			}
		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /thru!"));
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /thru!"));
		}

		return CommandResult.success();
	}
}
