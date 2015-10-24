package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import io.github.hsyyid.spongeessentialcmds.EssentialCmds;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;
import org.spongepowered.api.world.World;

public class DeleteWorldExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		String worldName = ctx.<String> getOne("name").get();

		World foundWorld = null;
		World altWorld = null;
		
		for (World world : EssentialCmds.game.getServer().getWorlds())
		{
			if (world.getName().equals(worldName))
			{
				foundWorld = world;
				break;
			}
		}
		
		for (World world : EssentialCmds.game.getServer().getWorlds())
		{
			if (!world.getName().equals(worldName))
			{
				altWorld = world;
				break;
			}
		}
		
		if (foundWorld != null)
		{
			for(Player player : EssentialCmds.game.getServer().getOnlinePlayers())
			{
				if(player.getWorld().getUniqueId().equals(foundWorld.getUniqueId()) && altWorld != null)
				{
					player.transferToWorld(altWorld.getName(), altWorld.getSpawnLocation().getPosition());
					
				}
			}
			
			foundWorld.getProperties().setEnabled(false);
			src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Disabled world. To fully delete the world, please delete it from your files."));
		}
		else
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "The world you specified was not found."));
		}

		return CommandResult.success();
	}
}
