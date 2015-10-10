package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import com.flowpowered.math.vector.Vector3d;
import io.github.hsyyid.spongeessentialcmds.utils.Utils;
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

import java.util.Objects;

public class SpawnExecutor implements CommandExecutor
{

	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		if (src instanceof Player)
		{
			Player player = (Player) src;
			if (Utils.isSpawnInConfig())
			{
				if (!Objects.equals(player.getWorld().getName(), Utils.getSpawnWorldName()))
				{
					Vector3d position = new Vector3d(Utils.getSpawn(player).getX(), Utils.getSpawn(player).getY(), Utils.getSpawn(player).getZ());
					player.transferToWorld(Utils.getSpawnWorldName(), position);
					src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported to Spawn"));
					return CommandResult.success();
				}
				else
				{
					Location<World> spawn = new Location<>(player.getWorld(), Utils.getSpawn(player).getX(), Utils.getSpawn(player).getY(), Utils.getSpawn(player).getZ());
					player.setLocation(spawn);
				}
				src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported to Spawn"));
			}
			else
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Spawn has not been set yet!"));
			}
		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /spawn!"));
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /spawn!"));
		}
		return CommandResult.success();
	}
}
