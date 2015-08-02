package io.github.hsyyid.spongeessentialcmds;

import io.github.hsyyid.spongeessentialcmds.cmdexecutors.BackExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.BroadcastExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.DeleteHomeExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.DeleteWarpExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.FeedExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.FlyExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.HealExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.HomeExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.JumpExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.ListHomeExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.ListWarpExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.SetHomeExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.SetSpawnExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.SetWarpExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.SpawnExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.TPAAcceptExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.TPADenyExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.TPAExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.TPAHereExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.TPHereExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.WarpExecutor;
import io.github.hsyyid.spongeessentialcmds.events.TPAAcceptEvent;
import io.github.hsyyid.spongeessentialcmds.events.TPAEvent;
import io.github.hsyyid.spongeessentialcmds.events.TPAHereAcceptEvent;
import io.github.hsyyid.spongeessentialcmds.events.TPAHereEvent;
import io.github.hsyyid.spongeessentialcmds.utils.PendingInvitation;
import io.github.hsyyid.spongeessentialcmds.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.data.manipulator.tileentity.SignData;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.block.tileentity.SignChangeEvent;
import org.spongepowered.api.event.entity.player.PlayerChatEvent;
import org.spongepowered.api.event.entity.player.PlayerDeathEvent;
import org.spongepowered.api.event.state.ServerStartedEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.config.DefaultConfig;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.option.OptionSubject;
import org.spongepowered.api.service.scheduler.SchedulerService;
import org.spongepowered.api.service.scheduler.Task;
import org.spongepowered.api.service.scheduler.TaskBuilder;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.args.GenericArguments;
import org.spongepowered.api.util.command.spec.CommandSpec;
import org.spongepowered.api.world.TeleportHelper;

import com.google.inject.Inject;

