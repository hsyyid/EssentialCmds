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
package io.github.hsyyid.essentialcmds.utils;

import static io.github.hsyyid.essentialcmds.EssentialCmds.getEssentialCmds;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.hsyyid.essentialcmds.EssentialCmds;
import io.github.hsyyid.essentialcmds.api.util.config.Configs;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.Game;
import org.spongepowered.api.data.manipulator.mutable.entity.FoodData;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.service.sql.SqlService;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.storage.WorldProperties;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

public class Utils
{
	private static Gson gson = new GsonBuilder().create();

	private static Game game = getEssentialCmds().getGame();

	public static void setSQLPort(String value)
	{
		Configs.getConfig().getNode("mysql", "port").setValue(value);

		Configs.saveConfig();
	}

	public static void addMute(UUID playerUUID)
	{
		Connection c;
		Statement stmt;

		try
		{
			if (Utils.useMySQL())
			{
				SqlService sql = game.getServiceManager().provide(SqlService.class).get();
				String host = Utils.getMySQLHost();
				String port = String.valueOf(Utils.getMySQLPort());
				String username = Utils.getMySQLUsername();
				String password = Utils.getMySQLPassword();
				String database = Utils.getMySQLDatabase();

				DataSource datasource = sql.getDataSource("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + username + "&password=" + password);

				String executeString = "CREATE TABLE IF NOT EXISTS MUTES " + "(UUID TEXT PRIMARY KEY     NOT NULL)";
				execute(executeString, datasource);

				executeString = "INSERT INTO MUTES (UUID) " + "VALUES ('" + playerUUID.toString() + "');";
				execute(executeString, datasource);
			}
			else
			{
				try
				{
					Class.forName("org.sqlite.JDBC");
				}
				catch (ClassNotFoundException exception)
				{
					System.out.println("[EssentialCmds]: You do not have ANY database software installed! Mutes will not work or be saved until this is fixed.");
				}

				c = DriverManager.getConnection("jdbc:sqlite:Mutes.db");
				stmt = c.createStatement();

				String sql = "CREATE TABLE IF NOT EXISTS MUTES " + "(UUID TEXT PRIMARY KEY     NOT NULL)";
				stmt.executeUpdate(sql);

				sql = "INSERT INTO MUTES (UUID) " + "VALUES ('" + playerUUID.toString() + "');";
				stmt.executeUpdate(sql);

				stmt.close();
				c.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void removeMute(UUID playerUUID)
	{
		Connection c;
		Statement stmt;

		try
		{
			if (Utils.useMySQL())
			{
				SqlService sql = game.getServiceManager().provide(SqlService.class).get();
				String host = Utils.getMySQLHost();
				String port = String.valueOf(Utils.getMySQLPort());
				String username = Utils.getMySQLUsername();
				String password = Utils.getMySQLPassword();
				String database = Utils.getMySQLDatabase();

				DataSource datasource = sql.getDataSource("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + username + "&password=" + password);

				String executeString = "CREATE TABLE IF NOT EXISTS MUTES " + "(UUID TEXT PRIMARY KEY     NOT NULL)";
				execute(executeString, datasource);

				executeString = "DELETE FROM MUTES WHERE UUID='" + playerUUID.toString() + "';";
				execute(executeString, datasource);
			}
			else
			{
				try
				{
					Class.forName("org.sqlite.JDBC");
				}
				catch (ClassNotFoundException exception)
				{
					System.out.println("[EssentialCmds]: You do not have ANY database software installed! Mutes will not work or be saved until this is fixed.");
				}
				
				c = DriverManager.getConnection("jdbc:sqlite:Mutes.db");
				stmt = c.createStatement();

				String sql = "CREATE TABLE IF NOT EXISTS MUTES " + "(UUID TEXT PRIMARY KEY     NOT NULL)";
				stmt.executeUpdate(sql);

				sql = "DELETE FROM MUTES WHERE UUID='" + playerUUID.toString() + "';";
				stmt.executeUpdate(sql);

				stmt.close();
				c.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void startAFKService()
	{
		Scheduler scheduler = game.getScheduler();
		Task.Builder taskBuilder = scheduler.createTaskBuilder();

		taskBuilder.execute(() -> {
			for (Player player : game.getServer().getOnlinePlayers())
			{
				for (AFK afk : EssentialCmds.movementList)
				{
					if (afk.getPlayer().getUniqueId().equals(player.getUniqueId()) && ((System.currentTimeMillis() - afk.lastMovementTime) > Utils.getAFK()) && !afk.getMessaged())
					{
						for (Player p : game.getServer().getOnlinePlayers())
						{
							p.sendMessage(Texts.of(TextColors.BLUE, player.getName(), TextColors.GOLD, " is now AFK."));
							Optional<FoodData> data = p.get(FoodData.class);
							if (data.isPresent())
							{
								FoodData food = data.get();
								afk.setFood(food.foodLevel().get());
							}
						}
						afk.setMessaged(true);
						afk.setAFK(true);
					}

					if (afk.getAFK())
					{
						Player p = afk.getPlayer();
						Optional<FoodData> data = p.get(FoodData.class);
						if (data.isPresent())
						{
							FoodData food = data.get();
							if (food.foodLevel().get() < afk.getFood())
							{
								Value<Integer> foodLevel = food.foodLevel().set(afk.getFood());
								food.set(foodLevel);
								p.offer(food);
							}
						}

						if (!(p.hasPermission("afk.kick.false")) && Utils.getAFKKick() && afk.getLastMovementTime() >= Utils.getAFKKickTimer())
						{
							p.kick(Texts.of(TextColors.GOLD, "Kicked for being AFK too long."));
						}
					}
				}
			}
		}).interval(1, TimeUnit.SECONDS).name("EssentialCmds - AFK").submit(game.getPluginManager().getPlugin("EssentialCmds").get().getInstance().get());
	}

	public static String getMySQLPort()
	{
		try
		{
			ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("mysql.port").split("\\."));

			if (valueNode.getValue() != null)
			{
				return valueNode.getString();
			}
			else
			{
				setSQLPort("8080");
				return "";
			}
		}
		catch (Exception e)
		{
			setSQLPort("8080");
			return "";
		}
	}

	public static String getMySQLDatabase()
	{
		try
		{
			ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("mysql.database").split("\\."));

			if (valueNode.getValue() != null)
			{
				return valueNode.getString();
			}
			else
			{
				setSQLDatabase("Mutes");
				return "";
			}
		}
		catch (Exception e)
		{
			setSQLDatabase("Mutes");
			return "";
		}
	}

	public static String getMySQLPassword()
	{
		try
		{
			ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("mysql.password").split("\\."));

			if (valueNode.getValue() != null)
			{
				return valueNode.getString();
			}
			else
			{
				setSQLPass("cat");
				return "";
			}
		}
		catch (Exception e)
		{
			setSQLPass("cats");
			return "";
		}
	}

	public static String getMySQLUsername()
	{
		try
		{
			ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("mysql.username").split("\\."));

			if (valueNode.getValue() != null)
			{
				return valueNode.getString();
			}
			else
			{
				setSQLUsername("HassanS6000");
				return "";
			}
		}
		catch (Exception e)
		{
			setSQLUsername("HassanS6000");
			return "";
		}
	}

	public static String getMySQLHost()
	{
		try
		{
			ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("mysql.host").split("\\."));

			if (valueNode.getValue() != null)
			{
				return valueNode.getString();
			}
			else
			{
				setSQLHost("null");
				return "";
			}
		}
		catch (Exception e)
		{
			setSQLHost("null");
			return "";
		}
	}

	public static boolean useMySQL()
	{
		ConfigurationNode valueNode = Configs.getConfig().getNode("mysql", "use");

		if (valueNode.getValue() != null)
		{
			return valueNode.getBoolean();
		}
		else
		{
			setUseMySQL(false);
			return false;
		}
	}

	public static void setUseMySQL(boolean value)
	{

		Configs.getConfig().getNode("mysql", "use").setValue(value);

		Configs.saveConfig();
	}

	public static String getLastTimePlayerJoined(UUID uuid)
	{
		try
		{
			ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("player." + uuid.toString() + ".time").split("\\."));

			if (valueNode.getValue() != null)
			{
				return valueNode.getString();
			}
			else
			{
				return "";
			}
		}
		catch (Exception e)
		{
			return "";
		}
	}

	public static void setLastTimePlayerJoined(UUID uuid, String time)
	{
		Configs.getConfig().getNode("player", uuid.toString(), "time").setValue(time);

		Configs.saveConfig();
	}

	public static String getLoginMessage()
	{
		ConfigurationNode valueNode = Configs.getConfig().getNode("login", "message");

		if (valueNode.getValue() != null)
		{
			return valueNode.getString();
		}
		else
		{
			setLoginMessage("");
			return null;
		}
	}

	public static void setLoginMessage(String value)
	{

		Configs.getConfig().getNode("login", "message").setValue(value);

		Configs.saveConfig();
	}

	public static String getDisconnectMessage()
	{
		ConfigurationNode valueNode = Configs.getConfig().getNode("disconnect", "message");

		if (valueNode.getValue() != null)
		{
			return valueNode.getString();
		}
		else
		{
			setDisconnectMessage("");
			return null;
		}
	}

	public static void setDisconnectMessage(String value)
	{

		Configs.getConfig().getNode("disconnect", "message").setValue(value);

		Configs.saveConfig();
	}

	public static boolean unsafeEnchanmentsEnabled()
	{
		ConfigurationNode valueNode = Configs.getConfig().getNode("unsafeenchantments", "enabled");

		if (valueNode.getValue() != null)
		{
			return valueNode.getBoolean();
		}
		else
		{
			setUnsafeEnchanmentsEnabled(false);
			return false;
		}
	}

	public static void setUnsafeEnchanmentsEnabled(boolean value)
	{

		Configs.getConfig().getNode("unsafeenchantments", "enabled").setValue(value);

		Configs.getConfig().getNode("mysql", "port").setValue(value);

		Configs.saveConfig();
	}

	public static void setSQLDatabase(String value)
	{

		Configs.getConfig().getNode("mysql", "database").setValue(value);

		Configs.saveConfig();
	}

	public static void setSQLHost(String value)
	{

		Configs.getConfig().getNode("mysql", "host").setValue(value);

		Configs.saveConfig();
	}

	public static void setSQLPass(String value)
	{

		Configs.getConfig().getNode("mysql", "password").setValue(value);

		Configs.saveConfig();
	}

	public static void setSQLUsername(String value)
	{

		Configs.getConfig().getNode("mysql", "username").setValue(value);

		Configs.saveConfig();
	}

	public static void execute(String execute, DataSource datasource)
	{
		try
		{
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			statement.execute(execute);
			statement.close();
			connection.close();

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public static void readMutes()
	{
		try
		{
			if (Utils.useMySQL())
			{
				SqlService sql = game.getServiceManager().provide(SqlService.class).get();
				String host = Utils.getMySQLHost();
				String port = String.valueOf(Utils.getMySQLPort());
				String username = Utils.getMySQLUsername();
				String password = Utils.getMySQLPassword();
				String database = Utils.getMySQLDatabase();

				DataSource datasource = sql.getDataSource("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + username + "&password=" + password);

				String executeString = "CREATE TABLE IF NOT EXISTS MUTES " + "(UUID TEXT PRIMARY KEY  NOT NULL)";
				execute(executeString, datasource);
				
				DatabaseMetaData metadata = datasource.getConnection().getMetaData();
				ResultSet rs = metadata.getTables(null, null, "Mutes", null);
				Set<UUID> muteList = Sets.newHashSet();

				while (rs.next())
				{
					String uuid = rs.getString("uuid");
					muteList.add(UUID.fromString(uuid));
				}

				EssentialCmds.muteList = muteList;
				rs.close();
			}
			else
			{
				Connection c;
				Statement stmt;
				
				try
				{
					Class.forName("org.sqlite.JDBC");
				}
				catch (ClassNotFoundException exception)
				{
					System.out.println("[EssentialCmds]: You do not have ANY database software installed! Mutes will not work or be saved until this is fixed.");
				}
				
				c = DriverManager.getConnection("jdbc:sqlite:Mutes.db");
				c.setAutoCommit(false);
				stmt = c.createStatement();
				
				String sql = "CREATE TABLE IF NOT EXISTS MUTES " + "(UUID TEXT PRIMARY KEY     NOT NULL)";
				stmt.executeUpdate(sql);
				
				ResultSet rs = stmt.executeQuery("SELECT * FROM MUTES;");
				Set<UUID> muteList = Sets.newHashSet();

				while (rs.next())
				{
					String uuid = rs.getString("uuid");
					muteList.add(UUID.fromString(uuid));
				}

				EssentialCmds.muteList = muteList;

				rs.close();
				stmt.close();
				c.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void addRule(String rule)
	{

		ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("rules.rules").split("\\."));

		if (valueNode.getString() != null)
		{
			String items = valueNode.getString();

			if (!items.contains(rule + ","))
			{
				String formattedItem = (rule + ",");
				valueNode.setValue(items + formattedItem);
			}
		}
		else
		{
			valueNode.setValue(rule + ",");
		}

		Configs.saveConfig();
	}

	public static void removeRule(int ruleIndex)
	{

		ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("rules.rules").split("\\."));

		String ruleToRemove = Utils.getRules().get(ruleIndex);

		if (valueNode.getString() != null && ruleToRemove != null)
		{
			String items = valueNode.getString();

			if (items.contains(ruleToRemove + ","))
			{
				valueNode.setValue(items.replace(ruleToRemove + ",", ""));
			}
		}

		Configs.saveConfig();
	}

	public static void setHome(UUID userName, Location<World> playerLocation, String worldName, String homeName)
	{
		String playerName = userName.toString();

		Configs.getConfig().getNode("home", "users", playerName, homeName, "world").setValue(worldName);
		Configs.getConfig().getNode("home", "users", playerName, homeName, "X").setValue(playerLocation.getX());
		Configs.getConfig().getNode("home", "users", playerName, homeName, "Y").setValue(playerLocation.getY());
		Configs.getConfig().getNode("home", "users", playerName, homeName, "Z").setValue(playerLocation.getZ());
		ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("home.users." + playerName + "." + "homes").split("\\."));
		if (valueNode.getString() != null)
		{
			String items = valueNode.getString();
			if (!items.contains(homeName + ","))
			{
				String formattedItem = (homeName + ",");
				valueNode.setValue(items + formattedItem);
			}
		}
		else
		{
			valueNode.setValue(homeName + ",");
		}

		Configs.saveConfig();
	}

	public static ArrayList<Mail> getMail()
	{
		String json;

		try
		{
			json = readFile("Mail.json", StandardCharsets.UTF_8);
		}
		catch (Exception e)
		{
			return new ArrayList<>();
		}

		if (json != null && json.length() > 0)
		{
			return new ArrayList<>(Arrays.asList(gson.fromJson(json, Mail[].class)));
		}
		else
		{
			return new ArrayList<>();
		}
	}

	static String readFile(String path, Charset encoding) throws IOException
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	public static void addMail(String senderName, String recipientName, String message)
	{
		if (Utils.getMail() != null)
		{
			ArrayList<Mail> currentMail = Utils.getMail();

			currentMail.add(new Mail(recipientName, senderName, message));
			String json;

			try
			{
				json = gson.toJson(currentMail);

				// Assume default encoding.
				FileWriter fileWriter = new FileWriter("Mail.json");

				// Always wrap FileWriter in BufferedWriter.
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

				bufferedWriter.write(json);
				bufferedWriter.flush();

				// Always close files.
				bufferedWriter.close();
			}
			catch (Exception ex)
			{
				System.out.println("Could not save JSON file!");
			}
		}
	}

	public static void removeMail(Mail mail)
	{
		if (Utils.getMail() != null)
		{
			ArrayList<Mail> currentMail = Utils.getMail();

			Mail mailToRemove = null;

			for (Mail m : currentMail)
			{
				if (m.getRecipientName().equals(mail.getRecipientName()) && m.getSenderName().equals(mail.getSenderName()) && m.getMessage().equals(mail.getMessage()))
				{
					mailToRemove = m;
					break;
				}
			}

			if (mailToRemove != null)
			{
				currentMail.remove(mailToRemove);
			}

			String json;

			try
			{
				json = gson.toJson(currentMail);

				// Assume default encoding.
				FileWriter fileWriter = new FileWriter("Mail.json");

				// Always wrap FileWriter in BufferedWriter.
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

				bufferedWriter.write(json);
				bufferedWriter.flush();

				// Always close files.
				bufferedWriter.close();
			}
			catch (Exception ex)
			{
				System.out.println("Could not save JSON file!");
			}
		}
	}

	public static void setWarp(Location<World> playerLocation, String warpName)
	{

		Configs.getConfig().getNode("warps", warpName, "world").setValue(playerLocation.getExtent().getUniqueId().toString());
		Configs.getConfig().getNode("warps", warpName, "X").setValue(playerLocation.getX());
		Configs.getConfig().getNode("warps", warpName, "Y").setValue(playerLocation.getY());
		Configs.getConfig().getNode("warps", warpName, "Z").setValue(playerLocation.getZ());
		ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("warps.warps").split("\\."));
		if (valueNode.getString() != null)
		{
			String items = valueNode.getString();
			if (!items.contains(warpName + ","))
			{
				String formattedItem = (warpName + ",");
				valueNode.setValue(items + formattedItem);
			}
		}
		else
		{
			valueNode.setValue(warpName + ",");
		}

		Configs.saveConfig();
	}

	public static String getJoinMsg()
	{
		ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("joinmsg").split("\\."));
		if (valueNode.getValue() != null)
		{
			return valueNode.getString();
		}
		else
		{
			Utils.setJoinMsg("&4Welcome");
			return "&4Welcome";
		}
	}

	public static String getFirstChatCharReplacement()
	{
		ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("chat.firstcharacter").split("\\."));
		if (valueNode.getValue() != null)
		{
			return valueNode.getString();
		}
		else
		{
			Utils.setFirstChatCharReplacement("<");
			return "<";
		}
	}

	public static String getLastChatCharReplacement()
	{
		ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("chat.lastcharacter").split("\\."));
		if (valueNode.getValue() != null)
		{
			return valueNode.getString();
		}
		else
		{
			Utils.setLastChatCharReplacement(">");
			return ">";
		}
	}

	public static double getAFK()
	{
		ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("afk.timer").split("\\."));
		if (valueNode.getDouble() != 0)
		{
			return valueNode.getDouble();
		}
		else
		{
			Utils.setAFK(30000);
			ConfigurationNode valNode = Configs.getConfig().getNode((Object[]) ("afk.timer").split("\\."));
			return valNode.getDouble();
		}
	}

