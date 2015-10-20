package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import io.github.hsyyid.spongeessentialcmds.SpongeEssentialCmds;
import io.github.hsyyid.spongeessentialcmds.utils.Mute;
import io.github.hsyyid.spongeessentialcmds.utils.Utils;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class UnmuteExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Player p = ctx.<Player> getOne("player").get();

		Mute targetMute = null;

		for (Mute mute : SpongeEssentialCmds.muteList)
		{
			if (mute.getUUID().equals(p.getUniqueId().toString()))
			{
				targetMute = mute;
			}
		}

		if (targetMute != null)
		{
			SpongeEssentialCmds.muteList.remove(targetMute);
			src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Player un-muted."));
		}
		else
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "This player is not muted."));
		}

		Utils.saveMutes();

		return CommandResult.success();
	}
}