@Plugin(id = "SpongeEssentialCmds", name = "SpongeEssentialCmds", version = "1.5")
public class Main
{
	public static Game game = null;
	public static ConfigurationNode config = null;
	public static ConfigurationLoader<CommentedConfigurationNode> configurationManager;
	public static TeleportHelper helper;
	public static ArrayList<PendingInvitation> pendingInvites = new ArrayList<PendingInvitation>();

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
		getLogger().info("SpongeEssentialCmds loading...");
		game = event.getGame();
		helper = game.getTeleportHelper();
		// Config File
		try
		{
			if (!dConfig.exists())
			{
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

		}
		catch (IOException exception)
		{
			getLogger().error("The default configuration could not be loaded or created!");
		}

		CommandSpec homeCommandSpec = CommandSpec.builder()
			.description(Texts.of("Home Command"))
			.permission("home.use")
			.arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("home name"))))
			.executor(new HomeExecutor())
			.build();

		game.getCommandDispatcher().register(this, homeCommandSpec, "home");

		CommandSpec broadcastCommandSpec = CommandSpec.builder()
			.description(Texts.of("Broadcast Command"))
			.permission("broadcast.use")
			.arguments(GenericArguments.remainingJoinedStrings(Texts.of("message")))
			.executor(new BroadcastExecutor())
			.build();

		game.getCommandDispatcher().register(this, broadcastCommandSpec, "broadcast");

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

		CommandSpec tpaCommandSpec = CommandSpec.builder()
			.description(Texts.of("TPA Command"))
			.permission("tpa.use")
			.arguments(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game)))
			.executor(new TPAExecutor())
			.build();

		game.getCommandDispatcher().register(this, tpaCommandSpec, "tpa");

		CommandSpec tpaHereCommandSpec = CommandSpec.builder()
			.description(Texts.of("TPA Here Command"))
			.permission("tpahere.use")
			.arguments(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game)))
			.executor(new TPAHereExecutor())
			.build();

		game.getCommandDispatcher().register(this, tpaHereCommandSpec, "tpahere");

		CommandSpec tpHereCommandSpec = CommandSpec.builder()
			.description(Texts.of("TP Here Command"))
			.permission("tphere.use")
			.arguments(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game)))
			.executor(new TPHereExecutor())
			.build();

		game.getCommandDispatcher().register(this, tpHereCommandSpec, "tphere");

		CommandSpec tpaAcceptCommandSpec = CommandSpec.builder()
			.description(Texts.of("TPA Accept Command"))
			.permission("tpa.accept")
			.executor(new TPAAcceptExecutor())
			.build();

		game.getCommandDispatcher().register(this, tpaAcceptCommandSpec, "tpaccept");

		CommandSpec listHomeCommandSpec = CommandSpec.builder()
			.description(Texts.of("List Home Command"))
			.permission("home.list")
			.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.integer(Texts.of("page no")))))
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

		CommandSpec tpaDenyCommandSpec = CommandSpec.builder()
			.description(Texts.of("TPA Deny Command"))
			.permission("tpadeny.use")
			.executor(new TPADenyExecutor())
			.build();

		game.getCommandDispatcher().register(this, tpaDenyCommandSpec, "tpadeny");

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
		
		CommandSpec warpCommandSpec = CommandSpec.builder()
			.description(Texts.of("Warp Command"))
			.permission("warp.use")
			.arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("warp name"))))
			.executor(new WarpExecutor())
			.build();

		game.getCommandDispatcher().register(this, warpCommandSpec, "warp");
		
		CommandSpec listWarpCommandSpec = CommandSpec.builder()
			.description(Texts.of("List Warps Command"))
			.permission("warps.list")
			.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.integer(Texts.of("page no")))))
			.executor(new ListWarpExecutor())
			.build();

		game.getCommandDispatcher().register(this, listWarpCommandSpec, "warps");
		
		CommandSpec setWarpCommandSpec = CommandSpec.builder()
			.description(Texts.of("Set Warp Command"))
			.permission("warp.set")
			.arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("warp name"))))
			.executor(new SetWarpExecutor())
			.build();

		game.getCommandDispatcher().register(this, setWarpCommandSpec, "setwarp");

		CommandSpec deleteWarpCommandSpec = CommandSpec.builder()
			.description(Texts.of("Delete Warp Command"))
			.permission("warp.delete")
			.arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("warp name"))))
			.executor(new DeleteWarpExecutor())
			.build();

		game.getCommandDispatcher().register(this, deleteWarpCommandSpec, "deletewarp", "delwarp");

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

	@Subscribe
	public void tpaEventHandler(TPAEvent event)
	{
		String senderName = event.getSender().getName();
		event.getRecipient().sendMessage(Texts.of(TextColors.BLUE, "TPA Request From: ", TextColors.GOLD, senderName + ".", TextColors.RED, " You have 10 seconds to do /tpaccept to accept the request"));

		// Adds Invite to List
		final PendingInvitation invite = new PendingInvitation(event.getSender(), event.getRecipient());
		pendingInvites.add(invite);

		// Removes Invite after 10 Seconds
		SchedulerService scheduler = game.getScheduler();
		TaskBuilder taskBuilder = scheduler.getTaskBuilder();

		Task task = taskBuilder.execute(new Runnable()
		{
			public void run()
			{
				if (pendingInvites.contains(invite))
				{
					pendingInvites.remove(invite);
				}
			}
		}).delay(10, TimeUnit.SECONDS).name("SpongeEssentialCmds - Remove Pending Invite").submit(game.getPluginManager().getPlugin("SpongeEssentialCmds").get().getInstance());
	}

	@Subscribe
	public void tpaAcceptEventHandler(TPAAcceptEvent event)
	{
		String senderName = event.getSender().getName();
		event.getRecipient().sendMessage(Texts.of(TextColors.GREEN, senderName, TextColors.WHITE, " accepted your TPA Request."));
		event.getRecipient().setLocation(event.getSender().getLocation());
	}

	@Subscribe
	public void tpaHereAcceptEventHandler(TPAHereAcceptEvent event)
	{
		String recipientName = event.getRecipient().getName();
		event.getSender().sendMessage(Texts.of(TextColors.GREEN, recipientName, TextColors.WHITE, " accepted your TPA Here Request."));
		event.getSender().setLocation(event.getRecipient().getLocation());
	}

	@Subscribe
	public void tpaHereEventHandler(TPAHereEvent event)
	{
		String senderName = event.getSender().getName();
		event.getRecipient().sendMessage(Texts.of(TextColors.BLUE, senderName, TextColors.GOLD, " has requested for you to teleport to them.", TextColors.RED, " You have 10 seconds to do /tpaccept to accept the request"));

		// Adds Invite to List
		final PendingInvitation invite = new PendingInvitation(event.getSender(), event.getRecipient());
		invite.isTPAHere = true;
		pendingInvites.add(invite);

		// Removes Invite after 10 Seconds
		SchedulerService scheduler = game.getScheduler();
		TaskBuilder taskBuilder = scheduler.getTaskBuilder();

		Task task = taskBuilder.execute(new Runnable()
		{
			public void run()
			{
				if (pendingInvites.contains(invite))
				{
					pendingInvites.remove(invite);
				}
			}
		}).delay(10, TimeUnit.SECONDS).name("SpongeEssentialCmds - Remove Pending Invite").submit(game.getPluginManager().getPlugin("SpongeEssentialCmds").get().getInstance());
	}

	@Subscribe
	public void onMessage(PlayerChatEvent event)
	{
		if(event.getEntity().hasPermission("color.chat.use"))
		{
			String original = Texts.toPlain(event.getMessage());
			String newMessage = original.replaceAll("&", "\u00A7");
			event.setNewMessage(Texts.of(newMessage));
		}
	}

	@Subscribe
	public void onSignChange(SignChangeEvent event)
	{
		SignData signData = event.getNewData();
		String line0 = Texts.toPlain(signData.getLine(0));
		String line1 = Texts.toPlain(signData.getLine(1));
		String line2 = Texts.toPlain(signData.getLine(2));
		String line3 = Texts.toPlain(signData.getLine(3));
		signData.setLine(0, Texts.of(line0.replaceAll("&", "\u00A7")));
		signData.setLine(1, Texts.of(line1.replaceAll("&", "\u00A7")));
		signData.setLine(2, Texts.of(line2.replaceAll("&", "\u00A7")));
		signData.setLine(3, Texts.of(line3.replaceAll("&", "\u00A7")));

		event.setNewData(signData);
	}

	public static ConfigurationLoader<CommentedConfigurationNode> getConfigManager()
	{
		return configurationManager;
	}
}