	public static boolean getAFKKick()
	{
		ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("afk.kick.use").split("\\."));

		if (valueNode.getValue() != null)
		{
			return valueNode.getBoolean();
		}
		else
		{
			Utils.setAFKKick(false);
			return false;
		}
	}

	public static double getAFKKickTimer()
	{
		ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("afk.kick.timer").split("\\."));

		if (valueNode.getValue() != null)
		{
			return valueNode.getDouble();
		}
		else
		{
			Utils.setAFKKickTimer(30000);
			ConfigurationNode valNode = Configs.getConfig().getNode((Object[]) ("afk.timer").split("\\."));
			return valNode.getDouble();
		}
	}

	public static void setSpawn(Location<World> playerLocation, String worldName)
	{

		Configs.getConfig().getNode("spawn", "X").setValue(playerLocation.getX());
		Configs.getConfig().getNode("spawn", "Y").setValue(playerLocation.getY());
		Configs.getConfig().getNode("spawn", "Z").setValue(playerLocation.getZ());
		Configs.getConfig().getNode("spawn", "world").setValue(worldName);

		Configs.saveConfig();
	}

	public static void setJoinMsg(String msg)
	{

		Configs.getConfig().getNode("joinmsg").setValue(msg);

		Configs.saveConfig();
	}

	public static void setFirstChatCharReplacement(String replacement)
	{

		Configs.getConfig().getNode("chat", "firstcharacter").setValue(replacement);
		Configs.saveConfig();
	}

	public static void setLastChatCharReplacement(String replacement)
	{

		Configs.getConfig().getNode("chat", "lastcharacter").setValue(replacement);
		Configs.saveConfig();
	}

	public static void setAFK(double length)
	{

		Configs.getConfig().getNode("afk", "timer").setValue(length);
		Configs.saveConfig();
	}

	public static void setAFKKick(boolean val)
	{

		Configs.getConfig().getNode("afk", "kick", "use").setValue(val);
		Configs.saveConfig();
	}

	public static void setAFKKickTimer(double length)
	{

		Configs.getConfig().getNode("afk", "kick", "timer").setValue(length);
		Configs.saveConfig();
	}

	public static void setLastDeathLocation(UUID userName, Location<World> playerLocation)
	{
		String playerName = userName.toString();

		Configs.getConfig().getNode("back", "users", playerName, "lastDeath", "X").setValue(playerLocation.getX());
		Configs.getConfig().getNode("back", "users", playerName, "lastDeath", "Y").setValue(playerLocation.getY());
		Configs.getConfig().getNode("back", "users", playerName, "lastDeath", "Z").setValue(playerLocation.getZ());
		Configs.getConfig().getNode("back", "users", playerName, "lastDeath", "worldUUID").setValue(playerLocation.getExtent().getUniqueId().toString());

		Configs.saveConfig();
	}

	public static Location<World> lastDeath(Player player)
	{
		try
		{
			String playerName = player.getUniqueId().toString();
			ConfigurationNode xNode = Configs.getConfig().getNode((Object[]) ("back.users." + playerName + "." + "lastDeath.X").split("\\."));
			double x = xNode.getDouble();
			ConfigurationNode yNode = Configs.getConfig().getNode((Object[]) ("back.users." + playerName + "." + "lastDeath.Y").split("\\."));
			double y = yNode.getDouble();
			ConfigurationNode zNode = Configs.getConfig().getNode((Object[]) ("back.users." + playerName + "." + "lastDeath.Z").split("\\."));
			double z = zNode.getDouble();
			ConfigurationNode worldNode = Configs.getConfig().getNode((Object[]) ("back.users." + playerName + "." + "lastDeath.worldUUID").split("\\."));
			UUID worldUUID = UUID.fromString(worldNode.getString());
			Optional<World> world = game.getServer().getWorld(worldUUID);

			if (world.isPresent())
				return new Location<World>(world.get(), x, y, z);
			else
				return null;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	public static ArrayList<String> getRules()
	{
		try
		{
			ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("rules.rules").split("\\."));
			String list = valueNode.getString();

			ArrayList<String> rulesList = new ArrayList<>();
			boolean finished = false;

			int endIndex = list.indexOf(",");

			if (endIndex != -1)
			{
				String substring = list.substring(0, endIndex);
				rulesList.add(substring);

				while (!finished)
				{
					int startIndex = endIndex;
					endIndex = list.indexOf(",", startIndex + 1);

					if (endIndex != -1)
					{
						String substrings = list.substring(startIndex + 1, endIndex);
						rulesList.add(substrings);
					}
					else
					{
						finished = true;
					}
				}
			}

			return rulesList;
		}
		catch (Exception e)
		{
			return new ArrayList<String>();
		}
	}

	public static ArrayList<String> getHomes(UUID userName)
	{
		String playerName = userName.toString();
		ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("home.users." + playerName + "." + "homes").split("\\."));
		String list = valueNode.getString();

		ArrayList<String> homeList = new ArrayList<>();
		boolean finished = false;

		// Add all homes to homeList
		int endIndex = list.indexOf(",");
		if (endIndex != -1)
		{
			String substring = list.substring(0, endIndex);
			homeList.add(substring);

			// If they Have More than 1
			while (!finished)
			{
				int startIndex = endIndex;
				endIndex = list.indexOf(",", startIndex + 1);

				if (endIndex != -1)
				{
					String substrings = list.substring(startIndex + 1, endIndex);
					homeList.add(substrings);
				}
				else
				{
					finished = true;
				}
			}
		}

		return homeList;
	}

	public static ArrayList<String> getWarps()
	{
		ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("warps.warps").split("\\."));
		String list = valueNode.getString();

		ArrayList<String> warpList = new ArrayList<>();
		boolean finished = false;

		// Add all homes to warpList
		int endIndex = list.indexOf(",");
		if (endIndex != -1)
		{
			String substring = list.substring(0, endIndex);
			warpList.add(substring);

			// If they Have More than 1
			while (!finished)
			{
				int startIndex = endIndex;
				endIndex = list.indexOf(",", startIndex + 1);
				if (endIndex != -1)
				{
					String substrings = list.substring(startIndex + 1, endIndex);
					warpList.add(substrings);
				}
				else
				{
					finished = true;
				}
			}
		}
		else
		{
			warpList.add(list);
		}

		return warpList;
	}

	public static double getX(UUID playerName, String homeName)
	{
		String userName = playerName.toString();
		ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("home.users." + userName + "." + homeName + ".X").split("\\."));
		return valueNode.getDouble();
	}

	public static String getHomeWorldName(UUID playerName, String homeName)
	{
		String userName = playerName.toString();
		ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("home.users." + userName + "." + homeName + ".world").split("\\."));
		return valueNode.getString();
	}

	public static double getY(UUID playerName, String homeName)
	{
		String userName = playerName.toString();
		ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("home.users." + userName + "." + homeName + ".Y").split("\\."));
		return valueNode.getDouble();
	}

	public static double getZ(UUID playerName, String homeName)
	{
		String userName = playerName.toString();
		ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("home.users." + userName + "." + homeName + ".Z").split("\\."));
		return valueNode.getDouble();
	}

	public static double getWarpX(String warpName)
	{
		ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("warps." + warpName + ".X").split("\\."));
		return valueNode.getDouble();
	}

	public static UUID getWarpWorldUUID(String warpName)
	{
		ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("warps." + warpName + ".world").split("\\."));
		try
		{
			UUID worlduuid = UUID.fromString(valueNode.getString());
			return worlduuid;
		}
		catch (IllegalArgumentException e)
		{
			Optional<WorldProperties> props = game.getServer().getWorldProperties(valueNode.getString());
			if (props.isPresent())
			{
				if (props.get().isEnabled())
				{
					Optional<World> world = game.getServer().loadWorld(valueNode.getString());
					if (world.isPresent())
					{
						return world.get().getUniqueId();
					}
				}
			}
		}
		throw new IllegalArgumentException();
	}

	public static String getSpawnWorldName()
	{
		ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("spawn.world").split("\\."));
		return valueNode.getString();
	}

	public static double getWarpY(String warpName)
	{
		ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("warps." + warpName + ".Y").split("\\."));
		return valueNode.getDouble();
	}

	public static double getWarpZ(String warpName)
	{
		ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("warps." + warpName + ".Z").split("\\."));
		return valueNode.getDouble();
	}

	// Check if Player is In Config
	public static boolean inConfig(UUID playerName, String homeName)
	{
		String userName = playerName.toString();
		ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("home.users." + userName + "." + homeName + ".X").split("\\."));
		Object inConfig = valueNode.getValue();
		return inConfig != null;
	}

	public static boolean isWarpInConfig(String warpName)
	{
		ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("warps." + warpName + ".X").split("\\."));
		Object inConfig = valueNode.getValue();
		return inConfig != null;
	}

	public static boolean isSpawnInConfig()
	{
		ConfigurationNode valueNode = Configs.getConfig().getNode((Object[]) ("spawn.X").split("\\."));
		Object inConfig = valueNode.getValue();
		return inConfig != null;
	}

	public static Location<World> getSpawn(Player player)
	{
		ConfigurationNode xNode = Configs.getConfig().getNode((Object[]) ("spawn.X").split("\\."));
		double x = xNode.getDouble();

		ConfigurationNode yNode = Configs.getConfig().getNode((Object[]) ("spawn.Y").split("\\."));
		double y = yNode.getDouble();

		ConfigurationNode zNode = Configs.getConfig().getNode((Object[]) ("spawn.Z").split("\\."));
		double z = zNode.getDouble();

		return new Location<>(player.getWorld(), x, y, z);
	}

	public static boolean isLastDeathInConfig(Player player)
	{
		return Utils.lastDeath(player) != null;
	}
}
