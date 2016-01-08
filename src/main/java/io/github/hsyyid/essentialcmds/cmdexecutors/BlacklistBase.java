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
package io.github.hsyyid.essentialcmds.cmdexecutors;

import io.github.hsyyid.essentialcmds.internal.CommandExecutorBase;
import io.github.hsyyid.essentialcmds.utils.PaginatedList;
import io.github.hsyyid.essentialcmds.utils.Utils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class BlacklistBase extends CommandExecutorBase {
    @Nonnull
    @Override
    public String[] getAliases() {
        return new String[] { "blacklist", "bl" };
    }

    @Nonnull
    @Override
    public CommandSpec getSpec() {
        return CommandSpec.builder()
            .description(Text.of("Blacklist Command"))
            .permission("essentialcmds.blacklist.use")
            .children(getChildrenList(new BlacklistAddExecutor(), new BlacklistListExecutor(), new BlacklistRemoveExecutor()))
            .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
        return CommandResult.empty();
    }

    private static class BlacklistAddExecutor extends CommandExecutorBase {

        @Nonnull
        @Override
        public String[] getAliases() {
            return new String[] { "add" };
        }

        @Nonnull
        @Override
        public CommandSpec getSpec() {
            return CommandSpec.builder()
                    .description(Text.of("Add Blacklist Command"))
                    .permission("essentialcmds.blacklist.add")
                    .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("item id"))))
                    .executor(this)
                    .build();
        }

        @Override
        public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
            String itemId = ctx.<String> getOne("item id").get();

            if (!Utils.getBlacklistItems().contains(itemId))
            {
                Utils.addBlacklistItem(itemId);
                src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, itemId + " has been blacklisted."));
            }
            else
            {
                src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, itemId + " has already been blacklisted."));
            }

            return CommandResult.success();
        }
    }

    private static class BlacklistRemoveExecutor extends CommandExecutorBase {

        @Nonnull
        @Override
        public String[] getAliases() {
            return new String[] { "remove" };
        }

        @Nonnull
        @Override
        public CommandSpec getSpec() {
            return CommandSpec.builder()
                    .description(Text.of("Remove Blacklist Command"))
                    .permission("essentailcmds.blacklist.remove")
                    .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("item id"))))
                    .executor(this)
                    .build();
        }

        @Override
        public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
            String itemId = ctx.<String> getOne("item id").get();

            if (Utils.getBlacklistItems().contains(itemId))
            {
                Utils.removeBlacklistItem(itemId);
                src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, itemId + " has been un-blacklisted."));
            }
            else
            {
                src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "This item is not blacklisted."));
            }

            return CommandResult.success();
        }
    }

    private static class BlacklistListExecutor extends CommandExecutorBase {

        @Nonnull
        @Override
        public String[] getAliases() {
            return new String[] { "list" };
        }

        @Nonnull
        @Override
        public CommandSpec getSpec() {
            return CommandSpec.builder()
                    .description(Text.of("List Blacklist Command"))
                    .permission("essentailcmds.blacklist.list")
                    .arguments(GenericArguments.optional(
                            GenericArguments.onlyOne(GenericArguments.integer(Text.of("page no")))))
                    .executor(this)
                    .build();
        }

        @Override
        public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
            Optional<Integer> arguments = ctx.<Integer> getOne("page no");

            if (src instanceof Player)
            {
                Player player = (Player) src;
                List<String> blacklistItems = Utils.getBlacklistItems();

                if(blacklistItems.size() == 0)
                {
                    player.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "There are no blacklisted items!"));
                    return CommandResult.success();
                }

                int pgNo = arguments.orElse(1);

                PaginatedList pList = new PaginatedList("/blacklist list");

                for (String name : blacklistItems)
                {
                    Text item = Text.builder(name)
                            .color(TextColors.DARK_AQUA)
                            .style(TextStyles.UNDERLINE)
                            .build();

                    pList.add(item);
                }

                pList.setItemsPerPage(10);

                Text.Builder header = Text.builder();
                header.append(Text.of(TextColors.GREEN, "------------"));
                header.append(Text.of(TextColors.GREEN, " Showing Blacklist page " + pgNo + " of " + pList.getTotalPages() + " "));
                header.append(Text.of(TextColors.GREEN, "------------"));

                pList.setHeader(header.build());

                if(pgNo > pList.getTotalPages())
                    pgNo = 1;

                src.sendMessage(pList.getPage(pgNo));
            }
            else if (src instanceof ConsoleSource)
            {
                src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /blacklist list!"));
            }
            else if (src instanceof CommandBlockSource)
            {
                src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /blacklist list!"));
            }

            return CommandResult.success();
        }
    }
}
