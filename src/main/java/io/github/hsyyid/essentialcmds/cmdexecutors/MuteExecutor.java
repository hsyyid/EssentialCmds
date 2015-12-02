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

import io.github.hsyyid.essentialcmds.EssentialCmds;
import io.github.hsyyid.essentialcmds.utils.Utils;
import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.scheduler.Task;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class MuteExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Game game = EssentialCmds.game;
		Player p = ctx.<Player> getOne("player").get();
		Optional<Long> time = ctx.<Long> getOne("time");
		Optional<String> timeUnit = ctx.<String> getOne("time unit");

		if (time.isPresent() && timeUnit.isPresent())
		{
			TimeUnit unit;

			if (timeUnit.get().toLowerCase().equals("m"))
			{
				unit = TimeUnit.MINUTES;
			}
			else if (timeUnit.get().toLowerCase().equals("h"))
			{
				unit = TimeUnit.HOURS;
			}
			else if (timeUnit.get().toLowerCase().equals("s"))
			{
				unit = TimeUnit.SECONDS;
			}
			else if (timeUnit.get().toLowerCase().equals("d"))
			{
				unit = TimeUnit.DAYS;
			}
			else
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! Invalid time unit."));
				return CommandResult.empty();
			}

			Task.Builder taskBuilder = game.getScheduler().createTaskBuilder();
			taskBuilder.execute(() -> {
				if (EssentialCmds.muteList.contains(p.getUniqueId()))
					EssentialCmds.muteList.remove(p.getUniqueId());
			}).interval(time.get(), unit).name("EssentialCmds - Remove previous mutes").submit(EssentialCmds.game.getPluginManager().getPlugin("EssentialCmds").get().getInstance());
		}

		EssentialCmds.muteList.add(p.getUniqueId());
		Utils.addMute(p.getUniqueId());
		src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Player muted."));

		return CommandResult.success();
	}
}
