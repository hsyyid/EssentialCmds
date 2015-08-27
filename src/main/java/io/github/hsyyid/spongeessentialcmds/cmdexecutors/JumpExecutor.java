package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import io.github.hsyyid.spongeessentialcmds.Main;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRay.BlockRayBuilder;
import org.spongepowered.api.util.blockray.BlockRayHit;
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

public class JumpExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		if(src instanceof Player)
		{
			Player player = (Player) src;
			BlockRayBuilder<World> blockRayBuilder = BlockRay.from(player);
			BlockRay<World> ray = blockRayBuilder.blockLimit(1).build();
			if(ray.hasNext())
			{
				BlockRayHit<World> hit = ray.next();
				Location<World> location = new Location<World>(player.getWorld(), hit.getBlockX(), hit.getBlockY(), hit.getBlockZ());
				TeleportHelper helper = Main.helper;

				if(helper.getSafeLocation(location).get() != null)
				{
					Location<World> safe = helper.getSafeLocation(location).get();
					player.setLocation(safe);
				}
				else
				{
					src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Couldn't find safe place!"));
				}
			}
		}
		else if(src instanceof ConsoleSource) {
			src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use /jump!"));
		}
		else if(src instanceof CommandBlockSource) {
			src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use /jump!"));
		}
		return CommandResult.success();
	}
}
