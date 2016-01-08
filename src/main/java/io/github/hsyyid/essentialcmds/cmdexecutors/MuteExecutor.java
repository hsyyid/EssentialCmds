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
import io.github.hsyyid.essentialcmds.cmdexecutors.argumentparsers.TimespanParser;
import io.github.hsyyid.essentialcmds.internal.AsyncCommandExecutorBase;
import io.github.hsyyid.essentialcmds.utils.Utils;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class MuteExecutor extends AsyncCommandExecutorBase
{
	@Override
	public void executeAsync(CommandSource src, CommandContext ctx)
	{
		Game game = EssentialCmds.getEssentialCmds().getGame();
		Player p = ctx.<Player> getOne("player").get();

		// Uses the TimespanParser.
		Optional<Long> time = ctx.<Long> getOne("time");

		if (time.isPresent())
		{
			Task.Builder taskBuilder = game.getScheduler().createTaskBuilder();
			taskBuilder.execute(() -> {
				if (EssentialCmds.muteList.contains(p.getUniqueId()))
					EssentialCmds.muteList.remove(p.getUniqueId());
			}).delay(time.get(), TimeUnit.SECONDS).name("EssentialCmds - Remove previous mutes").submit(EssentialCmds.getEssentialCmds());
		}

		EssentialCmds.muteList.add(p.getUniqueId());
		Utils.addMute(p.getUniqueId());
		src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Player muted."));
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "mute" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec
				.builder()
				.description(Text.of("Mute Command"))
				.permission("essentialcmds.mute.use")
				.arguments(
						GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
						GenericArguments.onlyOne(GenericArguments.optional(new TimespanParser(Text.of("time"))))))
				.executor(this).build();
	}
}
