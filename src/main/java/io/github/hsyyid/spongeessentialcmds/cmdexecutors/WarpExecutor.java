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

public class WarpExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		String warpName = ctx.<String>getOne("warp name").get();
		if(src instanceof Player)
		{
			Player player = (Player) src;
			if(Utils.isWarpInConfig(warpName))
			{
				if(player.getWorld().getName() != Utils.getWarpWorldName(warpName))
				{
					Vector3d position = new Vector3d(Utils.getWarpX(warpName), Utils.getWarpY(warpName), Utils.getWarpZ(warpName));
					player.transferToWorld(Utils.getWarpWorldName(warpName), position);
					src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported to Warp " + warpName));
					return CommandResult.success();
				} else
				{
					Location<World> warp = new Location<World>(player.getWorld(), Utils.getWarpX(warpName), Utils.getWarpY(warpName), Utils.getWarpZ(warpName));
					player.setLocation(warp);
				}
			} else
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Warp " + warpName + " does not exist!"));
			}
		} else if(src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /warp!"));
		} else if(src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /warp!"));
		}
		return CommandResult.success();
	}
}

