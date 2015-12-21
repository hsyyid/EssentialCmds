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

import static io.github.hsyyid.essentialcmds.PluginInfo.ID;
import static io.github.hsyyid.essentialcmds.PluginInfo.NAME;
import static io.github.hsyyid.essentialcmds.PluginInfo.VERSION;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import io.github.hsyyid.essentialcmds.cmdexecutors.AFKExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.AddRuleExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.AsConsoleExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.BackExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.BanExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.BlockInfoExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.BroadcastExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.ButcherExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.CreateWorldExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.DeleteHomeExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.DeleteWarpExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.DeleteWorldExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.DirectionExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.EnchantExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.EntityInfoExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.FeedExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.FireballExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.FlyExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.GamemodeExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.GetPosExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.HatExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.HealExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.HomeExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.IgniteExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.ItemInfoExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.JumpExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.KickExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.KillExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.LightningExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.ListHomeExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.ListWarpExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.ListWorldExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.LoadWorldExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.LockWeatherExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.MailExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.MailListExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.MailReadExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.MessageExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.MobSpawnExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.MobSpawnerExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.MoreExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.MotdExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.MuteExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.NickExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.PardonExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.PlayerFreezeExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.PowertoolExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.RTPExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.RemoveRuleExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.RepairExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.RespondExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.RuleExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.SetHomeExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.SetSpawnExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.SetWarpExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.SkullExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.SocialSpyExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.SpawnExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.SpeedExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.SudoExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.TPAAcceptExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.TPADenyExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.TPAExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.TPAHereExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.TPHereExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.TeleportExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.TeleportPosExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.TeleportWorldExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.ThruExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.TimeExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.UnmuteExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.VanishExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.WarpExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.WeatherExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.WhoisExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.argumentparsers.UserParser;
import io.github.hsyyid.essentialcmds.listeners.MailListener;
import io.github.hsyyid.essentialcmds.listeners.MessageSinkListener;
import io.github.hsyyid.essentialcmds.listeners.PlayerClickListener;
import io.github.hsyyid.essentialcmds.listeners.PlayerDeathListener;
import io.github.hsyyid.essentialcmds.listeners.PlayerDisconnectListener;
import io.github.hsyyid.essentialcmds.listeners.PlayerInteractListener;
import io.github.hsyyid.essentialcmds.listeners.PlayerJoinListener;
import io.github.hsyyid.essentialcmds.listeners.PlayerMoveListener;
import io.github.hsyyid.essentialcmds.listeners.SignChangeListener;
import io.github.hsyyid.essentialcmds.listeners.TPAListener;
import io.github.hsyyid.essentialcmds.listeners.WeatherChangeListener;
import io.github.hsyyid.essentialcmds.managers.config.Config;
import io.github.hsyyid.essentialcmds.utils.AFK;
import io.github.hsyyid.essentialcmds.utils.Message;
import io.github.hsyyid.essentialcmds.utils.PendingInvitation;
import io.github.hsyyid.essentialcmds.utils.Powertool;
import io.github.hsyyid.essentialcmds.utils.Utils;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.world.TeleportHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Plugin(id = ID, name = NAME, version = VERSION)
public class EssentialCmds 
{
	protected EssentialCmds() {}
	private static EssentialCmds essentialCmds;

	public static TeleportHelper helper;
	public static List<PendingInvitation> pendingInvites = Lists.newArrayList();
	public static List<AFK> afkList = Lists.newArrayList();
	public static List<Player> recentlyJoined = Lists.newArrayList();
	public static List<Powertool> powertools = Lists.newArrayList();
	public static Set<UUID> socialSpies = Sets.newHashSet();
	public static List<Message> recentlyMessaged = Lists.newArrayList();
	public static Set<UUID> muteList = Sets.newHashSet();
	public static Set<UUID> frozenPlayers = Sets.newHashSet();
	public static Set<UUID> lockedWeatherWorlds = Sets.newHashSet();

