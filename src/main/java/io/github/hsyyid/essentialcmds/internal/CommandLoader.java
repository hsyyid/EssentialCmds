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
package io.github.hsyyid.essentialcmds.internal;

import com.google.common.collect.Sets;
import io.github.hsyyid.essentialcmds.EssentialCmds;
import io.github.hsyyid.essentialcmds.cmdexecutors.AFKExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.AddRuleExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.AsConsoleExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.BackExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.BanExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.BlacklistBase;
import io.github.hsyyid.essentialcmds.cmdexecutors.BlockInfoExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.BroadcastExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.ButcherExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.ClearInventoryExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.DeleteHomeExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.DeleteWarpExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.DirectionExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.EnchantExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.EntityInfoExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.EssentialCmdsExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.ExpExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.FeedExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.FireballExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.FlyExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.GCExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.GamemodeExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.GetPosExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.GodExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.HatExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.HealExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.HelpOpExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.HomeExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.IgniteExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.InvSeeExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.ItemInfoExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.JailExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.JumpExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.KickAllExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.KickExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.KillExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.LightningExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.ListExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.ListHomeExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.ListWarpExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.LockWeatherExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.LoreBase;
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
import io.github.hsyyid.essentialcmds.cmdexecutors.RocketExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.RuleExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.SetHomeExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.SetNameExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.SetWarpExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.SkullExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.SlapExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.SocialSpyExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.SpeedExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.StopExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.SudoExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.TPAAcceptExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.TPAAllExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.TPADenyExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.TPAExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.TPAHereExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.TPAllExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.TPChunkExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.TPHereExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.TakeExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.TeleportExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.TeleportPosExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.TempBanExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.ThruExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.TimeExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.UnJailExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.UnmuteExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.VanishExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.WarpExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.WeatherExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.WhoisExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.world.CreateWorldExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.world.DeleteWorldExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.world.ListWorldExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.world.LoadWorldExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.world.SetSpawnExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.world.SetWorldSpawnExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.world.SpawnExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.world.TeleportWorldExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.world.WorldInfoExecutor;
import io.github.hsyyid.essentialcmds.cmdexecutors.world.WorldsBase;
import org.spongepowered.api.Sponge;

import java.util.Set;

/**
 * Utility class that handles loading and registering commands.
 */
public final class CommandLoader {

    /* (non-Javadoc)
     *
     * No need to instantiate!
     */
    private CommandLoader() {}

    private static Set<? extends CommandExecutorBase> getCommands() {
        return Sets.newHashSet(
            new AddRuleExecutor(),
            new AFKExecutor(),
            new AsConsoleExecutor(),
            new BackExecutor(),
            new BanExecutor(),
            new BlacklistBase(),
            new BlockInfoExecutor(),
            new BroadcastExecutor(),
            new ButcherExecutor(),
            new CreateWorldExecutor(),
            new DeleteHomeExecutor(),
            new DeleteWarpExecutor(),
            new DeleteWorldExecutor(),
            new DirectionExecutor(),
            new EnchantExecutor(),
            new EntityInfoExecutor(),
            new EssentialCmdsExecutor(),
            new ExpExecutor(),
            new FeedExecutor(),
            new FireballExecutor(),
            new FlyExecutor(),
            new GamemodeExecutor(),
            new GCExecutor(),
            new GetPosExecutor(),
            new HatExecutor(),
            new HomeExecutor(),
            new HealExecutor(),
            new IgniteExecutor(),
            new InvSeeExecutor(),
            new ItemInfoExecutor(),
            new JumpExecutor(),
            new KickExecutor(),
            new KillExecutor(),
            new LightningExecutor(),
            new ListHomeExecutor(),
            new ListWarpExecutor(),
            new ListWorldExecutor(),
            new LoadWorldExecutor(),
            new LockWeatherExecutor(),
            new MailExecutor(),
            new MailListExecutor(),
            new MailReadExecutor(),
            new MessageExecutor(),
            new MobSpawnerExecutor(),
            new MobSpawnExecutor(),
            new MoreExecutor(),
            new MotdExecutor(),
            new MuteExecutor(),
            new NickExecutor(),
            new PardonExecutor(),
            new PlayerFreezeExecutor(),
            new PowertoolExecutor(),
            new RemoveRuleExecutor(),
            new RepairExecutor(),
            new RespondExecutor(),
            new RocketExecutor(),
            new RTPExecutor(),
            new RuleExecutor(),
            new SetHomeExecutor(),
            new SetSpawnExecutor(),
            new SetWarpExecutor(),
            new SetWorldSpawnExecutor(),
            new SkullExecutor(),
            new SlapExecutor(),
            new SocialSpyExecutor(),
            new SpawnExecutor(),
            new SpeedExecutor(),
            new SudoExecutor(),
            new TakeExecutor(),
            new TeleportExecutor(),
            new TeleportPosExecutor(),
            new TeleportWorldExecutor(),
            new ThruExecutor(),
            new TimeExecutor(),
            new TPAAcceptExecutor(),
            new TPADenyExecutor(),
            new TPAExecutor(),
            new TPAHereExecutor(),
            new TPHereExecutor(),
            new UnmuteExecutor(),
            new WarpExecutor(),
            new WeatherExecutor(),
            new WhoisExecutor(),
            new WorldsBase(),
            new JailExecutor(),
            new UnJailExecutor(),
            new VanishExecutor(),
            new KickAllExecutor(),
            new WorldInfoExecutor(),
            new SlapExecutor(),
            new RocketExecutor(),
            new ClearInventoryExecutor(),
            new TempBanExecutor(),
            new SetNameExecutor(),
            new LoreBase(),
            new HelpOpExecutor(),
            new ListExecutor(),
            new StopExecutor(),
            new GodExecutor(),
            new TPChunkExecutor(),
            new TPAllExecutor(),
            new TPAAllExecutor()
        );
    }

    /**
     * Registers the EssentialCmds commands.
     */
    public static void registerCommands() {
        // TODO: Put module checks here in a stream.
        // getCommands().stream().filter(c -> c.getAssociatedModules().length == 0 && checkModules).forEach(CommandLoader::registerCommand);
        getCommands().forEach(cmd -> Sponge.getCommandManager().register(EssentialCmds.getEssentialCmds(), cmd.getSpec(), cmd.getAliases()));
    }
}
