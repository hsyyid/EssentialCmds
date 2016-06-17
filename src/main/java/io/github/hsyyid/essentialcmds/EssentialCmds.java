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
package io.github.hsyyid.essentialcmds;

import io.github.hsyyid.essentialcmds.internal.CommandLoader;
import io.github.hsyyid.essentialcmds.listeners.BlacklistListener;
import io.github.hsyyid.essentialcmds.listeners.ChangeBlockListener;
import io.github.hsyyid.essentialcmds.listeners.CommandListener;
import io.github.hsyyid.essentialcmds.listeners.CommandSentListener;
import io.github.hsyyid.essentialcmds.listeners.MailListener;
import io.github.hsyyid.essentialcmds.listeners.MessageSinkListener;
import io.github.hsyyid.essentialcmds.listeners.PlayerClickListener;
import io.github.hsyyid.essentialcmds.listeners.PlayerDamageListener;
import io.github.hsyyid.essentialcmds.listeners.PlayerDeathListener;
import io.github.hsyyid.essentialcmds.listeners.PlayerDisconnectListener;
import io.github.hsyyid.essentialcmds.listeners.PlayerInteractListener;
import io.github.hsyyid.essentialcmds.listeners.PlayerJoinListener;
import io.github.hsyyid.essentialcmds.listeners.PlayerMoveListener;
import io.github.hsyyid.essentialcmds.listeners.SignChangeListener;
import io.github.hsyyid.essentialcmds.listeners.TPAListener;
import io.github.hsyyid.essentialcmds.listeners.WeatherChangeListener;
import io.github.hsyyid.essentialcmds.managers.config.Config;
import io.github.hsyyid.essentialcmds.managers.config.HomeConfig;
import io.github.hsyyid.essentialcmds.managers.config.InventoryConfig;
import io.github.hsyyid.essentialcmds.managers.config.JailConfig;
import io.github.hsyyid.essentialcmds.managers.config.PlayerDataConfig;
import io.github.hsyyid.essentialcmds.managers.config.RulesConfig;
import io.github.hsyyid.essentialcmds.managers.config.SpawnConfig;
import io.github.hsyyid.essentialcmds.managers.config.WarpConfig;
import io.github.hsyyid.essentialcmds.managers.config.WorldConfig;
import io.github.hsyyid.essentialcmds.utils.AFK;
import io.github.hsyyid.essentialcmds.utils.EssLogger;
import io.github.hsyyid.essentialcmds.utils.Message;
import io.github.hsyyid.essentialcmds.utils.PendingInvitation;
import io.github.hsyyid.essentialcmds.utils.Powertool;
import io.github.hsyyid.essentialcmds.utils.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import me.flibio.updatifier.Updatifier;

import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

@Updatifier(repoName = "EssentialCmds", repoOwner = "hsyyid", version = "v" + PluginInfo.VERSION)
@Plugin(id = PluginInfo.ID, name = PluginInfo.NAME, version = PluginInfo.INFORMATIVE_VERSION, description = PluginInfo.DESCRIPTION, dependencies = @Dependency(id = "Updatifier", version = "1.0", optional = true) )
public class EssentialCmds
{
	protected EssentialCmds()
	{
		;
	}

	private static EssentialCmds essentialCmds;

	public static HashMap<UUID, AFK> afkList = new HashMap<>();
	public static List<PendingInvitation> pendingInvites = Lists.newArrayList();
	public static List<Player> recentlyJoined = Lists.newArrayList();
	public static List<Powertool> powertools = Lists.newArrayList();
	public static Set<UUID> socialSpies = Sets.newHashSet();
	public static List<Message> recentlyMessaged = Lists.newArrayList();
	public static Set<UUID> muteList = Sets.newHashSet();
	public static Set<UUID> frozenPlayers = Sets.newHashSet();
	public static Set<UUID> jailedPlayers = Sets.newHashSet();
	public static Set<UUID> teleportingPlayers = Sets.newHashSet();
	public static Set<UUID> godPlayers = Sets.newHashSet();
	public static Set<UUID> flyingPlayers = Sets.newHashSet();

	@Inject
	private EssLogger logger;

	@Inject
	@ConfigDir(sharedRoot = false)
	private Path configDir;

	public static EssentialCmds getEssentialCmds()
	{
		return essentialCmds;
	}

	@Listener
	public void onPreInitialization(GamePreInitializationEvent event)
	{
		essentialCmds = this;
		logger = new EssLogger();

		// Create Config Directory for EssentialCmds
		if (!Files.exists(configDir))
		{
			if (Files.exists(configDir.resolveSibling("essentialcmds")))
			{
				try
				{
					Files.move(configDir.resolveSibling("essentialcmds"), configDir);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				try
				{
					Files.createDirectories(configDir);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		// Create data Directory for EssentialCmds
		if (!Files.exists(configDir.resolve("data")))
		{
			try
			{
				Files.createDirectories(configDir.resolve("data"));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		// Create config.conf
		Config.getConfig().setup();
		// Create warps.conf
		WarpConfig.getConfig().setup();
		// Create homes.conf
		HomeConfig.getConfig().setup();
		// Create rules.conf
		RulesConfig.getConfig().setup();
		// Create jails.conf
		JailConfig.getConfig().setup();
		// Create spawn.conf
		SpawnConfig.getConfig().setup();
		// Create worlds.conf
		WorldConfig.getConfig().setup();
		// Create playerdata.conf
		PlayerDataConfig.getConfig().setup();
		// Create inventory.conf
		InventoryConfig.getConfig().setup();
	}

	@Listener
	public void onServerInit(GameInitializationEvent event)
	{
		getLogger().info(PluginInfo.NAME + " loading...");

		Utils.readMutes();
		Utils.startAFKService();

		// Register all commands.
		CommandLoader.registerCommands();

		getGame().getEventManager().registerListeners(this, new SignChangeListener());
		getGame().getEventManager().registerListeners(this, new PlayerJoinListener());
		getGame().getEventManager().registerListeners(this, new MessageSinkListener());
		getGame().getEventManager().registerListeners(this, new PlayerClickListener());
		getGame().getEventManager().registerListeners(this, new PlayerInteractListener());
		getGame().getEventManager().registerListeners(this, new PlayerMoveListener());
		getGame().getEventManager().registerListeners(this, new PlayerDeathListener());
		getGame().getEventManager().registerListeners(this, new TPAListener());
		getGame().getEventManager().registerListeners(this, new MailListener());
		getGame().getEventManager().registerListeners(this, new PlayerDisconnectListener());
		getGame().getEventManager().registerListeners(this, new WeatherChangeListener());
		getGame().getEventManager().registerListeners(this, new BlacklistListener());
		getGame().getEventManager().registerListeners(this, new CommandListener());
		getGame().getEventManager().registerListeners(this, new ChangeBlockListener());
		getGame().getEventManager().registerListeners(this, new PlayerDamageListener());
		getGame().getEventManager().registerListeners(this, new CommandSentListener());

		getLogger().info("-----------------------------");
		getLogger().info("EssentialCmds was made by HassanS6000!");
		getLogger().info("Please post all errors on the Sponge Thread or on GitHub!");
		getLogger().info("Have fun, and enjoy! :D");
		getLogger().info("-----------------------------");
		getLogger().info("EssentialCmds loaded!");
	}

	public Path getConfigDir()
	{
		return configDir;
	}

	public EssLogger getLogger()
	{
		return logger;
	}

	public Game getGame()
	{
		return Sponge.getGame();
	}

}
