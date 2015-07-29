package io.github.hsyyid.spongeessentialcmds.commandexecutors;

import io.github.hsyyid.spongeessentialcmds.Main;
import io.github.hsyyid.spongeessentialcmds.utils.PendingInvitation;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.source.CommandBlockSource;
import org.spongepowered.api.util.command.source.ConsoleSource;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class TPADenyExecutor  implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{	
		if(src instanceof Player)
		{
			Player player = (Player) src;
			Player sender = null;
			
			PendingInvitation cancel = null;
			
			for(PendingInvitation invitation : Main.pendingInvites)
			{
				if(invitation.recipient == player)
				{
					sender = invitation.sender;
					cancel = invitation;
					break;
				}
			}
			
			if(cancel != null && sender != null)
			{
				sender.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Your TPA Request was Denied by " + player.getName() + "!"));
				Main.pendingInvites.remove(cancel);
				src.sendMessage(Texts.of(TextColors.GREEN,"Success! ", TextColors.WHITE, "TPA Request Denied."));
			}
			else
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Pending TPA request not found!"));
			}
		}
		else if(src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use /tpadeny!"));
		}
		else if(src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use /tpadeny!"));
		}
		return CommandResult.success();
	}
}