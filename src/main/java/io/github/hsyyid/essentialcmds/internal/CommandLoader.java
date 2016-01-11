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
import io.github.hsyyid.essentialcmds.cmdexecutors.*;
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
            new KickAllExecutor()
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
