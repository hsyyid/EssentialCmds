package io.github.hsyyid.home;

import java.io.IOException;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

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

public class DeleteHomeExecutor implements CommandExecutor
{

	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = Main.getConfigManager();
		String homeName = ctx.<String>getOne("home name").get();
		if(src instanceof Player)
		{
			Player player = (Player) src;
			if(Utils.inConfig(player.getName(), homeName))
			{
				ConfigurationNode homeNode = Main.config.getNode((Object[]) ("home.users." + player.getName() + ".homes").split("\\."));
				
				//Get Value of Home Node
				String homes = homeNode.getString();
				
				//Remove Kit from Kits Node
				String newVal = homes.replace(homeName + ",","");
				homeNode.setValue(newVal);
				
				//Save CONFIG
				try
				{
					configManager.save(Main.config);
					configManager.load();
				}
				catch(IOException e)
				{
					System.out.println("[Home]: Failed to delete home " + homeName);
					src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "The home was not deleted successfully!"));
				}
				//Get Item Node
				ConfigurationNode itemNode = Main.config.getNode((Object[]) ("home.users." + player.getName() + ".").split("\\."));
				itemNode.removeChild(homeName);
				
				//save config
				try
				{
					configManager.save(Main.config);
					configManager.load();
				}
				catch(IOException e)
				{
					System.out.println("[Home]: Failed to remove home " + homeName );
					src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "The home was not deleted successfully!"));
				}
				src.sendMessage(Texts.of(TextColors.GREEN,"Success: ", TextColors.YELLOW, "Deleted home " + homeName));
			}
			else
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "This home doesn't exist!"));
			}
		}
		else if(src instanceof ConsoleSource) {
			src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use /delhome!"));
		}
		else if(src instanceof CommandBlockSource) {
			src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use /delhome!"));
		}
		return CommandResult.success();
	}
}