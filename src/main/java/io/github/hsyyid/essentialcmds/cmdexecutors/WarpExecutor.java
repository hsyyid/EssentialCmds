package io.github.hsyyid.essentialcmds.cmdexecutors;

import io.github.hsyyid.essentialcmds.utils.Utils;

import com.flowpowered.math.vector.Vector3d;
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

public class WarpExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		String warpName = ctx.<String> getOne("warp name").get();

		if (src instanceof Player)
		{
			Player player = (Player) src;

			if (Utils.isWarpInConfig(warpName))
			{
				if (player.hasPermission("essentialcmds.warp." + warpName))
				{
					if (!Objects.equals(player.getWorld().getUniqueId(), Utils.getWarpWorldUUID(warpName)))
					{
						Vector3d position = new Vector3d(Utils.getWarpX(warpName), Utils.getWarpY(warpName), Utils.getWarpZ(warpName));
						player.transferToWorld(Utils.getWarpWorldUUID(warpName), position);
						src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported to Warp " + warpName));
						return CommandResult.success();
					}
					else
					{
						Location<World> warp = new Location<>(player.getWorld(), Utils.getWarpX(warpName), Utils.getWarpY(warpName), Utils.getWarpZ(warpName));
						player.setLocation(warp);
					}
				}
				else
				{
					src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You do not have permission to use this warp!"));
					return CommandResult.success();
				}
			}
			else
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Warp " + warpName + " does not exist!"));
			}
		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /warp!"));
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /warp!"));
		}
		return CommandResult.success();
	}
}
