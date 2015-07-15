package io.github.hsyyid.home;

import java.io.IOException;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import org.spongepowered.api.world.Location;

public class Utils
{
	public static void setHome(String playerName, Location playerLocation)
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = Main.getConfigManager();
		Main.config.getNode("home", "users", playerName, "home", "X").setValue(playerLocation.getX());
		Main.config.getNode("home", "users", playerName, "home", "Y").setValue(playerLocation.getY());
		Main.config.getNode("home", "users", playerName, "home", "Z").setValue(playerLocation.getZ());
		try {
			configManager.save(Main.config);
			configManager.load();
		} catch(IOException e) {
			System.out.println("[Home]: Failed to add " + playerName + "'s home to config!");
		}
	}


	public static double getX(String userName)
	{
		ConfigurationNode valueNode = Main.config.getNode((Object[]) ("home.users." + userName + ".home.X").split("\\."));
		return valueNode.getDouble();
	}

	public static double getY(String userName)
	{
		ConfigurationNode valueNode = Main.config.getNode((Object[]) ("home.users." + userName + ".home.Y").split("\\."));
		return valueNode.getDouble();
	}

	public static double getZ(String userName)
	{
		ConfigurationNode valueNode = Main.config.getNode((Object[]) ("home.users." + userName + ".home.Z").split("\\."));
		return valueNode.getDouble();
	}
	
	//Check if Player is In Config
	public static boolean inConfig(String userName)
	{
		ConfigurationNode valueNode = Main.config.getNode((Object[]) ("home.users." + userName + ".home.X").split("\\."));
		Object inConfig = valueNode.getValue();
		if(inConfig != null){
			return true;
		}
		else{
			return false;
		}
	}

}
