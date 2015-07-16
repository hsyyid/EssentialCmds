package io.github.hsyyid.home;

import java.io.File;
import java.io.IOException;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.state.ServerStartedEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.config.DefaultConfig;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.spec.CommandSpec;

import com.google.inject.Inject;

@Plugin(id = "Home", name = "Home", version = "0.2")
public class Main 
{
	static Game game = null;
	static ConfigurationNode config = null;
	static ConfigurationLoader<CommentedConfigurationNode> configurationManager;

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
		getLogger().info("Home Plugin loading...");

		//Config File
		try {
			if (!dConfig.exists()) {
				dConfig.createNewFile();
				config = confManager.load();
				config.getNode("home", "users", "HassanS6000", "home", "X").setValue(0);
				config.getNode("home", "users", "HassanS6000", "home", "Y").setValue(0);
				config.getNode("home", "users", "HassanS6000", "home", "Z").setValue(0);
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
				.executor(new HomeExecutor())
				.build();

		game.getCommandDispatcher().register(this, homeCommandSpec, "home");

		CommandSpec setHomeCommandSpec = CommandSpec.builder()
				.description(Texts.of("Set Home Command"))
				.permission("home.set")
				.executor(new SetHomeExecutor())
				.build();

		game.getCommandDispatcher().register(this, setHomeCommandSpec, "sethome");

		getLogger().info("-----------------------------");
        getLogger().info("Home was made by HassanS6000!");
        getLogger().info("Please post all errors with Home on the Sponge Thread or on GitHub!");
        getLogger().info("Have fun, and enjoy! :D");
        getLogger().info("-----------------------------");
		getLogger().info("Home Plugin loaded!");
	}

	public static ConfigurationLoader<CommentedConfigurationNode> getConfigManager()
	{
		return configurationManager;
	}
}
