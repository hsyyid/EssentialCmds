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

import com.google.common.base.Preconditions;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * An interface that allows a {@link CommandCallable} to be specified along with an executor.
 */
public abstract class CommandExecutorBase implements CommandExecutor {

    /**
     * Gets the modules that this command belongs to, to determine whether it is loaded or not.
     *
     * <p>
     *     This command should be loaded if <strong>one</strong> of the modules is selected for loading.
     * </p>
     *
     * @return The name of the modules, or an empty array if it must ALWAYS be loaded.
     */
    protected String[] getAssociatedModules() {
        return new String[0];
    }

    /**
     * Gets the first alias from the {@link #getAliases()} command, by default.
     *
     * @return The first alias.
     */
    @Nonnull
    public String getPrimaryAlias() {
        return getAliases()[0];
    }

    /**
     * Gets the aliases for this command.
     *
     * @return An array of {@link String} aliases.
     */
    @Nonnull public abstract String[] getAliases();

    /**
     * Returns the built {@link CommandSpec} for this command.
     *
     * @return The {@link CommandSpec}
     */
    @Nonnull public abstract CommandSpec getSpec();

    /**
     * Creates a {@link CommandSpec} compatible version of the child commands from {@link CommandExecutorBase} objects.
     *
     * @param commands The commands to add to the map.
     * @return The object to add to the {@link org.spongepowered.api.command.spec.CommandSpec.Builder#children(Map)} method.
     */
    protected final Map<List<String>, CommandCallable> getChildrenList(CommandExecutorBase... commands) {
        Preconditions.checkArgument(commands.length > 0, "There needs to be at least one CommandExecutorBase");
        return Arrays.asList(commands).stream().collect(Collectors.toMap(a -> Arrays.asList(a.getAliases()), CommandExecutorBase::getSpec));
    }

    /**
     * Overridden implementation of the Hash Code so that all instantiations are seen as equal.
     *
     * @return The hash code of the name of the class.
     */
    @Override
    public int hashCode() {
        return getClass().getName().hashCode();
    }

    /**
     * Overridden implementation of the equals method so that all instantiations are seen as the same.
     *
     * @param obj The object to compare with.
     * @return <code>true</code> if the class is the same as the other.
     */
    @Override
    public boolean equals(Object obj) {
        return obj.getClass().equals(getClass());
    }
}
