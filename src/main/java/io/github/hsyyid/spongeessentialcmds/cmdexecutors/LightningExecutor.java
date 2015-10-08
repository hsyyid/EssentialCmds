package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import org.spongepowered.api.event.cause.Cause;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
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
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;
import java.util.Optional;

public class LightningExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		if (src instanceof Player)
		{
			Player player = (Player) src;
			Location<World> playerLocation = player.getLocation();
			Location<World> lightningLocation = new Location<World>(playerLocation.getExtent(), playerLocation.getX(), playerLocation.getY() + 50, playerLocation.getZ());
			spawnEntity(lightningLocation);
			player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Created Lightning Strike!"));
		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /lightning!"));
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /lightning!"));
		}
		return CommandResult.success();
	}

	public void spawnEntity(Location<World> location)
	{


		Extent extent = location.getExtent();
		Optional<Entity> optional = extent.createEntity(EntityTypes.LIGHTNING, location.getPosition());

		Entity lightning = optional.get();
		System.out.println("Spawned Lightning " + extent.spawnEntity(lightning, Cause.empty()));
	}
}
