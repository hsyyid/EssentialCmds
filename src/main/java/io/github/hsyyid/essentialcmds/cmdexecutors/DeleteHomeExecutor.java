package io.github.hsyyid.essentialcmds.cmdexecutors;

import io.github.hsyyid.essentialcmds.utils.Utils;

import io.github.hsyyid.essentialcmds.EssentialCmds;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
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

import java.io.IOException;

public class DeleteHomeExecutor implements CommandExecutor
{

	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = EssentialCmds.getConfigManager();
		String homeName = ctx.<String> getOne("home name").get();
		if (src instanceof Player)
		{
			Player player = (Player) src;
			if (Utils.inConfig(player.getUniqueId(), homeName))
			{
				ConfigurationNode homeNode = EssentialCmds.config.getNode((Object[]) ("home.users." + player.getUniqueId() + ".homes").split("\\."));

				// Get Value of Home Node
				String homes = homeNode.getString();

				// Remove Home from Homes Node
				String newVal = homes.replace(homeName + ",", "");
				homeNode.setValue(newVal);

				// Save CONFIG
				try
				{
					configManager.save(EssentialCmds.config);
					configManager.load();
				}
				catch (IOException e)
				{
					System.out.println("[Home]: Failed to delete home " + homeName);
					src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "The home was not deleted successfully!"));
				}
				// Get Item Node
				ConfigurationNode itemNode = EssentialCmds.config.getNode((Object[]) ("home.users." + player.getUniqueId() + ".").split("\\."));
				itemNode.removeChild(homeName);

				// save config
				try
				{
					configManager.save(EssentialCmds.config);
					configManager.load();
				}
				catch (IOException e)
				{
					System.out.println("[Home]: Failed to remove home " + homeName);
					src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "The home was not deleted successfully!"));
				}

				src.sendMessage(Texts.of(TextColors.GREEN, "Success: ", TextColors.YELLOW, "Deleted home " + homeName));
			}
			else
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "This home doesn't exist!"));
			}
		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /delhome!"));
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /delhome!"));
		}
		return CommandResult.success();
	}
}
