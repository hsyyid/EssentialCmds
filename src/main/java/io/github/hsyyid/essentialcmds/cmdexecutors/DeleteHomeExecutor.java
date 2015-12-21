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
import io.github.hsyyid.essentialcmds.api.util.config.Configurable;
import io.github.hsyyid.essentialcmds.managers.config.Config;
import io.github.hsyyid.essentialcmds.utils.Utils;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

public class DeleteHomeExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		String homeName = ctx.<String> getOne("home name").get();
		Configurable config = Config.getConfig();
		
		if (src instanceof Player)
		{
			Player player = (Player) src;
			if (Utils.inConfig(player.getUniqueId(), homeName))
			{
				ConfigurationNode homeNode = Configs.getConfig(config).getNode("home", "users", player.getUniqueId().toString(), "homes");

				// Get Value of Home Node
				String homes = homeNode.getString();
				String newVal = homes.replace(homeName + ",", "");
				
				Configs.setValue(config, homeNode.getPath(), newVal);
				Configs.removeChild(config, new Object[]{"home", "users", player.getUniqueId().toString()}, homeName);
				
				src.sendMessage(Texts.of(TextColors.GREEN, "Success: ", TextColors.YELLOW, "Deleted home " + homeName));

				return CommandResult.success();
			}
			else
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "This home doesn't exist!"));
				return CommandResult.empty();
			}
		}
		else {
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /delhome!"));
			return CommandResult.success();
		}
	}
}
