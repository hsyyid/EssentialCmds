package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import io.github.hsyyid.spongeessentialcmds.EssentialCmds;
import org.spongepowered.api.Game;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.monster.Monster;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;
import org.spongepowered.api.world.World;

public class ButcherExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Game game = EssentialCmds.game;

		if (src instanceof Player)
		{
			Player player = (Player) src;
			int butcheredEntities = 0;
			
			for (Entity entity : player.getWorld().getEntities())
			{
				if (entity instanceof Monster)
				{
					entity.remove();
					butcheredEntities++;
				}
			}
			
			player.sendMessage(Texts.of(TextColors.LIGHT_PURPLE, "Butchered " + butcheredEntities + " entities."));
		}
		else
		{
			int butcheredEntities = 0;
			
			for (World world : game.getServer().getWorlds())
			{
				for (Entity entity : world.getEntities())
				{
					if (entity instanceof Monster)
					{
						entity.remove();
						butcheredEntities++;
					}
				}
			}
			
			src.sendMessage(Texts.of(TextColors.LIGHT_PURPLE, "Butchered " + butcheredEntities + " entities."));
		}

		return CommandResult.success();
	}
}
