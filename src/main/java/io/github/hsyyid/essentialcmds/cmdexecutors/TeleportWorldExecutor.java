package io.github.hsyyid.essentialcmds.cmdexecutors;

import io.github.hsyyid.essentialcmds.EssentialCmds;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;

public class TeleportWorldExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Player> optPlayer = ctx.<Player> getOne("player");
		String name = ctx.<String> getOne("name").get();

		if (!optPlayer.isPresent())
		{
			if (src instanceof Player)
			{
				Player player = (Player) src;

				Optional<World> optWorld = EssentialCmds.game.getServer().getWorld(name);

				if (optWorld.isPresent())
				{
					Location<World> spawnLocation = optWorld.get().getSpawnLocation();
					player.transferToWorld(name, spawnLocation.getPosition());
					src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported to world."));
				}
				else
				{
					src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "World specified does not exist!"));
				}
			}
			else
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You cannot teleport, you are not a player!"));
			}
		}
		else
		{
			Player player = optPlayer.get();

			Optional<World> optWorld = EssentialCmds.game.getServer().getWorld(name);

			if (optWorld.isPresent())
			{
				Location<World> spawnLocation = optWorld.get().getSpawnLocation();
				player.transferToWorld(name, spawnLocation.getPosition());
				src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported player to world."));
				player.sendMessage(Texts.of(TextColors.GOLD, src.getName(), TextColors.GRAY, " has teleported you to this world."));
			}
			else
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "World specified does not exist!"));
			}
		}

		return CommandResult.success();
	}
}
