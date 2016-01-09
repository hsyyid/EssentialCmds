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

import io.github.hsyyid.essentialcmds.EssentialCmds;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;

/**
 * Represents a command that can be run on an Async thread.
 */
public abstract class AsyncCommandExecutorBase extends CommandExecutorBase {

    /**
     * The command to execute on an Async scheduler thread.
     *
     * <p>
     *     <strong>DO NOT USE NON-THREAD SAFE API CALLS WITHIN THIS METHOD</strong> unless you use a scheduler to put the
     *     calls back on the main thread.
     * </p>
     *
     * @param src The {@link CommandSource}
     * @param args The arguments.
     */
    public abstract void executeAsync(CommandSource src, CommandContext args);

    @Override
    public final CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Sponge.getScheduler().createAsyncExecutor(EssentialCmds.getEssentialCmds()).execute(() -> executeAsync(src, args));
        return CommandResult.success();
    }
}
