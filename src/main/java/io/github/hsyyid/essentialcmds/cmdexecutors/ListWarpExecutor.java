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
import io.github.hsyyid.essentialcmds.internal.AsyncCommandExecutorBase;
import io.github.hsyyid.essentialcmds.utils.Utils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

public class ListWarpExecutor extends AsyncCommandExecutorBase
{
	@Override
	public void executeAsync(CommandSource src, CommandContext ctx)
	{
		if (src instanceof Player)
		{
			Player player = (Player) src;
			Set<Object> warps = Utils.getWarps();

			if (warps.size() == 0)
			{
				player.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "No warps set!"));
				return;
			}

			if (warps.size() > 0)
			{
				PaginationService paginationService = Sponge.getServiceManager().provide(PaginationService.class).get();
				List<Text> warpText = Lists.newArrayList();

				for (Object name : warps)
				{
					if (player.hasPermission("essentialcmds.warp.use." + String.valueOf(name)))
					{
						Text item = Text.builder(String.valueOf(name))
							.onClick(TextActions.runCommand("/warp " + String.valueOf(name)))
							.onHover(TextActions.showText(Text.of(TextColors.WHITE, "Warp to ", TextColors.GOLD, String.valueOf(name))))
							.color(TextColors.DARK_AQUA)
							.style(TextStyles.UNDERLINE)
							.build();

						warpText.add(item);
					}
				}

				PaginationList.Builder paginationBuilder = paginationService.builder().contents(warpText).title(Text.of(TextColors.GREEN, "Showing Warps")).padding(Text.of("-"));
				paginationBuilder.sendTo(src);
			}
			else
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "No warps set!"));
			}
		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /warps!"));
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /warps!"));
		}
	}

	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "warps" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("List Warps Command"))
			.permission("essentialcmds.warps.list")
			.executor(this).build();
	}
}