	@Inject
	private Logger logger;

	@Inject
	@ConfigDir(sharedRoot = false)
	private Path configDir;

	@Inject
	@DefaultConfig(sharedRoot = false)
	private Path dConfig;

	@Inject
	@DefaultConfig(sharedRoot = false)
	private ConfigurationLoader<CommentedConfigurationNode> confManager;

	public static EssentialCmds getEssentialCmds() {
		return essentialCmds;
	}

	@Listener
	public void onPreInitialization(GamePreInitializationEvent event) {
		essentialCmds = this;
		getLogger().info(ID + " loading...");

		// Create Config Directory for EssentialCmds
		if (!Files.exists(configDir)) {
			try {
				Files.createDirectories(configDir);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Create config.conf
		Config.getConfig().setup();
	}

	@Listener
	public void onServerInit(GameInitializationEvent event) {
		helper = getGame().getTeleportHelper();

		Utils.readMutes();
		Utils.startAFKService();

		CommandSpec homeCommandSpec =
			CommandSpec.builder().description(Texts.of("Home Command")).permission("essentialcmds.home.use")
			.arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("home name")))).executor(new HomeExecutor()).build();
		getGame().getCommandManager().register(this, homeCommandSpec, "home");

		CommandSpec mobSpawnerCommandSpec =
			CommandSpec.builder().description(Texts.of("Mob Spawner Command")).permission("essentialcmds.mobspawner.use")
			.arguments(GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Texts.of("mob name")))).executor(new MobSpawnerExecutor()).build();
		getGame().getCommandManager().register(this, mobSpawnerCommandSpec, "spawner", "mobspawner");

		CommandSpec removeRuleCommandSpec =
			CommandSpec.builder().description(Texts.of("Home Command")).permission("essentialcmds.rules.remove")
			.arguments(GenericArguments.onlyOne(GenericArguments.integer(Texts.of("rule number")))).executor(new RemoveRuleExecutor()).build();
		getGame().getCommandManager().register(this, removeRuleCommandSpec, "removerule", "delrule", "deleterule");

		CommandSpec addRuleCommandSpec =
			CommandSpec.builder().description(Texts.of("Add Rule Command")).permission("essentialcmds.rules.add")
			.arguments(GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Texts.of("rule")))).executor(new AddRuleExecutor()).build();
		getGame().getCommandManager().register(this, addRuleCommandSpec, "addrule");

		CommandSpec deleteWorldCommandSpec =
			CommandSpec.builder().description(Texts.of("Delete World Command")).permission("essentialcmds.world.delete")
			.arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("name")))).executor(new DeleteWorldExecutor()).build();
		getGame().getCommandManager().register(this, deleteWorldCommandSpec, "delworld", "deleteworld");

		CommandSpec moreCommandSpec =
			CommandSpec.builder().description(Texts.of("More Command")).permission("essentialcmds.more.use")
			.executor(new MoreExecutor()).build();
		getGame().getCommandManager().register(this, moreCommandSpec, "more", "stack");

		CommandSpec thruCommandSpec =
			CommandSpec.builder().description(Texts.of("Thru Command")).permission("essentialcmds.thru.use")
			.executor(new ThruExecutor()).build();
		getGame().getCommandManager().register(this, thruCommandSpec, "through", "thru");

		CommandSpec directionCommandSpec =
			CommandSpec.builder().description(Texts.of("Direction Command")).permission("essentialcmds.direction.use")
			.executor(new DirectionExecutor()).build();
		getGame().getCommandManager().register(this, directionCommandSpec, "direction", "compass");

		CommandSpec itemInfoCommandSpec =
			CommandSpec.builder().description(Texts.of("ItemInfo Command")).permission("essentialcmds.iteminfo.use")
			.executor(new ItemInfoExecutor()).build();
		getGame().getCommandManager().register(this, itemInfoCommandSpec, "iteminfo");

		CommandSpec blockInfoCommandSpec =
			CommandSpec.builder().description(Texts.of("BlockInfo Command")).permission("essentialcmds.blockinfo.use")
			.executor(new BlockInfoExecutor()).build();
		getGame().getCommandManager().register(this, blockInfoCommandSpec, "blockinfo");

		CommandSpec entityInfoCommandSpec =
			CommandSpec.builder().description(Texts.of("EntityInfo Command")).permission("essentialcmds.entityinfo.use")
			.executor(new EntityInfoExecutor()).build();
		getGame().getCommandManager().register(this, entityInfoCommandSpec, "entityinfo");

		CommandSpec rtpCommandSpec =
			CommandSpec.builder().description(Texts.of("RTP Command")).permission("essentialcmds.rtp.use")
			.executor(new RTPExecutor()).build();
		getGame().getCommandManager().register(this, rtpCommandSpec, "rtp", "randomtp");

		CommandSpec butcherCommandSpec =
			CommandSpec.builder().description(Texts.of("Butcher Command")).permission("essentialcmds.butcher.use")
			.executor(new ButcherExecutor()).build();
		getGame().getCommandManager().register(this, butcherCommandSpec, "butcher");

		CommandSpec rulesCommandSpec =
			CommandSpec.builder().description(Texts.of("Rules Command")).permission("essentialcmds.rules.use")
			.executor(new RuleExecutor()).build();
		getGame().getCommandManager().register(this, rulesCommandSpec, "rules");

		CommandSpec vanishCommandSpec =
			CommandSpec.builder().description(Texts.of("Vanish Command")).permission("essentialcmds.vanish.use")
			.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"))))).executor(new VanishExecutor()).build();
		getGame().getCommandManager().register(this, vanishCommandSpec, "vanish");

		CommandSpec igniteCommandSpec =
			CommandSpec.builder().description(Texts.of("Ignite Command")).permission("essentialcmds.ignite.use")
			.arguments(GenericArguments.seq(
				GenericArguments.onlyOne(GenericArguments.integer(Texts.of("ticks"))),
				GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"))))))
			.executor(new IgniteExecutor()).build();
		getGame().getCommandManager().register(this, igniteCommandSpec, "burn", "ignite", "fire");

		CommandSpec whoIsCommandSpec =
			CommandSpec.builder().description(Texts.of("WhoIs Command")).permission("essentialcmds.whois.use")
			.arguments(
				GenericArguments.firstParsing(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"))),
					GenericArguments.onlyOne(GenericArguments.string(Texts.of("player name")))))
			.executor(new WhoisExecutor()).build();
		getGame().getCommandManager().register(this, whoIsCommandSpec, "whois", "realname", "seen");

		CommandSpec playerFreezeCommandSpec =
			CommandSpec.builder().description(Texts.of("Player Freeze Command")).permission("essentialcmds.playerfreeze.use")
			.arguments(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player")))).executor(new PlayerFreezeExecutor()).build();
		getGame().getCommandManager().register(this, playerFreezeCommandSpec, "playerfreeze", "freezeplayer");

		CommandSpec skullCommandSpec =
			CommandSpec.builder().description(Texts.of("Skull Command")).permission("essentialcmds.skull.use")
			.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"))))).executor(new SkullExecutor()).build();
		getGame().getCommandManager().register(this, skullCommandSpec, "skull", "playerskull", "head");

		CommandSpec getPosCommandSpec =
			CommandSpec.builder().description(Texts.of("GetPos Command")).permission("essentialcmds.getpos.use")
			.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player")))))
			.executor(new GetPosExecutor()).build();
		getGame().getCommandManager().register(this, getPosCommandSpec, "getpos");
		
		CommandSpec lockWeatherCommandSpec =
			CommandSpec.builder()
				.description(Texts.of("LockWeather Command"))
				.permission("essentialcmds.lockweather.use")
				.arguments(GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Texts.of("name"))))
				.executor(new LockWeatherExecutor()).build();
		getGame().getCommandManager().register(this, lockWeatherCommandSpec, "lockweather", "killweather");

		CommandSpec gamemodeCommandSpec =
			CommandSpec
			.builder()
			.description(Texts.of("Gamemode Command"))
			.permission("essentialcmds.gamemode.use")
			.arguments(
				GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.string(Texts.of("gamemode"))),
					GenericArguments.onlyOne(GenericArguments.optional(GenericArguments.player(Texts.of("player"))))))
			.executor(new GamemodeExecutor()).build();
		getGame().getCommandManager().register(this, gamemodeCommandSpec, "gamemode", "gm");

		CommandSpec motdCommandSpec =
			CommandSpec.builder().description(Texts.of("MOTD Command")).permission("essentialcmds.motd.use").executor(new MotdExecutor()).build();
		getGame().getCommandManager().register(this, motdCommandSpec, "motd");

		CommandSpec socialSpyCommandSpec =
			CommandSpec.builder().description(Texts.of("Allows Toggling of Seeing Other Players Private Messages")).permission("essentialcmds.socialspy.use")
			.executor(new SocialSpyExecutor()).build();
		getGame().getCommandManager().register(this, socialSpyCommandSpec, "socialspy");

		CommandSpec mailListCommandSpec =
			CommandSpec.builder().description(Texts.of("List Mail Command")).permission("essentialcmds.mail.list")
			.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.integer(Texts.of("page no")))))
			.executor(new MailListExecutor()).build();
		getGame().getCommandManager().register(this, mailListCommandSpec, "listmail");

		CommandSpec mailReadCommandSpec =
			CommandSpec.builder().description(Texts.of("Read Mail Command")).permission("essentialcmds.mail.read")
			.arguments(GenericArguments.onlyOne(GenericArguments.integer(Texts.of("mail no")))).executor(new MailReadExecutor()).build();
		getGame().getCommandManager().register(this, mailReadCommandSpec, "readmail");

		CommandSpec msgRespondCommandSpec =
			CommandSpec.builder().description(Texts.of("Respond to Message Command")).permission("essentialcmds.message.respond")
			.arguments(GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Texts.of("message"))))
			.executor(new RespondExecutor()).build();
		getGame().getCommandManager().register(this, msgRespondCommandSpec, "r");

		CommandSpec timeCommandSpec =
			CommandSpec
			.builder()
			.description(Texts.of("Set Time Command"))
			.permission("essentialcmds.time.set")
			.arguments(
				GenericArguments.firstParsing(
					GenericArguments.integer(Texts.of("ticks")), 
					GenericArguments.string(Texts.of("time"))))
			.executor(new TimeExecutor()).build();
		getGame().getCommandManager().register(this, timeCommandSpec, "time");

		CommandSpec repairCommandSpec =
			CommandSpec.builder().description(Texts.of("Repair Item in Player's Hand")).permission("essentialcmds.repair.use").executor(new RepairExecutor())
			.build();
		getGame().getCommandManager().register(this, repairCommandSpec, "repair");

		CommandSpec mailCommandSpec =
			CommandSpec
			.builder()
			.description(Texts.of("Mail Command"))
			.permission("essentialcmds.mail.use")
			.arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.string(Texts.of("player")))),
				GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Texts.of("message")))).executor(new MailExecutor())
			.build();
		getGame().getCommandManager().register(this, mailCommandSpec, "mail");

		CommandSpec weatherCommandSpec =
			CommandSpec
			.builder()
			.description(Texts.of("Weather Command"))
			.permission("essentialcmds.weather.use")
			.arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.string(Texts.of("weather")))),
				GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.integer(Texts.of("duration")))))
			.executor(new WeatherExecutor()).build();
		getGame().getCommandManager().register(this, weatherCommandSpec, "weather");

		CommandSpec mobSpawnCommandSpec =
			CommandSpec
			.builder()
			.description(Texts.of("Mob Spawn Command"))
			.permission("essentialcmds.mobspawn.use")
			.arguments(GenericArguments.seq(
				GenericArguments.onlyOne(GenericArguments.integer(Texts.of("amount")))),
				GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Texts.of("mob name"))))
			.executor(new MobSpawnExecutor()).build();
		getGame().getCommandManager().register(this, mobSpawnCommandSpec, "mobspawn", "entityspawn");

		CommandSpec enchantCommandSpec =
			CommandSpec
			.builder()
			.description(Texts.of("Enchant Command"))
			.permission("essentialcmds.enchant.use")
			.arguments(GenericArguments.seq(
				GenericArguments.onlyOne(GenericArguments.integer(Texts.of("level")))),
				GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Texts.of("enchantment"))))
			.executor(new EnchantExecutor()).build();
		getGame().getCommandManager().register(this, enchantCommandSpec, "enchant", "ench");

		CommandSpec banCommandSpec =
			CommandSpec
			.builder()
			.description(Texts.of("Ban Command"))
			.permission("essentialcmds.ban.use")
			.arguments(
				GenericArguments.seq(GenericArguments.onlyOne(new UserParser(Texts.of("player"))), GenericArguments
					.optional(GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Texts.of("reason"))))))
			.executor(new BanExecutor()).build();
		getGame().getCommandManager().register(this, banCommandSpec, "ban");

		CommandSpec pardonCommandSpec =
			CommandSpec.builder().description(Texts.of("Unban Command")).permission("essentialcmds.unban.use")
			.arguments(GenericArguments.onlyOne(new UserParser(Texts.of("player")))).executor(new PardonExecutor()).build();
		getGame().getCommandManager().register(this, pardonCommandSpec, "unban", "pardon");

		CommandSpec teleportPosCommandSpec =
			CommandSpec
			.builder()
			.description(Texts.of("Teleport Position Command"))
			.permission("essentialcmds.teleport.pos.use")
			.arguments(
				GenericArguments.seq(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"))))), 
				GenericArguments.onlyOne(GenericArguments.integer(Texts.of("x"))),
				GenericArguments.onlyOne(GenericArguments.integer(Texts.of("y"))),
				GenericArguments.onlyOne(GenericArguments.integer(Texts.of("z")))).executor(new TeleportPosExecutor()).build();
		getGame().getCommandManager().register(this, teleportPosCommandSpec, "tppos", "teleportpos", "teleportposition");

		CommandSpec teleportCommandSpec =
			CommandSpec
			.builder()
			.description(Texts.of("Teleport Command"))
			.permission("essentialcmds.teleport.use")
			.arguments(GenericArguments.seq(
				GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"))),
				GenericArguments.optional(GenericArguments.player(Texts.of("target")))))
			.executor(new TeleportExecutor()).build();
		getGame().getCommandManager().register(this, teleportCommandSpec, "tp", "teleport");

		CommandSpec kickCommandSpec =
			CommandSpec
			.builder()
			.description(Texts.of("Kick Command"))
			.permission("essentialcmds.kick.use")
			.arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player")))),
				GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Texts.of("reason")))).executor(new KickExecutor())
			.build();
		getGame().getCommandManager().register(this, kickCommandSpec, "kick");

		CommandSpec messageCommandSpec =
			CommandSpec
			.builder()
			.description(Texts.of("Message Command"))
			.permission("essentialcmds.message.use")
			.arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.player(Texts.of("recipient")))),
				GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Texts.of("message"))))
			.executor(new MessageExecutor()).build();
		getGame().getCommandManager().register(this, messageCommandSpec, "message", "m", "msg", "tell");

		CommandSpec lightningCommandSpec =
			CommandSpec.builder().description(Texts.of("Lightning Command")).permission("essentialcmds.lightning.use")
			.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player")))))
			.executor(new LightningExecutor()).build();
		getGame().getCommandManager().register(this, lightningCommandSpec, "thor", "smite", "lightning");

		CommandSpec fireballCommandSpec =
			CommandSpec.builder().description(Texts.of("Fireball Command")).permission("essentialcmds.fireball.use")
			.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player")))))
			.executor(new FireballExecutor()).build();
		getGame().getCommandManager().register(this, fireballCommandSpec, "fireball", "ghast");

		CommandSpec sudoCommandSpec =
			CommandSpec
			.builder()
			.description(Texts.of("Sudo Command"))
			.permission("essentialcmds.sudo.use")
			.arguments(
				GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"))),
					GenericArguments.remainingJoinedStrings(Texts.of("command")))).executor(new SudoExecutor()).build();
		getGame().getCommandManager().register(this, sudoCommandSpec, "sudo");

		CommandSpec createWorldCommandSpec =
			CommandSpec
			.builder()
			.description(Texts.of("Create World Command"))
			.permission("essentialcmds.world.create")
			.arguments(
				GenericArguments.seq(
					GenericArguments.onlyOne(GenericArguments.string(Texts.of("name"))),
					GenericArguments.onlyOne(GenericArguments.string(Texts.of("environment"))),
					GenericArguments.onlyOne(GenericArguments.string(Texts.of("gamemode"))),
					GenericArguments.onlyOne(GenericArguments.string(Texts.of("difficulty")))))
			.executor(new CreateWorldExecutor()).build();
		getGame().getCommandManager().register(this, createWorldCommandSpec, "createworld");

		CommandSpec loadWorldCommandSpec =
			CommandSpec
			.builder()
			.description(Texts.of("Load World Command"))
			.permission("essentialcmds.world.load")
			.arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("name"))))
			.executor(new LoadWorldExecutor()).build();
		getGame().getCommandManager().register(this, loadWorldCommandSpec, "loadworld", "importworld");

		CommandSpec afkCommandSpec =
			CommandSpec.builder().description(Texts.of("AFK Command")).permission("essentialcmds.afk.use").executor(new AFKExecutor()).build();
		getGame().getCommandManager().register(this, afkCommandSpec, "afk");

		CommandSpec broadcastCommandSpec =
			CommandSpec.builder().description(Texts.of("Broadcast Command")).permission("essentialcmds.broadcast.use")
			.arguments(GenericArguments.remainingJoinedStrings(Texts.of("message"))).executor(new BroadcastExecutor()).build();
		getGame().getCommandManager().register(this, broadcastCommandSpec, "broadcast");

		CommandSpec spawnCommandSpec =
			CommandSpec.builder().description(Texts.of("Spawn Command")).permission("essentialcmds.spawn.use").executor(new SpawnExecutor()).build();
		getGame().getCommandManager().register(this, spawnCommandSpec, "spawn");

		CommandSpec setSpawnCommandSpec =
			CommandSpec.builder().description(Texts.of("Set Spawn Command")).permission("essentialcmds.spawn.set").executor(new SetSpawnExecutor()).build();
		getGame().getCommandManager().register(this, setSpawnCommandSpec, "setspawn");

		CommandSpec tpaCommandSpec =
			CommandSpec.builder().description(Texts.of("TPA Command")).permission("essentialcmds.tpa.use")
			.arguments(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player")))).executor(new TPAExecutor()).build();
		getGame().getCommandManager().register(this, tpaCommandSpec, "tpa");

		CommandSpec tpaHereCommandSpec =
			CommandSpec.builder().description(Texts.of("TPA Here Command")).permission("essentialcmds.tpahere.use")
			.arguments(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player")))).executor(new TPAHereExecutor())
			.build();
		getGame().getCommandManager().register(this, tpaHereCommandSpec, "tpahere");

		CommandSpec tpWorldSpec =
			CommandSpec.builder().description(Texts.of("TP World Command")).permission("essentialcmds.tpworld.use")
			.arguments(GenericArguments.seq(GenericArguments.string(Texts.of("name")),
				GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"))))))
			.executor(new TeleportWorldExecutor())
			.build();
		getGame().getCommandManager().register(this, tpWorldSpec, "tpworld");

		CommandSpec tpHereCommandSpec =
			CommandSpec.builder().description(Texts.of("TP Here Command")).permission("essentialcmds.tphere.use")
			.arguments(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player")))).executor(new TPHereExecutor())
			.build();
		getGame().getCommandManager().register(this, tpHereCommandSpec, "tphere");

		CommandSpec tpaAcceptCommandSpec =
			CommandSpec.builder().description(Texts.of("TPA Accept Command")).permission("essentialcmds.tpa.accept").executor(new TPAAcceptExecutor()).build();
		getGame().getCommandManager().register(this, tpaAcceptCommandSpec, "tpaccept");

		CommandSpec listHomeCommandSpec =
			CommandSpec.builder().description(Texts.of("List Home Command")).permission("essentialcmds.home.list")
			.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.integer(Texts.of("page no")))))
			.executor(new ListHomeExecutor()).build();
		getGame().getCommandManager().register(this, listHomeCommandSpec, "homes");

		CommandSpec listWorldsCommandSpec =
			CommandSpec.builder().description(Texts.of("List World Command")).permission("essentialcmds.worlds.list")
			.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.integer(Texts.of("page no")))))
			.executor(new ListWorldExecutor()).build();
		getGame().getCommandManager().register(this, listWorldsCommandSpec, "worlds");

		CommandSpec healCommandSpec =
			CommandSpec.builder().description(Texts.of("Heal Command")).permission("essentialcmds.heal.use")
			.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player")))))
			.executor(new HealExecutor()).build();
		getGame().getCommandManager().register(this, healCommandSpec, "heal");

		CommandSpec backCommandSpec =
			CommandSpec.builder().description(Texts.of("Back Command")).permission("essentialcmds.back.use").executor(new BackExecutor()).build();
		getGame().getCommandManager().register(this, backCommandSpec, "back");

		CommandSpec tpaDenyCommandSpec =
			CommandSpec.builder().description(Texts.of("TPA Deny Command")).permission("essentialcmds.tpadeny.use").executor(new TPADenyExecutor()).build();
		getGame().getCommandManager().register(this, tpaDenyCommandSpec, "tpadeny");

		CommandSpec hatCommandSpec =
			CommandSpec.builder().description(Texts.of("Hat Command")).permission("essentialcmds.hat.use").executor(new HatExecutor()).build();
		getGame().getCommandManager().register(this, hatCommandSpec, "hat");

		CommandSpec flyCommandSpec =
			CommandSpec.builder().description(Texts.of("Fly Command")).permission("essentialcmds.fly.use")
			.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("palyer")))))
			.executor(new FlyExecutor()).build();
		getGame().getCommandManager().register(this, flyCommandSpec, "fly");

		CommandSpec setHomeCommandSpec =
			CommandSpec.builder().description(Texts.of("Set Home Command")).permission("essentialcmds.home.set")
			.arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("home name")))).executor(new SetHomeExecutor()).build();
		getGame().getCommandManager().register(this, setHomeCommandSpec, "sethome");

		CommandSpec deleteHomeCommandSpec =
			CommandSpec.builder().description(Texts.of("Delete Home Command")).permission("essentialcmds.home.delete")
			.arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("home name")))).executor(new DeleteHomeExecutor())
			.build();
		getGame().getCommandManager().register(this, deleteHomeCommandSpec, "deletehome", "delhome");

		CommandSpec warpCommandSpec =
			CommandSpec.builder().description(Texts.of("Warp Command")).permission("essentialcmds.warp.use")
			.arguments(GenericArguments.seq(
				GenericArguments.onlyOne(GenericArguments.string(Texts.of("warp name"))),
					GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"))))))
			.executor(new WarpExecutor()).build();
		getGame().getCommandManager().register(this, warpCommandSpec, "warp");

		CommandSpec listWarpCommandSpec =
			CommandSpec.builder().description(Texts.of("List Warps Command")).permission("essentialcmds.warps.list")
			.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.integer(Texts.of("page no")))))
			.executor(new ListWarpExecutor()).build();
		getGame().getCommandManager().register(this, listWarpCommandSpec, "warps");

		CommandSpec setWarpCommandSpec =
			CommandSpec.builder().description(Texts.of("Set Warp Command")).permission("essentialcmds.warp.set")
			.arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("warp name")))).executor(new SetWarpExecutor()).build();
		getGame().getCommandManager().register(this, setWarpCommandSpec, "setwarp");

		CommandSpec deleteWarpCommandSpec =
			CommandSpec.builder().description(Texts.of("Delete Warp Command")).permission("essentialcmds.warp.delete")
			.arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("warp name")))).executor(new DeleteWarpExecutor())
			.build();
		getGame().getCommandManager().register(this, deleteWarpCommandSpec, "deletewarp", "delwarp");

		CommandSpec feedCommandSpec =
			CommandSpec.builder().description(Texts.of("Feed Command")).permission("essentialcmds.feed.use")
			.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player")))))
			.executor(new FeedExecutor()).build();
		getGame().getCommandManager().register(this, feedCommandSpec, "feed");

		CommandSpec unmuteCommnadSpec =
			CommandSpec.builder().description(Texts.of("Unmute Command")).permission("essentialcmds.unmute.use")
			.arguments(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player")))).executor(new UnmuteExecutor())
			.build();
		getGame().getCommandManager().register(this, unmuteCommnadSpec, "unmute");

		CommandSpec killCommandSpec =
			CommandSpec.builder().description(Texts.of("Kill Command")).permission("essentialcmds.kill.use")
			.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player")))))
			.executor(new KillExecutor()).build();
		getGame().getCommandManager().register(this, killCommandSpec, "kill");

		CommandSpec jumpCommandSpec =
			CommandSpec.builder().description(Texts.of("Jump Command")).permission("essentialcmds.jump.use").executor(new JumpExecutor()).build();
		getGame().getCommandManager().register(this, jumpCommandSpec, "jump");

		CommandSpec speedCommandSpec =
			CommandSpec.builder().description(Texts.of("Speed Command")).permission("essentialcmds.speed.use")
			.arguments(GenericArguments.seq(
				GenericArguments.onlyOne(GenericArguments.integer(Texts.of("speed"))),
				GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"))))))
			.executor(new SpeedExecutor()).build();
		getGame().getCommandManager().register(this, speedCommandSpec, "speed");

		CommandSpec powertoolCommandSpec =
			CommandSpec.builder().description(Texts.of("Powertool Command")).permission("essentialcmds.powertool.use")
			.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Texts.of("command")))))
			.executor(new PowertoolExecutor()).build();
		getGame().getCommandManager().register(this, powertoolCommandSpec, "powertool");

		CommandSpec asConsoleCommandSpec =
			CommandSpec.builder().description(Texts.of("AsConsole Command")).permission("essentialcmds.asconsole.use")
			.arguments(GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Texts.of("command"))))
			.executor(new AsConsoleExecutor()).build();
		getGame().getCommandManager().register(this, asConsoleCommandSpec, "asConsole", "asconsole");

		CommandSpec nickCommandSpec =
			CommandSpec
			.builder()
			.description(Texts.of("Nick Command"))
			.permission("essentialcmds.nick.use")
			.arguments(
				GenericArguments.seq(
					GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player")))),
					GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Texts.of("nick")))))
			.executor(new NickExecutor()).build();
		getGame().getCommandManager().register(this, nickCommandSpec, "nick");

		CommandSpec muteCommandSpec =
			CommandSpec
			.builder()
			.description(Texts.of("Mute Command"))
			.permission("essentialcmds.mute.use")
			.arguments(
				GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"))),
					GenericArguments.onlyOne(GenericArguments.optional(GenericArguments.integer(Texts.of("time")))),
					GenericArguments.onlyOne(GenericArguments.optional(GenericArguments.string(Texts.of("time unit"))))))
			.executor(new MuteExecutor()).build();
		getGame().getCommandManager().register(this, muteCommandSpec, "mute");

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

		getLogger().info("-----------------------------");
		getLogger().info("EssentialCmds was made by HassanS6000!");
		getLogger().info("Please post all errors on the Sponge Thread or on GitHub!");
		getLogger().info("Have fun, and enjoy! :D");
		getLogger().info("-----------------------------");
		getLogger().info("EssentialCmds loaded!");
	}

	public Path getConfigDir() {
		return configDir;
	}

	public Logger getLogger() {
		return logger;
	}

	public Game getGame() {
		return Sponge.getGame();
	}

}
