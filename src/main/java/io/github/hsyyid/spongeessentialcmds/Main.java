package io.github.hsyyid.spongeessentialcmds;

import java.io.File;
import java.io.IOException;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerDeathEvent;
import org.spongepowered.api.event.state.ServerStartedEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.config.DefaultConfig;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.args.GenericArguments;
import org.spongepowered.api.util.command.spec.CommandSpec;
import org.spongepowered.api.world.TeleportHelper;

import com.google.inject.Inject;

@Plugin(id = "SpongeEssentialCmds", name = "SpongeEssentialCmds", version = "0.9")
public class Main 
{
	static Game game = null;
	static ConfigurationNode config = null;
	static ConfigurationLoader<CommentedConfigurationNode> configurationManager;
	static TeleportHelper helper;

	@Inject
	private Logger logger;

	public Logger getLogger()
	{
		return logger;
	}

	@Inject
	@DefaultConfig(sharedRoot = true)
	private File dConfig;

	@Inject
	@DefaultConfig(sharedRoot = true)
	private ConfigurationLoader<CommentedConfigurationNode> confManager;
	
	@Subscribe
	public void onServerStart(ServerStartedEvent event)
	{
		game = event.getGame();
		helper = game.getTeleportHelper();
		//Config File
		try {
			if (!dConfig.exists()) {
				dConfig.createNewFile();
				config = confManager.load();
				config.getNode("home", "users", "HassanS6000", "home", "X").setValue(0);
				config.getNode("home", "users", "HassanS6000", "home", "Y").setValue(0);
				config.getNode("home", "users", "HassanS6000", "home", "Z").setValue(0);
				config.getNode("home", "users", "HassanS6000", "homes").setValue("home,");
				confManager.save(config); 
			}
			configurationManager = confManager;
			config = confManager.load();

		} catch (IOException exception) {
			getLogger().error("The default configuration could not be loaded or created!");
		}

		CommandSpec homeCommandSpec = CommandSpec.builder()
				.description(Texts.of("Home Command"))
				.permission("home.use")
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("home name"))))
				.executor(new HomeExecutor())
				.build();

		game.getCommandDispatcher().register(this, homeCommandSpec, "home");
		
		CommandSpec spawnCommandSpec = CommandSpec.builder()
				.description(Texts.of("Spawn Command"))
				.permission("spawn.use")
				.executor(new SpawnExecutor())
				.build();

		game.getCommandDispatcher().register(this, spawnCommandSpec, "spawn");
		
		CommandSpec setSpawnCommandSpec = CommandSpec.builder()
				.description(Texts.of("Spawn Command"))
				.permission("spawn.set")
				.executor(new SetSpawnExecutor())
				.build();

		game.getCommandDispatcher().register(this, setSpawnCommandSpec, "setspawn");
		
		CommandSpec listHomeCommandSpec = CommandSpec.builder()
				.description(Texts.of("List Home Command"))
				.permission("home.list")
				.arguments(GenericArguments.onlyOne(GenericArguments.integer(Texts.of("page no"))))
				.executor(new ListHomeExecutor())
				.build();

		game.getCommandDispatcher().register(this, listHomeCommandSpec, "homes");
		
		CommandSpec healCommandSpec = CommandSpec.builder()
				.description(Texts.of("Heal Command"))
				.permission("heal.use")
				.executor(new HealExecutor())
				.build();

		game.getCommandDispatcher().register(this, healCommandSpec, "heal");
		
		CommandSpec backCommandSpec = CommandSpec.builder()
				.description(Texts.of("Back Command"))
				.permission("back.use")
				.executor(new BackExecutor())
				.build();

		game.getCommandDispatcher().register(this, backCommandSpec, "back");
		
		CommandSpec flyCommandSpec = CommandSpec.builder()
				.description(Texts.of("Fly Command"))
				.permission("fly.use")
				.executor(new FlyExecutor())
				.build();

		game.getCommandDispatcher().register(this, flyCommandSpec, "fly");

		CommandSpec setHomeCommandSpec = CommandSpec.builder()
				.description(Texts.of("Set Home Command"))
				.permission("home.set")
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("home name"))))
				.executor(new SetHomeExecutor())
				.build();

		game.getCommandDispatcher().register(this, setHomeCommandSpec, "sethome");
		
		CommandSpec deleteHomeCommandSpec = CommandSpec.builder()
				.description(Texts.of("Delete Home Command"))
				.permission("home.delete")
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("home name"))))
				.executor(new DeleteHomeExecutor())
				.build();

		game.getCommandDispatcher().register(this, deleteHomeCommandSpec, "deletehome", "delhome");
		
		CommandSpec feedCommandSpec = CommandSpec.builder()
				.description(Texts.of("Feed Command"))
				.permission("feed.use")
				.executor(new FeedExecutor())
				.build();

		game.getCommandDispatcher().register(this, feedCommandSpec, "feed");
		
		CommandSpec jumpCommandSpec = CommandSpec.builder()
				.description(Texts.of("Jump Command"))
				.permission("jump.use")
				.executor(new JumpExecutor())
				.build();

		game.getCommandDispatcher().register(this, jumpCommandSpec, "jump");

		getLogger().info("-----------------------------");
        getLogger().info("SpongeEssentialCmds was made by HassanS6000!");
        getLogger().info("Please post all errors on the Sponge Thread or on GitHub!");
        getLogger().info("Have fun, and enjoy! :D");
        getLogger().info("-----------------------------");
		getLogger().info("SpongeEssentialCmds loaded!");
	}
	
	@Subscribe
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		Player died = event.getEntity();
		Utils.addLastDeathLocation(died.getUniqueId(), died.getLocation());
	}
	
	public static ConfigurationLoader<CommentedConfigurationNode> getConfigManager()
	{
		return configurationManager;
	}
}
