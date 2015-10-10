package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import io.github.hsyyid.spongeessentialcmds.Main;
import io.github.hsyyid.spongeessentialcmds.utils.Message;
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

import java.util.ArrayList;
import java.util.stream.Collectors;

public class RespondExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		if(src instanceof Player)
		{
			Player recipient = null;
			Player player = (Player) src;

			for (Message m : Main.recentlyMessaged)
			{
				if(m.getRecipient().getUniqueId().toString().equals(player.getUniqueId().toString()))
				{
					recipient = m.getSender();
					break;
				}
			}

			if(recipient != null)
			{
				String message = ctx.<String>getOne("message").get();

				ArrayList<Player> socialSpies = (ArrayList<Player>) Main.game.getServer().getOnlinePlayers().stream().filter(p -> Main.socialSpies.contains(p.getUniqueId())).collect(Collectors.toList());

				if(recipient.getUniqueId().toString().equals(player.getUniqueId().toString()))
				{
					src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You cannot send a private message to yourself!"));
					return CommandResult.success();
				}

				src.sendMessage(Texts.of(TextColors.GOLD, "[", TextColors.RED, player.getName(), TextColors.GOLD, " > ", TextColors.RED, recipient.getName(), TextColors.GOLD, "]: ", TextColors.GRAY, message));
				recipient.sendMessage(Texts.of(TextColors.GOLD, "[", TextColors.RED, player.getName(), TextColors.GOLD, " > ", TextColors.RED, recipient.getName(), TextColors.GOLD, "]: ", TextColors.GRAY, message));

				Message messageToRemove = null;

				for (Message m : Main.recentlyMessaged)
				{
					if(m.getRecipient().getUniqueId().equals(recipient.getUniqueId()))
					{
						messageToRemove = m;
						break;
					}
				}

				if(messageToRemove != null)
				{
					Main.recentlyMessaged.remove(messageToRemove);
				}

				Message msg = new Message(player, recipient, message);
				Main.recentlyMessaged.add(msg);

				for (Player socialspy : socialSpies)
				{
					socialspy.sendMessage(Texts.of(TextColors.GOLD, "[", TextColors.RED, player.getName(), TextColors.GOLD, " > ", TextColors.RED, recipient.getName(), TextColors.GOLD, "]: ", TextColors.GRAY, message));
				}
			} else
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "No recent messages found!"));
			}
		} else if(src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be an in-game player to use /r!"));
		} else if(src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be an in-game player to use /r!"));
		}

		return CommandResult.success();
	}
}