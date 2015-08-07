package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import io.github.hsyyid.spongeessentialcmds.utils.Utils;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.option.OptionSubject;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.source.CommandBlockSource;
import org.spongepowered.api.util.command.source.ConsoleSource;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class SetHomeExecutor implements CommandExecutor
{

	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		String homeName = ctx.<String> getOne("home name").get();
		if (src instanceof Player)
		{
			Player player = (Player) src;
			Subject subject = player.getContainingCollection().get(player.getIdentifier());
			String homesAllowed = null;
			if (subject instanceof OptionSubject)
			{
				homesAllowed = ((OptionSubject) subject).getOption("homes").or("");
			}
			if (homesAllowed != null && !(homesAllowed.equals("")))
			{
				if (homesAllowed.equals("unlimited"))
				{
					Utils.setHome(player.getUniqueId(), player.getLocation(), player.getWorld().getName(), homeName);
					src.sendMessage(Texts.of(TextColors.GREEN, "Success: ", TextColors.YELLOW, "Home set."));
				}
				else
				{
					Integer allowedHomes = Integer.parseInt(homesAllowed);
					try
					{
						if(allowedHomes > Utils.getHomes(player.getUniqueId()).size())
						{
							Utils.setHome(player.getUniqueId(), player.getLocation(), player.getWorld().getName(), homeName);
							src.sendMessage(Texts.of(TextColors.GREEN, "Success: ", TextColors.YELLOW, "Home set."));
						}
						else
						{
							src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You have reached the maximum number of homes you are allowed!"));
						}
					}
					catch(NullPointerException e)
					{
						if(allowedHomes > 0)
						{
							Utils.setHome(player.getUniqueId(), player.getLocation(), player.getWorld().getName(), homeName);
							src.sendMessage(Texts.of(TextColors.GREEN, "Success: ", TextColors.YELLOW, "Home set."));
						}
						else
						{
							src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You have reached the maximum number of homes you are allowed!"));
						}
					}
				}
			}
			else
			{
				Utils.setHome(player.getUniqueId(), player.getLocation(), player.getWorld().getName(), homeName);
				src.sendMessage(Texts.of(TextColors.GREEN, "Success: ", TextColors.YELLOW, "Home set."));
			}
		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /sethome!"));
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /sethome!"));
		}

		return CommandResult.success();
	}
}
