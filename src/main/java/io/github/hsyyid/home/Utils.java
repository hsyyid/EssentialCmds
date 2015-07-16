package io.github.hsyyid.home;

import java.io.IOException;
import java.util.ArrayList;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import org.spongepowered.api.world.Location;

public class Utils
{
	public static void setHome(String playerName, Location playerLocation, String homeName)
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = Main.getConfigManager();
		Main.config.getNode("home", "users", playerName, homeName, "X").setValue(playerLocation.getX());
		Main.config.getNode("home", "users", playerName, homeName, "Y").setValue(playerLocation.getY());
		Main.config.getNode("home", "users", playerName, homeName, "Z").setValue(playerLocation.getZ());
		ConfigurationNode valueNode = Main.config.getNode((Object[]) ("home.users." + playerName + "." + "homes").split("\\."));
		String items = valueNode.getString();
		if(items.contains(homeName + ","));
		else
		{
			String formattedItem = (homeName + ",");
			valueNode.setValue(items + formattedItem);
		}
		
		try {
			configManager.save(Main.config);
			configManager.load();
		} catch(IOException e) {
			System.out.println("[Home]: Failed to add " + playerName + "'s home!");
		}
	}
	
	public static ArrayList<String> getHomes(String playerName)
	{
		ConfigurationNode valueNode =  Main.config.getNode((Object[]) ("home.users." + playerName + "." + "homes").split("\\."));
		String list = valueNode.getString();

		ArrayList<String> homeList = new ArrayList<String>();
		boolean finished = false;

		//Add all homes to homeList
		if(finished != true){
			int endIndex = list.indexOf(",");
			if(endIndex != -1){
				String substring = list.substring(0, endIndex);
				homeList.add(substring);

				//If they Have More than 1
				while(finished != true){
					int startIndex = endIndex;
					endIndex = list.indexOf(",", startIndex + 1);
					if(endIndex != -1){
						String substrings = list.substring(startIndex+1, endIndex);
						homeList.add(substrings);
					}
					else{
						finished = true;
					}
				}
			}
			else
			{
				homeList.add(list);
				finished = true;
			}
		}

		return homeList;
	}


	public static double getX(String userName, String homeName)
	{
		ConfigurationNode valueNode = Main.config.getNode((Object[]) ("home.users." + userName + "." + homeName + ".X").split("\\."));
		return valueNode.getDouble();
	}

	public static double getY(String userName, String homeName)
	{
		ConfigurationNode valueNode = Main.config.getNode((Object[]) ("home.users." + userName + "." + homeName + ".Y").split("\\."));
		return valueNode.getDouble();
	}

	public static double getZ(String userName, String homeName)
	{
		ConfigurationNode valueNode = Main.config.getNode((Object[]) ("home.users." + userName + "." + homeName + ".Z").split("\\."));
		return valueNode.getDouble();
	}

	//Check if Player is In Config
	public static boolean inConfig(String userName, String homeName)
	{
		ConfigurationNode valueNode = Main.config.getNode((Object[]) ("home.users." + userName + "." + homeName + ".X").split("\\."));
		Object inConfig = valueNode.getValue();
		if(inConfig != null){
			return true;
		}
		else{
			return false;
		}
	}

}
