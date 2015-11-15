package io.github.hsyyid.essentialcmds.utils;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.hsyyid.essentialcmds.EssentialCmds;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.data.manipulator.mutable.entity.FoodData;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.scheduler.SchedulerService;
import org.spongepowered.api.service.scheduler.Task;
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

	public static void saveMutes()
	{
		Connection c;
		Statement stmt;

		try
		{
			if (Utils.useMySQL())
			{
				SqlService sql = EssentialCmds.game.getServiceManager().provide(SqlService.class).get();
				String host = Utils.getMySQLHost();
				String port = String.valueOf(Utils.getMySQLPort());
				String username = Utils.getMySQLUsername();
				String password = Utils.getMySQLPassword();
				String database = Utils.getMySQLDatabase();

				DataSource datasource = sql.getDataSource("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + username + "&password=" + password);

				String executeString = ("DROP TABLE IF EXISTS MUTES");
				execute(executeString, datasource);

				executeString = "CREATE TABLE MUTES " +
					"(UUID TEXT PRIMARY KEY     NOT NULL)";
				execute(executeString, datasource);

				for (UUID mute : EssentialCmds.muteList)
				{
					String UUID = mute.toString();

					executeString = "INSERT INTO MUTES (UUID) " +
						"VALUES ('" + UUID + "');";
					execute(executeString, datasource);
				}
			}
			else
			{
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:Mutes.db");
				stmt = c.createStatement();
				String sql = ("DROP TABLE IF EXISTS MUTES");
				stmt.executeUpdate(sql);

				sql = "CREATE TABLE MUTES " +
					"(UUID TEXT PRIMARY KEY     NOT NULL)";
				stmt.executeUpdate(sql);

				for (UUID mute : EssentialCmds.muteList)
				{
					String UUID = mute.toString();

					sql = "INSERT INTO MUTES (UUID) " +
						"VALUES ('" + UUID + "');";
					stmt.executeUpdate(sql);
				}

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
		SchedulerService scheduler = EssentialCmds.game.getScheduler();
		Task.Builder taskBuilder = scheduler.createTaskBuilder();

		taskBuilder.execute(() -> {
			for (Player player : EssentialCmds.game.getServer().getOnlinePlayers())
			{
				for (AFK afk : EssentialCmds.movementList)
				{
					if (afk.getPlayer() == player && ((System.currentTimeMillis() - afk.lastMovementTime) > (Utils.getAFK())) && !afk.getMessaged())
					{
						for (Player p : EssentialCmds.game.getServer().getOnlinePlayers())
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
		}).interval(1, TimeUnit.SECONDS).name("EssentialCmds - AFK")
			.submit(EssentialCmds.game.getPluginManager().getPlugin("EssentialCmds").get().getInstance());
	}

	public static String getMySQLPort()
	{
		try
		{
			ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("mysql.port").split("\\."));

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
			ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("mysql.database").split("\\."));

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
			ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("mysql.password").split("\\."));

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
			ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("mysql.username").split("\\."));

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
			ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("mysql.host").split("\\."));

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
		ConfigurationNode valueNode = EssentialCmds.config.getNode("mysql", "use");

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
		ConfigurationLoader<CommentedConfigurationNode> configManager = EssentialCmds.getConfigManager();
		EssentialCmds.config.getNode("mysql", "use").setValue(value);

		try
		{
			configManager.save(EssentialCmds.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("[SpongeEssentialCmds]: Failed to update config.");
		}
	}

	public static String getLastTimePlayerJoined(UUID uuid)
	{
		try
		{
			ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("player." + uuid.toString() + ".time").split("\\."));

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
		ConfigurationLoader<CommentedConfigurationNode> configManager = EssentialCmds.getConfigManager();
		EssentialCmds.config.getNode("player", uuid.toString(), "time").setValue(time);

		try
		{
			configManager.save(EssentialCmds.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("[SpongeEssentialCmds]: Failed to update config.");
		}
	}

	public static String getLoginMessage()
	{
		ConfigurationNode valueNode = EssentialCmds.config.getNode("login", "message");

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
		ConfigurationLoader<CommentedConfigurationNode> configManager = EssentialCmds.getConfigManager();
		EssentialCmds.config.getNode("login", "message").setValue(value);

		try
		{
			configManager.save(EssentialCmds.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("[SpongeEssentialCmds]: Failed to update config.");
		}
	}

	public static String getDisconnectMessage()
	{
		ConfigurationNode valueNode = EssentialCmds.config.getNode("disconnect", "message");

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
		ConfigurationLoader<CommentedConfigurationNode> configManager = EssentialCmds.getConfigManager();
		EssentialCmds.config.getNode("disconnect", "message").setValue(value);

		try
		{
			configManager.save(EssentialCmds.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("[SpongeEssentialCmds]: Failed to update config.");
		}
	}

	public static boolean unsafeEnchanmentsEnabled()
	{
		ConfigurationNode valueNode = EssentialCmds.config.getNode("unsafeenchantments", "enabled");

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
		ConfigurationLoader<CommentedConfigurationNode> configManager = EssentialCmds.getConfigManager();
		EssentialCmds.config.getNode("unsafeenchantments", "enabled").setValue(value);

		try
		{
			configManager.save(EssentialCmds.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("[SpongeEssentialCmds]: Failed to update config.");
		}
	}

	public static void setSQLPort(String value)
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = EssentialCmds.getConfigManager();
		EssentialCmds.config.getNode("mysql", "port").setValue(value);

		try
		{
			configManager.save(EssentialCmds.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("[SpongeEssentialCmds]: Failed to update config.");
		}
	}

	public static void setSQLDatabase(String value)
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = EssentialCmds.getConfigManager();
		EssentialCmds.config.getNode("mysql", "database").setValue(value);

		try
		{
			configManager.save(EssentialCmds.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("[SpongeEssentialCmds]: Failed to update config.");
		}
	}

	public static void setSQLHost(String value)
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = EssentialCmds.getConfigManager();
		EssentialCmds.config.getNode("mysql", "host").setValue(value);

		try
		{
			configManager.save(EssentialCmds.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("[SpongeEssentialCmds]: Failed to update config.");
		}
	}

	public static void setSQLPass(String value)
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = EssentialCmds.getConfigManager();
		EssentialCmds.config.getNode("mysql", "password").setValue(value);

		try
		{
			configManager.save(EssentialCmds.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("[SpongeEssentialCmds]: Failed to update config.");
		}
	}

	public static void setSQLUsername(String value)
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = EssentialCmds.getConfigManager();
		EssentialCmds.config.getNode("mysql", "username").setValue(value);

		try
		{
			configManager.save(EssentialCmds.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("[SpongeEssentialCmds]: Failed to update config.");
		}
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
				SqlService sql = EssentialCmds.game.getServiceManager().provide(SqlService.class).get();
				String host = Utils.getMySQLHost();
				String port = String.valueOf(Utils.getMySQLPort());
				String username = Utils.getMySQLUsername();
				String password = Utils.getMySQLPassword();
				String database = Utils.getMySQLDatabase();

				DataSource datasource = sql.getDataSource("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + username + "&password=" + password);

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
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:Mutes.db");
				c.setAutoCommit(false);
				stmt = c.createStatement();
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
		ConfigurationLoader<CommentedConfigurationNode> configManager = EssentialCmds.getConfigManager();
		ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("rules.rules").split("\\."));

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

		try
		{
			configManager.save(EssentialCmds.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("[EssentialCmds]: Failed to add rule to config!");
		}
	}

	public static void removeRule(int ruleIndex)
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = EssentialCmds.getConfigManager();
		ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("rules.rules").split("\\."));

		String ruleToRemove = Utils.getRules().get(ruleIndex);

		if (valueNode.getString() != null && ruleToRemove != null)
		{
			String items = valueNode.getString();

			if (items.contains(ruleToRemove + ","))
			{
				valueNode.setValue(items.replace(ruleToRemove + ",", ""));
			}
		}

		try
		{
			configManager.save(EssentialCmds.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("[EssentialCmds]: Failed to add rule to config!");
		}
	}

	public static void setHome(UUID userName, Location<World> playerLocation, String worldName, String homeName)
	{
		String playerName = userName.toString();
		ConfigurationLoader<CommentedConfigurationNode> configManager = EssentialCmds.getConfigManager();
		EssentialCmds.config.getNode("home", "users", playerName, homeName, "world").setValue(worldName);
		EssentialCmds.config.getNode("home", "users", playerName, homeName, "X").setValue(playerLocation.getX());
		EssentialCmds.config.getNode("home", "users", playerName, homeName, "Y").setValue(playerLocation.getY());
		EssentialCmds.config.getNode("home", "users", playerName, homeName, "Z").setValue(playerLocation.getZ());
		ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("home.users." + playerName + "." + "homes").split("\\."));
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

		try
		{
			configManager.save(EssentialCmds.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("[SpongeEssentialCmds]: Failed to add " + playerName + "'s home!");
		}
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
		ConfigurationLoader<CommentedConfigurationNode> configManager = EssentialCmds.getConfigManager();
		EssentialCmds.config.getNode("warps", warpName, "world").setValue(playerLocation.getExtent().getUniqueId().toString());
		EssentialCmds.config.getNode("warps", warpName, "X").setValue(playerLocation.getX());
		EssentialCmds.config.getNode("warps", warpName, "Y").setValue(playerLocation.getY());
		EssentialCmds.config.getNode("warps", warpName, "Z").setValue(playerLocation.getZ());
		ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("warps.warps").split("\\."));
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

		try
		{
			configManager.save(EssentialCmds.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("[SpongeEssentialCmds]: Failed to add warp!");
		}
	}

	public static String getJoinMsg()
	{
		ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("joinmsg").split("\\."));
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
		ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("chat.firstcharacter").split("\\."));
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
		ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("chat.lastcharacter").split("\\."));
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
		ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("afk.timer").split("\\."));
		if (valueNode.getDouble() != 0)
		{
			return valueNode.getDouble();
		}
		else
		{
			Utils.setAFK(30000);
			ConfigurationNode valNode = EssentialCmds.config.getNode((Object[]) ("afk.timer").split("\\."));
			return valNode.getDouble();
		}
	}

	public static boolean getAFKKick()
	{
		ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("afk.kick.use").split("\\."));

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
		ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("afk.kick.timer").split("\\."));

		if (valueNode.getValue() != null)
		{
			return valueNode.getDouble();
		}
		else
		{
			Utils.setAFKKickTimer(30000);
			ConfigurationNode valNode = EssentialCmds.config.getNode((Object[]) ("afk.timer").split("\\."));
			return valNode.getDouble();
		}
	}

	public static void setSpawn(Location<World> playerLocation, String worldName)
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = EssentialCmds.getConfigManager();
		EssentialCmds.config.getNode("spawn", "X").setValue(playerLocation.getX());
		EssentialCmds.config.getNode("spawn", "Y").setValue(playerLocation.getY());
		EssentialCmds.config.getNode("spawn", "Z").setValue(playerLocation.getZ());
		EssentialCmds.config.getNode("spawn", "world").setValue(worldName);
		try
		{
			configManager.save(EssentialCmds.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("[SpongeEssentialCmds]: Failed to set spawn");
		}
	}

	public static void setJoinMsg(String msg)
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = EssentialCmds.getConfigManager();
		EssentialCmds.config.getNode("joinmsg").setValue(msg);
		try
		{
			configManager.save(EssentialCmds.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("[SpongeEssentialCmds]: Failed to add Join Message to Config!");
		}
	}

	public static void setFirstChatCharReplacement(String replacement)
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = EssentialCmds.getConfigManager();
		EssentialCmds.config.getNode("chat", "firstcharacter").setValue(replacement);
		try
		{
			configManager.save(EssentialCmds.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("[SpongeEssentialCmds]: Failed to set First Chat Character in Config!");
		}
	}

	public static void setLastChatCharReplacement(String replacement)
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = EssentialCmds.getConfigManager();
		EssentialCmds.config.getNode("chat", "lastcharacter").setValue(replacement);
		try
		{
			configManager.save(EssentialCmds.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("[SpongeEssentialCmds]: Failed to set Last Chat Character in Config!");
		}
	}

	public static void setAFK(double length)
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = EssentialCmds.getConfigManager();
		EssentialCmds.config.getNode("afk", "timer").setValue(length);
		try
		{
			configManager.save(EssentialCmds.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("[SpongeEssentialCmds]: Failed to add AFK to config");
		}
	}

	public static void setAFKKick(boolean val)
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = EssentialCmds.getConfigManager();
		EssentialCmds.config.getNode("afk", "kick", "use").setValue(val);
		try
		{
			configManager.save(EssentialCmds.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("[SpongeEssentialCmds]: Failed to add AFK to config");
		}
	}

	public static void setAFKKickTimer(double length)
	{
		ConfigurationLoader<CommentedConfigurationNode> configManager = EssentialCmds.getConfigManager();
		EssentialCmds.config.getNode("afk", "kick", "timer").setValue(length);
		try
		{
			configManager.save(EssentialCmds.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("[SpongeEssentialCmds]: Failed to add AFK to config");
		}
	}

	public static void setLastDeathLocation(UUID userName, Location<World> playerLocation)
	{
		String playerName = userName.toString();
		ConfigurationLoader<CommentedConfigurationNode> configManager = EssentialCmds.getConfigManager();
		EssentialCmds.config.getNode("back", "users", playerName, "lastDeath", "X").setValue(playerLocation.getX());
		EssentialCmds.config.getNode("back", "users", playerName, "lastDeath", "Y").setValue(playerLocation.getY());
		EssentialCmds.config.getNode("back", "users", playerName, "lastDeath", "Z").setValue(playerLocation.getZ());

		try
		{
			configManager.save(EssentialCmds.config);
			configManager.load();
		}
		catch (IOException e)
		{
			System.out.println("[SpongeEssentialCmds]: Failed to add " + playerName + "'s last death location!");
		}
	}

	public static Location<World> lastDeath(Player player)
	{
		String playerName = player.getUniqueId().toString();
		ConfigurationNode xNode = EssentialCmds.config.getNode((Object[]) ("back.users." + playerName + "." + "lastDeath.X").split("\\."));
		double x = xNode.getDouble();
		ConfigurationNode yNode = EssentialCmds.config.getNode((Object[]) ("back.users." + playerName + "." + "lastDeath.Y").split("\\."));
		double y = yNode.getDouble();
		ConfigurationNode zNode = EssentialCmds.config.getNode((Object[]) ("back.users." + playerName + "." + "lastDeath.Z").split("\\."));
		double z = zNode.getDouble();

		return new Location<>(player.getWorld(), x, y, z);
	}

	public static ArrayList<String> getRules()
	{
		try
		{
			ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("rules.rules").split("\\."));
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
		ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("home.users." + playerName + "." + "homes").split("\\."));
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
		ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("warps.warps").split("\\."));
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
		ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("home.users." + userName + "." + homeName + ".X").split("\\."));
		return valueNode.getDouble();
	}

	public static String getHomeWorldName(UUID playerName, String homeName)
	{
		String userName = playerName.toString();
		ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("home.users." + userName + "." + homeName + ".world").split("\\."));
		return valueNode.getString();
	}

	public static double getY(UUID playerName, String homeName)
	{
		String userName = playerName.toString();
		ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("home.users." + userName + "." + homeName + ".Y").split("\\."));
		return valueNode.getDouble();
	}

	public static double getZ(UUID playerName, String homeName)
	{
		String userName = playerName.toString();
		ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("home.users." + userName + "." + homeName + ".Z").split("\\."));
		return valueNode.getDouble();
	}

	public static double getWarpX(String warpName)
	{
		ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("warps." + warpName + ".X").split("\\."));
		return valueNode.getDouble();
	}

	public static UUID getWarpWorldUUID(String warpName)
	{
		ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("warps." + warpName + ".world").split("\\."));
		try
		{
			UUID worlduuid = UUID.fromString(valueNode.getString());
			return worlduuid;
		}
		catch (IllegalArgumentException e)
		{
			Optional<WorldProperties> props = EssentialCmds.game.getServer().getWorldProperties(valueNode.getString());
			if (props.isPresent())
			{
				if (props.get().isEnabled())
				{
					Optional<World> world = EssentialCmds.game.getServer().loadWorld(valueNode.getString());
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
		ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("spawn.world").split("\\."));
		return valueNode.getString();
	}

	public static double getWarpY(String warpName)
	{
		ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("warps." + warpName + ".Y").split("\\."));
		return valueNode.getDouble();
	}

	public static double getWarpZ(String warpName)
	{
		ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("warps." + warpName + ".Z").split("\\."));
		return valueNode.getDouble();
	}

	// Check if Player is In Config
	public static boolean inConfig(UUID playerName, String homeName)
	{
		String userName = playerName.toString();
		ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("home.users." + userName + "." + homeName + ".X").split("\\."));
		Object inConfig = valueNode.getValue();
		return inConfig != null;
	}

	public static boolean isWarpInConfig(String warpName)
	{
		ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("warps." + warpName + ".X").split("\\."));
		Object inConfig = valueNode.getValue();
		return inConfig != null;
	}

	public static boolean isSpawnInConfig()
	{
		ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("spawn.X").split("\\."));
		Object inConfig = valueNode.getValue();
		return inConfig != null;
	}

	public static Location<World> getSpawn(Player player)
	{
		ConfigurationNode xNode = EssentialCmds.config.getNode((Object[]) ("spawn.X").split("\\."));
		double x = xNode.getDouble();

		ConfigurationNode yNode = EssentialCmds.config.getNode((Object[]) ("spawn.Y").split("\\."));
		double y = yNode.getDouble();

		ConfigurationNode zNode = EssentialCmds.config.getNode((Object[]) ("spawn.Z").split("\\."));
		double z = zNode.getDouble();

		return new Location<>(player.getWorld(), x, y, z);
	}

	public static boolean isLastDeathInConfig(Player player)
	{
		String userName = player.getUniqueId().toString();
		ConfigurationNode valueNode = EssentialCmds.config.getNode((Object[]) ("back.users." + userName + ".lastDeath.X").split("\\."));
		Object inConfig = valueNode.getValue();
		return inConfig != null;
	}
}
