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

import io.github.hsyyid.essentialcmds.api.util.config.Configs;
import io.github.hsyyid.essentialcmds.utils.Utils;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.source.CommandBlockSource;
import org.spongepowered.api.util.command.source.ConsoleSource;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class DeleteWarpExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		String warpName = ctx.<String> getOne("warp name").get();
		
		if (src instanceof Player)
		{
			Player player = (Player) src;
			if (Utils.isWarpInConfig(warpName))
			{
				ConfigurationNode warpNode = Configs.getConfig().getNode((Object[]) ("warps.warps").split("\\."));

				// Get Value of Warp Node
				String warps = warpNode.getString();

				// Remove Warp
				String newVal = warps.replace(warpName + ",", "");
				warpNode.setValue(newVal);

				// Save CONFIG
				Configs.saveConfig();

				// Get Item Node
				ConfigurationNode itemNode = Configs.getConfig().getNode((Object[]) ("home.users." + player.getUniqueId() + ".").split("\\."));
				itemNode.removeChild(warpName);

				// save config
				Configs.saveConfig();
				src.sendMessage(Texts.of(TextColors.GREEN, "Success: ", TextColors.YELLOW, "Deleted warp " + warpName));
			}
			else
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "This warp doesn't exist!"));
			}
		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /delwarp!"));
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /delwarp!"));
		}
		return CommandResult.success();
	}
}
