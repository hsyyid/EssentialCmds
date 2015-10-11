package io.github.hsyyid.spongeessentialcmds;

import com.google.inject.Inject;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.AFKExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.BackExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.BanExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.BroadcastExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.DeleteHomeExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.DeleteWarpExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.FeedExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.FlyExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.GamemodeExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.GetPosExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.HatExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.HealExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.HomeExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.JumpExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.KickExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.KillExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.LightningExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.ListHomeExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.ListWarpExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.MailExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.MailListExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.MailReadExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.MessageExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.MotdExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.MuteExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.NickExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.PardonExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.PowertoolExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.RepairExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.RespondExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.SetHomeExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.SetSpawnExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.SetWarpExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.SocialSpyExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.SpawnExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.SpeedExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.SudoExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.TPAAcceptExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.TPADenyExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.TPAExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.TPAHereExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.TPHereExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.TeleportPosExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.TimeExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.UnmuteExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.WarpExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.WeatherExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.WhoisExecutor;
import io.github.hsyyid.spongeessentialcmds.listeners.MailListener;
import io.github.hsyyid.spongeessentialcmds.listeners.MessageSinkListener;
import io.github.hsyyid.spongeessentialcmds.listeners.PlayerClickListener;
import io.github.hsyyid.spongeessentialcmds.listeners.PlayerDeathListener;
import io.github.hsyyid.spongeessentialcmds.listeners.PlayerInteractListener;
import io.github.hsyyid.spongeessentialcmds.listeners.PlayerJoinListener;
import io.github.hsyyid.spongeessentialcmds.listeners.PlayerMoveListener;
import io.github.hsyyid.spongeessentialcmds.listeners.SignChangeListener;
import io.github.hsyyid.spongeessentialcmds.listeners.TPAListener;
import io.github.hsyyid.spongeessentialcmds.utils.AFK;
import io.github.hsyyid.spongeessentialcmds.utils.Message;
import io.github.hsyyid.spongeessentialcmds.utils.Mute;
import io.github.hsyyid.spongeessentialcmds.utils.PendingInvitation;
import io.github.hsyyid.spongeessentialcmds.utils.Powertool;
import io.github.hsyyid.spongeessentialcmds.utils.Utils;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.config.DefaultConfig;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.args.GenericArguments;
import org.spongepowered.api.util.command.spec.CommandSpec;
import org.spongepowered.api.world.TeleportHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

@Plugin(id = "SpongeEssentialCmds", name = "SpongeEssentialCmds", version = "4.1")
public class Main
{
	public static Game game;
	public static ConfigurationNode config;
	public static ConfigurationLoader<CommentedConfigurationNode> configurationManager;
	public static TeleportHelper helper;
	public static ArrayList<PendingInvitation> pendingInvites = new ArrayList<>();
	public static ArrayList<AFK> movementList = new ArrayList<>();
	public static ArrayList<Player> recentlyJoined = new ArrayList<>();
	public static ArrayList<Powertool> powertools = new ArrayList<>();
	public static ArrayList<UUID> socialSpies = new ArrayList<>();
	public static ArrayList<Message> recentlyMessaged = new ArrayList<>();
	public static ArrayList<Mute> muteList = new ArrayList<>();

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

