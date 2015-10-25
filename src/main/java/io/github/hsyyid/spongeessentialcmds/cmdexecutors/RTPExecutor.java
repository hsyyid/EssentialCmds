package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import io.github.hsyyid.spongeessentialcmds.EssentialCmds;
import org.spongepowered.api.block.BlockTypes;
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
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.TeleportHelper;
import org.spongepowered.api.world.World;

import java.util.Optional;
import java.util.Random;

public class RTPExecutor implements CommandExecutor
{
	@Override
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		if (src instanceof Player)
		{
			Player player = (Player) src;
			Location<World> playerLocation = player.getLocation();

			Random rand = new Random();
			int x = rand.nextInt(3999);
			int y = rand.nextInt(256);
			int z = rand.nextInt(3999);

			Location<World> randLocation = new Location<World>(playerLocation.getExtent(), x, y, z);
			TeleportHelper teleportHelper = EssentialCmds.helper;
			Optional<Location<World>> optionalLocation = teleportHelper.getSafeLocation(randLocation);

			if (optionalLocation.isPresent())
			{
				if (optionalLocation.get().getBlock().getType().equals(BlockTypes.WATER) || optionalLocation.get().getBlock().getType().equals(BlockTypes.FLOWING_WATER) || optionalLocation.get().getBlock().getType().equals(BlockTypes.LAVA) || optionalLocation.get().getBlock().getType().equals(BlockTypes.FLOWING_LAVA) || optionalLocation.get().getBlock().getType().equals(BlockTypes.FIRE))
				{
					boolean found = false;
					while (!found)
					{
						x = rand.nextInt(30000);
						y = rand.nextInt(256);
						z = rand.nextInt(30000);

						randLocation = new Location<World>(playerLocation.getExtent(), x, y, z);
						optionalLocation = teleportHelper.getSafeLocation(randLocation);

						if (optionalLocation.isPresent() && optionalLocation.get().getBlock().getType() != BlockTypes.WATER && optionalLocation.get().getBlock().getType() != BlockTypes.LAVA && optionalLocation.get().getBlock().getType() != BlockTypes.FLOWING_LAVA && optionalLocation.get().getBlock().getType() != BlockTypes.FLOWING_WATER && optionalLocation.get().getBlock().getType() != BlockTypes.FIRE)
						{
							found = true;
						}
					}
				}
				player.setLocation(optionalLocation.get());
				player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW,
					"Teleporting you to nearest safe location!"));
			}
			else
			{
				boolean found = false;
				while (!found)
				{
					x = rand.nextInt(30000);
					y = rand.nextInt(256);
					z = rand.nextInt(30000);

					randLocation = new Location<World>(playerLocation.getExtent(), x, y, z);
					optionalLocation = teleportHelper.getSafeLocation(randLocation);
					if (optionalLocation.isPresent() && optionalLocation.get().getBlock().getType() != BlockTypes.WATER)
					{
						found = true;
					}
				}
				player.setLocation(optionalLocation.get());
				player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW,
					"Teleporting you to nearest safe location!"));
			}
		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /rtp!"));
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /rtp!"));
		}

		return CommandResult.success();
	}
}
