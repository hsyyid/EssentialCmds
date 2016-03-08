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

import com.google.common.collect.Lists;
import io.github.hsyyid.essentialcmds.internal.CommandExecutorBase;
import io.github.hsyyid.essentialcmds.utils.Utils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.List;

import javax.annotation.Nonnull;

public class BlacklistBase extends CommandExecutorBase
{
	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "blacklist", "bl" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("Blacklist Command"))
			.permission("essentialcmds.blacklist.use")
			.children(getChildrenList(new BlacklistAddExecutor(), new BlacklistListExecutor(), new BlacklistRemoveExecutor()))
			.build();
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		return CommandResult.empty();
	}

	private static class BlacklistAddExecutor extends CommandExecutorBase
	{

		@Nonnull
		@Override
		public String[] getAliases()
		{
			return new String[] { "add" };
		}

		@Nonnull
		@Override
		public CommandSpec getSpec()
		{
			return CommandSpec.builder()
				.description(Text.of("Add Blacklist Command"))
				.permission("essentialcmds.blacklist.add")
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("item id"))))
				.executor(this)
				.build();
		}

		@Override
		public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
		{
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

	private static class BlacklistRemoveExecutor extends CommandExecutorBase
	{

		@Nonnull
		@Override
		public String[] getAliases()
		{
			return new String[] { "remove" };
		}

		@Nonnull
		@Override
		public CommandSpec getSpec()
		{
			return CommandSpec.builder()
				.description(Text.of("Remove Blacklist Command"))
				.permission("essentialcmds.blacklist.remove")
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("item id"))))
				.executor(this)
				.build();
		}

		@Override
		public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
		{
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

	private static class BlacklistListExecutor extends CommandExecutorBase
	{

		@Nonnull
		@Override
		public String[] getAliases()
		{
			return new String[] { "list" };
		}

		@Nonnull
		@Override
		public CommandSpec getSpec()
		{
			return CommandSpec.builder()
				.description(Text.of("List Blacklist Command"))
				.permission("essentialcmds.blacklist.list")
				.executor(this)
				.build();
		}

		@Override
		public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
		{
			List<String> blacklistItems = Utils.getBlacklistItems();

			if (blacklistItems.size() == 0)
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "There are no blacklisted items!"));
				return CommandResult.success();
			}

			PaginationService paginationService = Sponge.getServiceManager().provide(PaginationService.class).get();
			List<Text> blacklistText = Lists.newArrayList();

			for (String name : blacklistItems)
			{
				Text item = Text.builder(name)
					.color(TextColors.DARK_AQUA)
					.style(TextStyles.UNDERLINE)
					.build();

				blacklistText.add(item);
			}

			PaginationList.Builder paginationBuilder = paginationService.builder().contents(blacklistText).title(Text.of(TextColors.GREEN, "Showing Blacklist")).padding(Text.of("-"));
			paginationBuilder.sendTo(src);

			return CommandResult.success();
		}
	}
}
