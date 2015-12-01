package io.github.hsyyid.essentialcmds.cmdexecutors;

import io.github.hsyyid.essentialcmds.utils.AFK;
import io.github.hsyyid.essentialcmds.utils.Utils;

import io.github.hsyyid.essentialcmds.EssentialCmds;
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

public class AFKExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		if (src instanceof Player)
		{
			Player player = (Player) src;

			AFK playerAFK = null;

			for (AFK afk : EssentialCmds.movementList)
			{
				if (afk.getPlayer().getUniqueId().equals(player.getUniqueId()))
				{
					playerAFK = afk;
					break;
				}
			}

			if (playerAFK != null)
			{
				EssentialCmds.movementList.remove(playerAFK);
			}

			int afkTime = (int) Utils.getAFK();
			long afkTimer = afkTime + 1000;
			AFK afk = new AFK(player, afkTimer);
			EssentialCmds.movementList.add(afk);
		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /afk!"));
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /afk!"));
		}
		return CommandResult.success();
	}
}
