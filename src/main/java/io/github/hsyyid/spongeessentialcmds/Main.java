package io.github.hsyyid.spongeessentialcmds;

import com.google.common.base.Optional;
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
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.PowertoolExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.RepairExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.RespondExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.SetHomeExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.SetSpawnExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.SetWarpExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.SocialSpyExecutor;
import io.github.hsyyid.spongeessentialcmds.cmdexecutors.SpawnExecutor;
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
import io.github.hsyyid.spongeessentialcmds.events.MailSendEvent;
import io.github.hsyyid.spongeessentialcmds.events.TPAAcceptEvent;
import io.github.hsyyid.spongeessentialcmds.events.TPAEvent;
import io.github.hsyyid.spongeessentialcmds.events.TPAHereAcceptEvent;
import io.github.hsyyid.spongeessentialcmds.events.TPAHereEvent;
import io.github.hsyyid.spongeessentialcmds.utils.AFK;
import io.github.hsyyid.spongeessentialcmds.utils.Mail;
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
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.block.tileentity.TileEntityTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
import org.spongepowered.api.data.manipulator.mutable.entity.FoodData;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.block.tileentity.ChangeSignEvent;
import org.spongepowered.api.event.command.MessageSinkEvent;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.entity.DisplaceEntityEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.command.CommandService;
import org.spongepowered.api.service.config.DefaultConfig;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.option.OptionSubject;
import org.spongepowered.api.service.scheduler.SchedulerService;
import org.spongepowered.api.service.scheduler.TaskBuilder;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.TextMessageException;
import org.spongepowered.api.util.command.args.GenericArguments;
import org.spongepowered.api.util.command.spec.CommandSpec;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.TeleportHelper;
import org.spongepowered.api.world.World;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Plugin(id = "SpongeEssentialCmds", name = "SpongeEssentialCmds", version = "2.8")
public class Main
{
    public static Game game = null;
    public static ConfigurationNode config = null;
    public static ConfigurationLoader<CommentedConfigurationNode> configurationManager;
    public static TeleportHelper helper;
    public static ArrayList<PendingInvitation> pendingInvites = new ArrayList<PendingInvitation>();
    public static ArrayList<AFK> movementList = new ArrayList<AFK>();
    public static ArrayList<Player> recentlyJoined = new ArrayList<Player>();
    public static ArrayList<Powertool> powertools = new ArrayList<Powertool>();
    public static ArrayList<UUID> socialSpies = new ArrayList<UUID>();
    public static ArrayList<Message> recentlyMessaged = new ArrayList<Message>();
    public static ArrayList<Mute> muteList = new ArrayList<Mute>();

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
    public void onServerStart(GameInitializationEvent event)
    {
        getLogger().info("SpongeEssentialCmds loading...");
        game = event.getGame();
        helper = game.getTeleportHelper();
        
        Utils.readMutes();
        
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

        SchedulerService scheduler = game.getScheduler();
        TaskBuilder taskBuilder = scheduler.createTaskBuilder();

        taskBuilder.execute(new Runnable()
        {
            public void run()
            {
                for (Player player : game.getServer().getOnlinePlayers())
                {
                    for (AFK afk : movementList)
                    {
                        if (afk.getPlayer() == player && ((System.currentTimeMillis() - afk.lastMovementTime) > (Utils.getAFK())) && afk.getMessaged() == false)
                        {
                            for (Player p : game.getServer().getOnlinePlayers())
                            {
                                p.sendMessage(Texts.of(TextColors.BLUE, player.getName(), TextColors.GOLD, " is now AFK."));
                                Optional<FoodData> data = p.get(FoodData.class);
                                if(data.isPresent())
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
                            if(data.isPresent())
                            {
                                FoodData food = data.get();
                                if (food.foodLevel().get() < afk.getFood())
                                {
                                    Value<Integer> foodLevel = food.foodLevel().set(afk.getFood());
                                    food.set(foodLevel);
                                    p.offer(food);
                                }
                            }

                            if(!(p.hasPermission("afk.kick.false")) && Utils.getAFKKick() && afk.getLastMovementTime() >= Utils.getAFKKickTimer())
                            {
                                p.kick(Texts.of(TextColors.GOLD, "Kicked for being AFK too long."));
                            }
                        }
                    }
                }
            }
        }).interval(1, TimeUnit.SECONDS).name("SpongeEssentialCmds - AFK").submit(game.getPluginManager().getPlugin("SpongeEssentialCmds").get().getInstance());

        CommandSpec homeCommandSpec = CommandSpec.builder()
                .description(Texts.of("Home Command"))
                .permission("home.use")
                .arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("home name"))))
                .executor(new HomeExecutor())
                .build();

        game.getCommandDispatcher().register(this, homeCommandSpec, "home");

        CommandSpec gamemodeCommandSpec = CommandSpec.builder()
                .description(Texts.of("Gamemode Command"))
                .permission("gamemode.use")
                .arguments(GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.string(Texts.of("gamemode"))),
                        GenericArguments.onlyOne(GenericArguments.optional(GenericArguments.player(Texts.of("player"), game)))))
                        .executor(new GamemodeExecutor())
                        .build();

        game.getCommandDispatcher().register(this, gamemodeCommandSpec, "gamemode", "gm");

        CommandSpec motdCommandSpec = CommandSpec.builder()
                .description(Texts.of("MOTD Command"))
                .permission("motd.use")
                .executor(new MotdExecutor())
                .build();

        game.getCommandDispatcher().register(this, motdCommandSpec, "motd");

        CommandSpec socialSpyCommandSpec = CommandSpec.builder()
                .description(Texts.of("Allows Toggling of Seeing Other Players Private Messages"))
                .permission("socialspy.use")
                .executor(new SocialSpyExecutor())
                .build();

        game.getCommandDispatcher().register(this, socialSpyCommandSpec, "socialspy");

        CommandSpec mailListCommandSpec = CommandSpec.builder()
                .description(Texts.of("List Mail Command"))
                .permission("mail.list")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.integer(Texts.of("page no")))))
                .executor(new MailListExecutor())
                .build();

        game.getCommandDispatcher().register(this, mailListCommandSpec, "listmail");

        CommandSpec mailReadCommandSpec = CommandSpec.builder()
                .description(Texts.of("Read Mail Command"))
                .permission("mail.read")
                .arguments(GenericArguments.onlyOne(GenericArguments.integer(Texts.of("mail no"))))
                .executor(new MailReadExecutor())
                .build();

        game.getCommandDispatcher().register(this, mailReadCommandSpec, "readmail");

        CommandSpec msgRespondCommandSpec = CommandSpec.builder()
                .description(Texts.of("Respond to Message Command"))
                .permission("message.respond")
                .arguments(GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Texts.of("message"))))
                .executor(new RespondExecutor())
                .build();

        game.getCommandDispatcher().register(this, msgRespondCommandSpec, "r");

        CommandSpec timeCommandSpec = CommandSpec.builder()
                .description(Texts.of("Set Time Command"))
                .permission("time.set")
                .arguments(GenericArguments.firstParsing(
                        GenericArguments.string(Texts.of("time")),
                                GenericArguments.integer(Texts.of("ticks"))))
                .executor(new TimeExecutor())
                .build();

        game.getCommandDispatcher().register(this, timeCommandSpec, "time");
        
        CommandSpec repairCommandSpec = CommandSpec.builder()
                .description(Texts.of("Repair Item in Player's Hand"))
                .permission("repair.use")
                .executor(new RepairExecutor())
                .build();

        game.getCommandDispatcher().register(this, repairCommandSpec, "repair");

        CommandSpec mailCommandSpec = CommandSpec.builder()
                .description(Texts.of("Mail Command"))
                .permission("mail.use")
                .arguments(GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.string(Texts.of("player")))),
                        GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Texts.of("message"))))
                        .executor(new MailExecutor())
                        .build();

        game.getCommandDispatcher().register(this, mailCommandSpec, "mail");
        
        CommandSpec weatherCommandSpec = CommandSpec.builder()
                .description(Texts.of("Weather Command"))
                .permission("weather.use")
                .arguments(GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.string(Texts.of("weather")))),
                        GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Texts.of("duration")))))
                        .executor(new WeatherExecutor())
                        .build();

        game.getCommandDispatcher().register(this, weatherCommandSpec, "weather");

        CommandSpec banCommandSpec = CommandSpec.builder()
                .description(Texts.of("Ban Command"))
                .permission("ban.use")
                .arguments(GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game))),
                        GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Texts.of("reason"))))
                        .executor(new BanExecutor())
                        .build();

        game.getCommandDispatcher().register(this, banCommandSpec, "ban");
        
        CommandSpec teleportPosCommandSpec = CommandSpec.builder()
                .description(Texts.of("Teleport Position Command"))
                .permission("teleport.pos.use")
                .arguments(GenericArguments.seq(
                        GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game)))),
                        GenericArguments.onlyOne(GenericArguments.integer(Texts.of("x"))),
                        GenericArguments.onlyOne(GenericArguments.integer(Texts.of("y"))),
                        GenericArguments.onlyOne(GenericArguments.integer(Texts.of("z"))))
                        .executor(new TeleportPosExecutor())
                        .build();

        game.getCommandDispatcher().register(this, teleportPosCommandSpec, "tppos", "teleportpos", "teleportposition");

        CommandSpec kickCommandSpec = CommandSpec.builder()
                .description(Texts.of("Kick Command"))
                .permission("kick.use")
                .arguments(GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game))),
                        GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Texts.of("reason"))))
                        .executor(new KickExecutor())
                        .build();

        game.getCommandDispatcher().register(this, kickCommandSpec, "kick");

        CommandSpec messageCommandSpec = CommandSpec.builder()
                .description(Texts.of("Message Command"))
                .permission("message.use")
                .arguments(GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.player(Texts.of("recipient"), game))),
                        GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Texts.of("message"))))
                        .executor(new MessageExecutor())
                        .build();

        game.getCommandDispatcher().register(this, messageCommandSpec, "message", "m", "msg", "tell");

        CommandSpec lightningCommandSpec = CommandSpec.builder()
                .description(Texts.of("Lightning Command"))
                .permission("lightning.use")
                .executor(new LightningExecutor())
                .build();

        game.getCommandDispatcher().register(this, lightningCommandSpec, "thor", "smite", "lightning");

        CommandSpec sudoCommandSpec = CommandSpec.builder()
                .description(Texts.of("Sudo Command"))
                .permission("sudo.use")
                .arguments(GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game)),
                        GenericArguments.remainingJoinedStrings(Texts.of("command"))))
                        .executor(new SudoExecutor())
                        .build();

        game.getCommandDispatcher().register(this, sudoCommandSpec, "sudo");

        CommandSpec afkCommandSpec = CommandSpec.builder()
                .description(Texts.of("AFK Command"))
                .permission("afk.use")
                .executor(new AFKExecutor())
                .build();

        game.getCommandDispatcher().register(this, afkCommandSpec, "afk");

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
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game))))
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
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game))))
                .executor(new FeedExecutor())
                .build();

        game.getCommandDispatcher().register(this, feedCommandSpec, "feed");
        
        CommandSpec unmuteCommnadSpec = CommandSpec.builder()
                .description(Texts.of("Unmute Command"))
                .permission("unmute.use")
                .arguments(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game)))
                .executor(new UnmuteExecutor())
                .build();

        game.getCommandDispatcher().register(this, unmuteCommnadSpec, "unmute");
        
        CommandSpec killCommandSpec = CommandSpec.builder()
                .description(Texts.of("Kill Command"))
                .permission("kill.use")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game))))
                .executor(new KillExecutor())
                .build();

        game.getCommandDispatcher().register(this, killCommandSpec, "kill");

        CommandSpec jumpCommandSpec = CommandSpec.builder()
                .description(Texts.of("Jump Command"))
                .permission("jump.use")
                .executor(new JumpExecutor())
                .build();

        game.getCommandDispatcher().register(this, jumpCommandSpec, "jump");

        CommandSpec powertoolCommandSpec = CommandSpec.builder()
                .description(Texts.of("Powertool Command"))
                .permission("powertool.use")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Texts.of("command")))))
                .executor(new PowertoolExecutor())
                .build();

        game.getCommandDispatcher().register(this, powertoolCommandSpec, "powertool");

        CommandSpec nickCommandSpec = CommandSpec.builder()
                .description(Texts.of("Nick Command"))
                .permission("nick.use")
                .arguments(GenericArguments.seq(
                        GenericArguments.optional(
                                GenericArguments.onlyOne(
                                        GenericArguments.player(Texts.of("player"), game)
                                        )),
                                        GenericArguments.onlyOne(
                                                GenericArguments.remainingJoinedStrings(Texts.of("nick")))))
                                                .executor(new NickExecutor())
                                                .build();

        game.getCommandDispatcher().register(this, nickCommandSpec, "nick");

        CommandSpec muteCommandSpec = CommandSpec.builder()
                .description(Texts.of("Mute Command"))
                .permission("mute.use")
                .arguments(GenericArguments.seq(
                        GenericArguments.onlyOne(
                                GenericArguments.player(Texts.of("player"), game)
                                ),
                                GenericArguments.onlyOne(
                                        GenericArguments.optional(GenericArguments.integer(Texts.of("time")))),
                                        GenericArguments.onlyOne(
                                                GenericArguments.optional(
                                                        GenericArguments.string(Texts.of("time unit"))))))
                                                        .executor(new MuteExecutor())
                                                        .build();

        game.getCommandDispatcher().register(this, muteCommandSpec, "mute");


        getLogger().info("-----------------------------");
        getLogger().info("SpongeEssentialCmds was made by HassanS6000!");
        getLogger().info("Please post all errors on the Sponge Thread or on GitHub!");
        getLogger().info("Have fun, and enjoy! :D");
        getLogger().info("-----------------------------");
        getLogger().info("SpongeEssentialCmds loaded!");
    }

    @Listener
    public void onMailSend(MailSendEvent event)
    {
        String recipientName = event.getRecipientName();

        if(game.getServer().getPlayer(recipientName).isPresent())
        {
            Utils.addMail(event.getSender().getName(), recipientName, event.getMessage());
            game.getServer().getPlayer(recipientName).get().sendMessage(Texts.of(TextColors.GOLD, "[Mail]: ", TextColors.GRAY, "You have received new mail from " + event.getSender().getName() + " do ", TextColors.RED, "/listmail!"));
        }
        else
        {
            Utils.addMail(event.getSender().getName(), recipientName, event.getMessage());
        }
    }
    
    @Listener
    public void onServerStop(GameStoppingServerEvent event)
    {
        Utils.saveMutes();
    }

    @Listener
    public void onPlayerDeath(DestructEntityEvent event)
    {   
        if(event.getTargetEntity() instanceof Player)
        {
            Player died = (Player) event.getTargetEntity();
            Utils.setLastDeathLocation(died.getUniqueId(), died.getLocation());
        }
    }

    @SuppressWarnings("deprecation")
    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event)
    {
        Player player = event.getTargetEntity();

        String message = Utils.getJoinMsg().replaceAll("&", "\u00A7");
        player.sendMessage(Texts.of(message));

        ArrayList<Mail> newMail = new ArrayList<Mail>();

        for(Mail mail : Utils.getMail())
        {
            if(mail.getRecipientName().equals(player.getName().toString()))
            {
                newMail.add(mail);
            }
        }

        if(newMail.size() > 0)
        {
            player.sendMessage(Texts.of(TextColors.GOLD, "[Mail]: ", TextColors.GRAY, "While you were away, you received new mail to view it do ", TextColors.RED, "/listmail"));
        }

        recentlyJoined.add(event.getTargetEntity());

        AFK afkToRemove = null;

        for(AFK afk : movementList)
        {
            if(afk.getPlayer().equals(player))
            {
                afkToRemove = afk;
                break;
            }
        }

        if(afkToRemove != null)
        {
            movementList.remove(afkToRemove);
        }

        Subject subject = player.getContainingCollection().get(player.getIdentifier());

        if (subject instanceof OptionSubject)
        {
            OptionSubject optionSubject = (OptionSubject) subject;
            String prefix = optionSubject.getOption("prefix").or("");
            Text textPrefix = null;

            try
            {
                textPrefix = Texts.legacy('&').from(prefix + " ");
            }
            catch (TextMessageException e)
            {
                getLogger().warn("Error! A TextMessageException was caught when trying to format the prefix!");
            }

            DisplayNameData data = player.getOrCreate(DisplayNameData.class).get();
            Optional<Text> name = data.get(Keys.DISPLAY_NAME);

            if(name.isPresent())
            {
                data.set(Keys.DISPLAY_NAME, Texts.of(textPrefix, name.get()));
            }
            else
            {
                data.set(Keys.DISPLAY_NAME, Texts.of(textPrefix, player.getName()));
            }

            player.offer(data);
        }
        else
        {
            getLogger().info("Player is not an instance of OptionSubject!");
        }
    }

    @Listener
    public void tpaEventHandler(TPAEvent event)
    {
        String senderName = event.getSender().getName();
        event.getRecipient().sendMessage(Texts.of(TextColors.BLUE, "TPA Request From: ", TextColors.GOLD, senderName + ".", TextColors.RED, " You have 10 seconds to do /tpaccept to accept the request"));

        // Adds Invite to List
        final PendingInvitation invite = new PendingInvitation(event.getSender(), event.getRecipient());
        pendingInvites.add(invite);

        // Removes Invite after 10 Seconds
        SchedulerService scheduler = game.getScheduler();
        TaskBuilder taskBuilder = scheduler.createTaskBuilder();

        taskBuilder.execute(new Runnable()
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

    @Listener
    public void tpaAcceptEventHandler(TPAAcceptEvent event)
    {
        String senderName = event.getSender().getName();
        event.getRecipient().sendMessage(Texts.of(TextColors.GREEN, senderName, TextColors.WHITE, " accepted your TPA Request."));
        event.getRecipient().setLocation(event.getSender().getLocation());
    }

    @Listener
    public void tpaHereAcceptEventHandler(TPAHereAcceptEvent event)
    {
        String recipientName = event.getRecipient().getName();
        event.getSender().sendMessage(Texts.of(TextColors.GREEN, recipientName, TextColors.WHITE, " accepted your TPA Here Request."));
        event.getSender().setLocation(event.getRecipient().getLocation());
    }

    @Listener
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
        TaskBuilder taskBuilder = scheduler.createTaskBuilder();

        taskBuilder.execute(new Runnable()
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

    @SuppressWarnings("deprecation")
    @Listener
    public void onMessage(MessageSinkEvent event)
    {
        if(event.getCause().root().isPresent() && event.getCause().root().get() instanceof Player)
        {
            Player player = (Player) event.getCause().root().get();

            for(Mute mute : muteList)
            {
                if(mute.getUUID().equals(player.getUniqueId().toString()))
                {
                    player.sendMessage(Texts.of(TextColors.RED, "You have been muted."));
                    event.setCancelled(true);
                    return;
                }
            }

            String original = Texts.toPlain(event.getMessage());
            Subject subject = player.getContainingCollection().get(player.getIdentifier());

            if (subject instanceof OptionSubject)
            {
                OptionSubject optionSubject = (OptionSubject) subject;
                String prefix = optionSubject.getOption("prefix").or("");
                prefix = prefix.replaceAll("&", "\u00A7");
                original = original.replaceFirst("<", ("<" + prefix + " " + "\u00A7f"));
                if (!(player.hasPermission("color.chat.use")))
                {
                    event.setMessage(Texts.of(original));
                }
            }

            original = original.replaceFirst("<", Utils.getFirstChatCharReplacement());
            original = original.replace(">", Utils.getLastChatCharReplacement());

            if (!(player.hasPermission("color.chat.use")))
            {
                event.setMessage(Texts.of(original));
            }

            //			OptionSubject optionSubject = (OptionSubject) subject;
            //			String prefix = optionSubject.getOption("prefix").or("");
            //			
            //			if(!(original.contains(prefix)))
            //			{
            //				Text textPrefix = null;
            //				
            //				try
            //				{
            //					textPrefix = Texts.legacy('&').from(prefix + " ");
            //				}
            //				catch (TextMessageException e)
            //				{
            //					getLogger().warn("Error! A TextMessageException was caught when trying to format the prefix!");
            //				}
            //
            //				DisplayNameData data = player.getOrCreate(DisplayNameData.class).get();
            //				Optional<Text> name = data.get(Keys.DISPLAY_NAME);
            //				
            //				if(name.isPresent())
            //				{
            //					data.set(Keys.DISPLAY_NAME, Texts.of(textPrefix, name.get()));
            //				}
            //				else
            //				{
            //					data.set(Keys.DISPLAY_NAME, Texts.of(textPrefix, player.getName()));
            //				}
            //
            //				player.offer(data);
            //			}
            //		}

            if (player.hasPermission("color.chat.use"))
            {
                Text newMessage = null;
                try
                {
                    newMessage = Texts.legacy('&').from(original);
                }
                catch (TextMessageException e)
                {
                    ;
                }
                event.setMessage(newMessage);
            }
        }
    }

    @Listener
    public void onSignChange(ChangeSignEvent event)
    {
        if(event.getCause().first(Player.class).isPresent())
        {
            Player player = (Player) event.getCause().first(Player.class).get();
            SignData signData = event.getText();

            if(signData.getValue(Keys.SIGN_LINES).isPresent())
            {
                String line0 = Texts.toPlain(signData.getValue(Keys.SIGN_LINES).get().get(0));
                String line1 = Texts.toPlain(signData.getValue(Keys.SIGN_LINES).get().get(1));
                String line2 = Texts.toPlain(signData.getValue(Keys.SIGN_LINES).get().get(2));
                String line3 = Texts.toPlain(signData.getValue(Keys.SIGN_LINES).get().get(3));

                if (line0.equals("[Warp]"))
                {
                    if (Utils.getWarps().contains(line1))
                    {	
                        signData = signData.set(signData.getValue(Keys.SIGN_LINES).get().set(0, Texts.of(TextColors.DARK_BLUE, "[Warp]")));
                    }
                    else
                    {
                        signData = signData.set(signData.getValue(Keys.SIGN_LINES).get().set(0, Texts.of(TextColors.DARK_RED, "[Warp]")));
                    }
                }
                else if(player != null && player.hasPermission("color.sign.use"))
                {
                    signData = signData.set(signData.getValue(Keys.SIGN_LINES).get().set(0, Texts.of(line0.replaceAll("&", "\u00A7"))));
                    signData = signData.set(signData.getValue(Keys.SIGN_LINES).get().set(1, Texts.of(line1.replaceAll("&", "\u00A7"))));
                    signData = signData.set(signData.getValue(Keys.SIGN_LINES).get().set(2, Texts.of(line2.replaceAll("&", "\u00A7"))));
                    signData = signData.set(signData.getValue(Keys.SIGN_LINES).get().set(3, Texts.of(line3.replaceAll("&", "\u00A7"))));
                }
            }
        }
    }

    @Listener
    public void onPlayerAttack(InteractEntityEvent.Attack event)
    {
        if(event.getCause().first(Player.class).isPresent())
        {
            Player player = (Player) event.getCause().first(Player.class).get();
            Powertool foundTool = null;

            for(Powertool powertool : powertools)
            {
                if(powertool.getPlayer().equals(player))
                {
                    if(player.getItemInHand().isPresent() && powertool.getItemID().equals(player.getItemInHand().get().getItem().getName()))
                    {
                        foundTool = powertool;
                        break;
                    }
                }
            }

            if(foundTool != null)
            {
                game.getCommandDispatcher().process(player, foundTool.getCommand());
            }
        }
    }

    @Listener
    public void onPlayerUse(InteractEntityEvent.Primary event)
    {
        if(event.getCause().first(Player.class).isPresent())
        {
            Player player = (Player) event.getCause().first(Player.class).get();
            Powertool foundTool = null;

            for(Powertool powertool : powertools)
            {
                if(powertool.getPlayer().equals(player))
                {
                    if(player.getItemInHand().isPresent() && powertool.getItemID().equals(player.getItemInHand().get().getItem().getName()))
                    {
                        foundTool = powertool;
                        break;
                    }
                }
            }

            if(foundTool != null)
            {
                game.getCommandDispatcher().process(player, foundTool.getCommand());
            }
        }
    }

    @Listener
    public void onPlayerInteractBlock(InteractBlockEvent event)
    {
        if(event.getCause().first(Player.class).isPresent())
        {
            Player player = (Player) event.getCause().first(Player.class).get();
            Location<World> location = event.getTargetBlock().getLocation().get();

            if (location.getTileEntity().isPresent() && location.getTileEntity().get() != null && location.getTileEntity().get().getType() != null)
            {
                TileEntity clickedEntity = location.getTileEntity().get();
                if (clickedEntity.getType() == TileEntityTypes.SIGN)
                {
                    Optional<SignData> signData = clickedEntity.getOrCreate(SignData.class);
                    if(signData.isPresent())
                    {
                        SignData data = signData.get();
                        CommandService cmdService = game.getCommandDispatcher();
                        String line0 = Texts.toPlain(data.getValue(Keys.SIGN_LINES).get().get(0));
                        String line1 = Texts.toPlain(data.getValue(Keys.SIGN_LINES).get().get(1));
                        String command = "warp " + line1;

                        if (line0.equals("[Warp]"))
                        {
                            if (player.hasPermission("warps.use.sign"))
                            {
                                cmdService.process(player, command);
                            }
                            else
                            {
                                player.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You do not have permission to use Warp Signs!"));
                            }
                        }
                    }
                }
            }
        }
    }

    @Listener
    public void onPlayerMove(DisplaceEntityEvent event)
    {
        if(event.getTargetEntity() instanceof Player)
        {
            Player player = (Player) event.getTargetEntity();
            if (recentlyJoined.contains(player))
            {
                recentlyJoined.remove(player);
                AFK removeAFK = null;
                for (AFK a : movementList)
                {
                    if (a.getPlayer() == a.getPlayer())
                    {
                        removeAFK = a;
                        break;
                    }
                }
                if (removeAFK != null)
                {
                    movementList.remove(removeAFK);
                }
            }
            else
            {
                AFK afk = new AFK(player, System.currentTimeMillis());
                AFK removeAFK = null;
                for (AFK a : movementList)
                {
                    if (a.getPlayer() == a.getPlayer())
                    {
                        removeAFK = a;
                        break;
                    }
                }

                if (removeAFK != null)
                {
                    if (removeAFK.getAFK() == true)
                    {
                        for (Player p : game.getServer().getOnlinePlayers())
                        {
                            p.sendMessage(Texts.of(TextColors.BLUE, player.getName(), TextColors.GOLD, " is no longer AFK."));
                        }
                        movementList.remove(removeAFK);
                    }
                    else if (removeAFK.getAFK() == false)
                    {
                        movementList.remove(removeAFK);
                        movementList.add(afk);
                    }
                }
                else
                {
                    movementList.add(afk);
                }
            }
        }
    }

    public static ConfigurationLoader<CommentedConfigurationNode> getConfigManager()
    {
        return configurationManager;
    }
}
