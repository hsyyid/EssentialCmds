package io.github.hsyyid.spongeessentialcmds.commandexecutors;

import io.github.hsyyid.spongeessentialcmds.Main;
import io.github.hsyyid.spongeessentialcmds.events.TPAAcceptEvent;
import io.github.hsyyid.spongeessentialcmds.events.TPAHereAcceptEvent;
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

public class TPAAcceptExecutor  implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Game game = Main.game;
		
		if(src instanceof Player)
		{
			Player player = (Player) src;
			Player sender = null;
			boolean tpaHere = false;
			
			for(PendingInvitation invitation : Main.pendingInvites)
			{
				if(!invitation.isTPAHere && invitation.recipient == player)
				{
					sender = invitation.sender;
					break;
				}
				else if(invitation.isTPAHere && invitation.recipient == player)
				{
					tpaHere = true;
					sender = invitation.sender;
					break;
				}
			}
			
			if(sender != null && !tpaHere)
			{
				game.getEventManager().post(new TPAAcceptEvent(player, sender));
				src.sendMessage(Texts.of(TextColors.GREEN,"Success! ", TextColors.WHITE, "TPA Request Accepted."));
			}
			else if(sender != null && tpaHere)
			{	
				game.getEventManager().post(new TPAHereAcceptEvent(player, sender));
				src.sendMessage(Texts.of(TextColors.GREEN,"Success! ", TextColors.WHITE, "TPA Here Request Accepted."));
			}
			else
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Pending TPA request not found!"));
			}
		}
		else if(src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use /tpaccept!"));
		}
		else if(src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use /tpaccept!"));
		}
		return CommandResult.success();
	}
}