	@Listener
	public void onServerInit(GameInitializationEvent event)
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
				config.getNode("afk", "timer").setValue(30000);
				config.getNode("afk", "kick", "use").setValue(false);
				config.getNode("afk", "kick", "timer").setValue(30000);
				config.getNode("joinmsg").setValue("&4Welcome!");
				confManager.save(config);
			}

			configurationManager = confManager;
			config = confManager.load();
		}
		catch (IOException exception)
		{
			getLogger().error("The default configuration could not be loaded or created!");
		}
		Utils.readMutes();

		Utils.readMutes();
		Utils.startAFKService();
		
		CommandSpec homeCommandSpec =
			CommandSpec.builder().description(Texts.of("Home Command")).permission("home.use")
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("home name")))).executor(new HomeExecutor()).build();
		game.getCommandDispatcher().register(this, homeCommandSpec, "home");

		CommandSpec whoIsCommandSpec =
			CommandSpec.builder().description(Texts.of("WhoIs Command")).permission("whois.use")
				.arguments(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game))).executor(new WhoisExecutor()).build();
		game.getCommandDispatcher().register(this, whoIsCommandSpec, "whois");

		CommandSpec getPosCommandSpec =
			CommandSpec.builder().description(Texts.of("GetPos Command")).permission("getpos.use")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game))))
				.executor(new GetPosExecutor()).build();
		game.getCommandDispatcher().register(this, getPosCommandSpec, "getpos");

		CommandSpec gamemodeCommandSpec =
			CommandSpec
				.builder()
				.description(Texts.of("Gamemode Command"))
				.permission("gamemode.use")
				.arguments(
					GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.string(Texts.of("gamemode"))),
						GenericArguments.onlyOne(GenericArguments.optional(GenericArguments.player(Texts.of("player"), game)))))
				.executor(new GamemodeExecutor()).build();
		game.getCommandDispatcher().register(this, gamemodeCommandSpec, "gamemode", "gm");

		CommandSpec motdCommandSpec =
			CommandSpec.builder().description(Texts.of("MOTD Command")).permission("motd.use").executor(new MotdExecutor()).build();
		game.getCommandDispatcher().register(this, motdCommandSpec, "motd");

		CommandSpec socialSpyCommandSpec =
			CommandSpec.builder().description(Texts.of("Allows Toggling of Seeing Other Players Private Messages")).permission("socialspy.use")
				.executor(new SocialSpyExecutor()).build();
		game.getCommandDispatcher().register(this, socialSpyCommandSpec, "socialspy");

		CommandSpec mailListCommandSpec =
			CommandSpec.builder().description(Texts.of("List Mail Command")).permission("mail.list")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.integer(Texts.of("page no")))))
				.executor(new MailListExecutor()).build();
		game.getCommandDispatcher().register(this, mailListCommandSpec, "listmail");

		CommandSpec mailReadCommandSpec =
			CommandSpec.builder().description(Texts.of("Read Mail Command")).permission("mail.read")
				.arguments(GenericArguments.onlyOne(GenericArguments.integer(Texts.of("mail no")))).executor(new MailReadExecutor()).build();
		game.getCommandDispatcher().register(this, mailReadCommandSpec, "readmail");

		CommandSpec msgRespondCommandSpec =
			CommandSpec.builder().description(Texts.of("Respond to Message Command")).permission("message.respond")
				.arguments(GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Texts.of("message"))))
				.executor(new RespondExecutor()).build();
		game.getCommandDispatcher().register(this, msgRespondCommandSpec, "r");

		CommandSpec timeCommandSpec =
			CommandSpec
				.builder()
				.description(Texts.of("Set Time Command"))
				.permission("time.set")
				.arguments(
					GenericArguments.firstParsing(GenericArguments.string(Texts.of("time")), GenericArguments.integer(Texts.of("ticks"))))
				.executor(new TimeExecutor()).build();
		game.getCommandDispatcher().register(this, timeCommandSpec, "time");

		CommandSpec repairCommandSpec =
			CommandSpec.builder().description(Texts.of("Repair Item in Player's Hand")).permission("repair.use").executor(new RepairExecutor())
				.build();
		game.getCommandDispatcher().register(this, repairCommandSpec, "repair");

		CommandSpec mailCommandSpec =
			CommandSpec
				.builder()
				.description(Texts.of("Mail Command"))
				.permission("mail.use")
				.arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.string(Texts.of("player")))),
					GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Texts.of("message")))).executor(new MailExecutor())
				.build();
		game.getCommandDispatcher().register(this, mailCommandSpec, "mail");

		CommandSpec weatherCommandSpec =
			CommandSpec
				.builder()
				.description(Texts.of("Weather Command"))
				.permission("weather.use")
				.arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.string(Texts.of("weather")))),
					GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Texts.of("duration")))))
				.executor(new WeatherExecutor()).build();
		game.getCommandDispatcher().register(this, weatherCommandSpec, "weather");

		CommandSpec banCommandSpec =
			CommandSpec
				.builder()
				.description(Texts.of("Ban Command"))
				.permission("ban.use")
				.arguments(
					GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game)), GenericArguments
						.optional(GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Texts.of("reason"))))))
				.executor(new BanExecutor()).build();
		game.getCommandDispatcher().register(this, banCommandSpec, "ban");

		CommandSpec pardonCommandSpec =
			CommandSpec.builder().description(Texts.of("Unban Command")).permission("unban.use")
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("player")))).executor(new PardonExecutor()).build();
		game.getCommandDispatcher().register(this, pardonCommandSpec, "unban", "pardon");

		CommandSpec teleportPosCommandSpec =
			CommandSpec
				.builder()
				.description(Texts.of("Teleport Position Command"))
				.permission("teleport.pos.use")
				.arguments(
					GenericArguments.seq(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"),
						game)))), GenericArguments.onlyOne(GenericArguments.integer(Texts.of("x"))),
					GenericArguments.onlyOne(GenericArguments.integer(Texts.of("y"))),
					GenericArguments.onlyOne(GenericArguments.integer(Texts.of("z")))).executor(new TeleportPosExecutor()).build();
		game.getCommandDispatcher().register(this, teleportPosCommandSpec, "tppos", "teleportpos", "teleportposition");

		CommandSpec kickCommandSpec =
			CommandSpec
				.builder()
				.description(Texts.of("Kick Command"))
				.permission("kick.use")
				.arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game))),
					GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Texts.of("reason")))).executor(new KickExecutor())
				.build();
		game.getCommandDispatcher().register(this, kickCommandSpec, "kick");

		CommandSpec messageCommandSpec =
			CommandSpec
				.builder()
				.description(Texts.of("Message Command"))
				.permission("message.use")
				.arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.player(Texts.of("recipient"), game))),
					GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Texts.of("message"))))
				.executor(new MessageExecutor()).build();
		game.getCommandDispatcher().register(this, messageCommandSpec, "message", "m", "msg", "tell");

		CommandSpec lightningCommandSpec =
			CommandSpec.builder().description(Texts.of("Lightning Command")).permission("lightning.use")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game))))
				.executor(new LightningExecutor()).build();
		game.getCommandDispatcher().register(this, lightningCommandSpec, "thor", "smite", "lightning");

		CommandSpec sudoCommandSpec =
			CommandSpec
				.builder()
				.description(Texts.of("Sudo Command"))
				.permission("sudo.use")
				.arguments(
					GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game)),
						GenericArguments.remainingJoinedStrings(Texts.of("command")))).executor(new SudoExecutor()).build();
		game.getCommandDispatcher().register(this, sudoCommandSpec, "sudo");

		CommandSpec afkCommandSpec =
			CommandSpec.builder().description(Texts.of("AFK Command")).permission("afk.use").executor(new AFKExecutor()).build();
		game.getCommandDispatcher().register(this, afkCommandSpec, "afk");

		CommandSpec broadcastCommandSpec =
			CommandSpec.builder().description(Texts.of("Broadcast Command")).permission("broadcast.use")
				.arguments(GenericArguments.remainingJoinedStrings(Texts.of("message"))).executor(new BroadcastExecutor()).build();
		game.getCommandDispatcher().register(this, broadcastCommandSpec, "broadcast");

		CommandSpec spawnCommandSpec =
			CommandSpec.builder().description(Texts.of("Spawn Command")).permission("spawn.use").executor(new SpawnExecutor()).build();
		game.getCommandDispatcher().register(this, spawnCommandSpec, "spawn");

		CommandSpec setSpawnCommandSpec =
			CommandSpec.builder().description(Texts.of("Set Spawn Command")).permission("spawn.set").executor(new SetSpawnExecutor()).build();
		game.getCommandDispatcher().register(this, setSpawnCommandSpec, "setspawn");

		CommandSpec tpaCommandSpec =
			CommandSpec.builder().description(Texts.of("TPA Command")).permission("tpa.use")
				.arguments(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game))).executor(new TPAExecutor()).build();
		game.getCommandDispatcher().register(this, tpaCommandSpec, "tpa");

		CommandSpec tpaHereCommandSpec =
			CommandSpec.builder().description(Texts.of("TPA Here Command")).permission("tpahere.use")
				.arguments(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game))).executor(new TPAHereExecutor())
				.build();
		game.getCommandDispatcher().register(this, tpaHereCommandSpec, "tpahere");

		CommandSpec tpHereCommandSpec =
			CommandSpec.builder().description(Texts.of("TP Here Command")).permission("tphere.use")
				.arguments(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game))).executor(new TPHereExecutor())
				.build();
		game.getCommandDispatcher().register(this, tpHereCommandSpec, "tphere");

		CommandSpec tpaAcceptCommandSpec =
			CommandSpec.builder().description(Texts.of("TPA Accept Command")).permission("tpa.accept").executor(new TPAAcceptExecutor()).build();
		game.getCommandDispatcher().register(this, tpaAcceptCommandSpec, "tpaccept");

		CommandSpec listHomeCommandSpec =
			CommandSpec.builder().description(Texts.of("List Home Command")).permission("home.list")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.integer(Texts.of("page no")))))
				.executor(new ListHomeExecutor()).build();
		game.getCommandDispatcher().register(this, listHomeCommandSpec, "homes");

		CommandSpec healCommandSpec =
			CommandSpec.builder().description(Texts.of("Heal Command")).permission("heal.use")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game))))
				.executor(new HealExecutor()).build();
		game.getCommandDispatcher().register(this, healCommandSpec, "heal");

		CommandSpec backCommandSpec =
			CommandSpec.builder().description(Texts.of("Back Command")).permission("back.use").executor(new BackExecutor()).build();
		game.getCommandDispatcher().register(this, backCommandSpec, "back");

		CommandSpec tpaDenyCommandSpec =
			CommandSpec.builder().description(Texts.of("TPA Deny Command")).permission("tpadeny.use").executor(new TPADenyExecutor()).build();
		game.getCommandDispatcher().register(this, tpaDenyCommandSpec, "tpadeny");

		CommandSpec hatCommandSpec =
			CommandSpec.builder().description(Texts.of("Hat Command")).permission("hat.use").executor(new HatExecutor()).build();
		game.getCommandDispatcher().register(this, hatCommandSpec, "hat");

		CommandSpec flyCommandSpec =
			CommandSpec.builder().description(Texts.of("Fly Command")).permission("fly.use")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("palyer"), game))))
				.executor(new FlyExecutor()).build();
		game.getCommandDispatcher().register(this, flyCommandSpec, "fly");

		CommandSpec setHomeCommandSpec =
			CommandSpec.builder().description(Texts.of("Set Home Command")).permission("home.set")
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("home name")))).executor(new SetHomeExecutor()).build();
		game.getCommandDispatcher().register(this, setHomeCommandSpec, "sethome");

		CommandSpec deleteHomeCommandSpec =
			CommandSpec.builder().description(Texts.of("Delete Home Command")).permission("home.delete")
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("home name")))).executor(new DeleteHomeExecutor())
				.build();
		game.getCommandDispatcher().register(this, deleteHomeCommandSpec, "deletehome", "delhome");

		CommandSpec warpCommandSpec =
			CommandSpec.builder().description(Texts.of("Warp Command")).permission("warp.use")
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("warp name")))).executor(new WarpExecutor()).build();
		game.getCommandDispatcher().register(this, warpCommandSpec, "warp");

		CommandSpec listWarpCommandSpec =
			CommandSpec.builder().description(Texts.of("List Warps Command")).permission("warps.list")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.integer(Texts.of("page no")))))
				.executor(new ListWarpExecutor()).build();
		game.getCommandDispatcher().register(this, listWarpCommandSpec, "warps");

		CommandSpec setWarpCommandSpec =
			CommandSpec.builder().description(Texts.of("Set Warp Command")).permission("warp.set")
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("warp name")))).executor(new SetWarpExecutor()).build();
		game.getCommandDispatcher().register(this, setWarpCommandSpec, "setwarp");

		CommandSpec deleteWarpCommandSpec =
			CommandSpec.builder().description(Texts.of("Delete Warp Command")).permission("warp.delete")
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("warp name")))).executor(new DeleteWarpExecutor())
				.build();
		game.getCommandDispatcher().register(this, deleteWarpCommandSpec, "deletewarp", "delwarp");

		CommandSpec feedCommandSpec =
			CommandSpec.builder().description(Texts.of("Feed Command")).permission("feed.use")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game))))
				.executor(new FeedExecutor()).build();
		game.getCommandDispatcher().register(this, feedCommandSpec, "feed");

		CommandSpec unmuteCommnadSpec =
			CommandSpec.builder().description(Texts.of("Unmute Command")).permission("unmute.use")
				.arguments(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game))).executor(new UnmuteExecutor())
				.build();
		game.getCommandDispatcher().register(this, unmuteCommnadSpec, "unmute");

		CommandSpec killCommandSpec =
			CommandSpec.builder().description(Texts.of("Kill Command")).permission("kill.use")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game))))
				.executor(new KillExecutor()).build();
		game.getCommandDispatcher().register(this, killCommandSpec, "kill");

		CommandSpec jumpCommandSpec =
			CommandSpec.builder().description(Texts.of("Jump Command")).permission("jump.use").executor(new JumpExecutor()).build();
		game.getCommandDispatcher().register(this, jumpCommandSpec, "jump");

		CommandSpec speedCommandSpec =
			CommandSpec.builder().description(Texts.of("Speed Command")).permission("speed.use")
				.arguments(GenericArguments.onlyOne(GenericArguments.integer(Texts.of("speed"))))
				.executor(new SpeedExecutor()).build();
		game.getCommandDispatcher().register(this, speedCommandSpec, "speed");

		CommandSpec powertoolCommandSpec =
			CommandSpec.builder().description(Texts.of("Powertool Command")).permission("powertool.use")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Texts.of("command")))))
				.executor(new PowertoolExecutor()).build();
		game.getCommandDispatcher().register(this, powertoolCommandSpec, "powertool");

		CommandSpec nickCommandSpec =
			CommandSpec
				.builder()
				.description(Texts.of("Nick Command"))
				.permission("nick.use")
				.arguments(
					GenericArguments.seq(
						GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game))),
						GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Texts.of("nick")))))
				.executor(new NickExecutor()).build();
		game.getCommandDispatcher().register(this, nickCommandSpec, "nick");

		CommandSpec muteCommandSpec =
			CommandSpec
				.builder()
				.description(Texts.of("Mute Command"))
				.permission("mute.use")
				.arguments(
					GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game)),
						GenericArguments.onlyOne(GenericArguments.optional(GenericArguments.integer(Texts.of("time")))),
						GenericArguments.onlyOne(GenericArguments.optional(GenericArguments.string(Texts.of("time unit"))))))
				.executor(new MuteExecutor()).build();
		game.getCommandDispatcher().register(this, muteCommandSpec, "mute");

		game.getEventManager().registerListeners(this, new SignChangeListener());
		game.getEventManager().registerListeners(this, new PlayerJoinListener());
		game.getEventManager().registerListeners(this, new MessageSinkListener());
		game.getEventManager().registerListeners(this, new PlayerClickListener());
		game.getEventManager().registerListeners(this, new PlayerInteractListener());
		game.getEventManager().registerListeners(this, new PlayerMoveListener());
		game.getEventManager().registerListeners(this, new PlayerDeathListener());
		game.getEventManager().registerListeners(this, new TPAListener());
		game.getEventManager().registerListeners(this, new MailListener());
		
		getLogger().info("-----------------------------");
		getLogger().info("SpongeEssentialCmds was made by HassanS6000!");
		getLogger().info("Please post all errors on the Sponge Thread or on GitHub!");
		getLogger().info("Have fun, and enjoy! :D");
		getLogger().info("-----------------------------");
		getLogger().info("SpongeEssentialCmds loaded!");
	}

	@Listener
	public void onServerStop(GameStoppingServerEvent event)
	{
		Utils.saveMutes();
	}

	public static ConfigurationLoader<CommentedConfigurationNode> getConfigManager()
	{
		return configurationManager;
	}
}
