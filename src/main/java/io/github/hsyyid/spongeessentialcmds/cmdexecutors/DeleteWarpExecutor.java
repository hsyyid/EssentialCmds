package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import io.github.hsyyid.spongeessentialcmds.Main;
import io.github.hsyyid.spongeessentialcmds.utils.Utils;
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

public class DeleteWarpExecutor implements CommandExecutor
{

	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = Main.getConfigManager();
		String warpName = ctx.<String> getOne("warp name").get();
		if (src instanceof Player)
		{
			Player player = (Player) src;
			if (Utils.isWarpInConfig(warpName))
			{
				ConfigurationNode warpNode = Main.config.getNode((Object[]) ("warps.warps").split("\\."));

				// Get Value of Warp Node
				String warps = warpNode.getString();

				// Remove Warp
				String newVal = warps.replace(warpName + ",", "");
				warpNode.setValue(newVal);

				// Save CONFIG
				try
				{
					configManager.save(Main.config);
					configManager.load();
				}
				catch (IOException e)
				{
					System.out.println("[Home]: Failed to delete warp " + warpName);
					src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "The warp was not deleted successfully!"));
				}
				// Get Item Node
				ConfigurationNode itemNode = Main.config.getNode((Object[]) ("home.users." + player.getUniqueId() + ".").split("\\."));
				itemNode.removeChild(warpName);

				// save config
				try
				{
					configManager.save(Main.config);
					configManager.load();
				}
				catch (IOException e)
				{
					System.out.println("[Home]: Failed to remove home " + warpName);
					src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "The warp was not deleted successfully!"));
				}
				src.sendMessage(Texts.of(TextColors.GREEN, "Success: ", TextColors.YELLOW, "Deleted warp " + warpName));
			}
			else
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "This warp doesn't exist!"));
			}
		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /delwarp!"));
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /delwarp!"));
		}
		return CommandResult.success();
	}
}
