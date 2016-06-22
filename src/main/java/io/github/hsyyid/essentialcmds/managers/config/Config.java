/*
 * This file is part of EssentialCmds, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2015 - 2015 HassanS6000
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.github.hsyyid.essentialcmds.managers.config;

import io.github.hsyyid.essentialcmds.EssentialCmds;
import io.github.hsyyid.essentialcmds.api.util.config.Configurable;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Handles the config.conf file
 */
public class Config implements Configurable
{
	private static Config config = new Config();

	private Config()
	{
		;
	}

	public static Config getConfig()
	{
		return config;
	}

	private Path configFile = Paths.get(EssentialCmds.getEssentialCmds().getConfigDir() + "/config.conf");
	private ConfigurationLoader<CommentedConfigurationNode> configLoader = HoconConfigurationLoader.builder().setPath(configFile).build();
	private CommentedConfigurationNode configNode;

	@Override
	public void setup()
	{
		if (!Files.exists(configFile))
		{
			try
			{
				Files.createFile(configFile);
				load();
				populate();
				save();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			load();
		}
	}

	@Override
	public void load()
	{
		try
		{
			configNode = configLoader.load();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void save()
	{
		try
		{
			configLoader.save(configNode);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void populate()
	{
		get().getNode("afk", "timer").setValue(300000).setComment("The length of time in millisec until a player is AFK.");
		get().getNode("afk", "announce").setValue(true).setComment("Toggles the announcing of when a player is AFK.");
		get().getNode("afk", "kick", "use").setValue(false).setComment("When enabled, it enables the kicking of players who are AFK.");
		get().getNode("afk", "kick", "timer").setValue(30000).setComment("Sets the amount of time until a player is kicked for being AFK.");
		get().getNode("message", "firstjoin").setValue("&4Welcome &a@p &4to the server!").setComment("Message when a player first joins the server.");
		get().getNode("message", "join").setValue("&4Welcome!").setComment("Message sent to a player when they join the server.");
		get().getNode("message", "login").setValue("").setComment("Message sent to everyone when a player joins.");
		get().getNode("message", "disconnect").setValue("").setComment("Message sent to everyone when a player disconnects.");
		get().getNode("mysql").setComment("MySQL Options for EssentialCmds.");
		get().getNode("mysql", "use").setValue(false).setComment("Enables/Disables MySQL usage for EssentialCmds.");
		get().getNode("mysql", "port").setValue("8080").setComment("Port of MySQL Database.");
		get().getNode("mysql", "host").setValue("localhost").setComment("Address of MySQL Database.");
		get().getNode("mysql", "database").setValue("EssentialCmds").setComment("Name of MySQL Database.");
		get().getNode("mysql", "username").setValue("root").setComment("Username for MySQL Database.");
		get().getNode("mysql", "password").setValue("pass").setComment("Password for MySQL Database.");
		get().getNode("chat", "firstcharacter").setValue("<").setComment("When set, changes the first character in chat from '<'.");
		get().getNode("chat", "lastcharacter").setValue(">").setComment("When set, changes the character after the player name from '>'.");
		get().getNode("teleport", "cooldown", "enabled").setValue(false).setComment("Toggles the cooldown option for teleports.");
		get().getNode("teleport", "cooldown", "timer").setValue(10).setComment("The length of time in seconds until a player teleports.");
		get().getNode("log", "command", "player").setValue(true).setComment("Toggles the logging of player commands.");
		get().getNode("log", "command", "console").setValue(true).setComment("Toggles the logging of console commands.");
		get().getNode("log", "command", "command-block").setValue(false).setComment("Toggles the logging of command block commands.");
		get().getNode("log", "command", "other").setValue(false).setComment("Toggles the logging of any other command blocks.");
		get().getNode("blacklist", "messages").setValue(true).setComment("Toggles blacklist messages.");
	}

	@Override
	public CommentedConfigurationNode get()
	{
		return configNode;
	}
}
