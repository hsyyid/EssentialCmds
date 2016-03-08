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
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.weather.Weather;
import org.spongepowered.api.world.weather.Weathers;

import javax.annotation.Nonnull;
import java.util.Optional;

public class WeatherExecutor extends CommandExecutorBase
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		String weatherString = ctx.<String> getOne("weather").get();
		Optional<Integer> duration = ctx.<Integer> getOne("duration");

		if (src instanceof Player)
		{
			Player player = (Player) src;

			Weather weather;

			if (weatherString.toLowerCase().equals("clear") || weatherString.toLowerCase().equals("sun"))
			{
				weather = Weathers.CLEAR;
				player.sendMessage(Text.of(TextColors.GOLD, "Changing weather to ", TextColors.GRAY, "sunny."));
			}
			else if (weatherString.toLowerCase().equals("rain"))
			{
				weather = Weathers.RAIN;
				player.sendMessage(Text.of(TextColors.GOLD, "Changing weather to ", TextColors.GRAY, "rain."));
			}
			else if (weatherString.toLowerCase().equals("storm") || weatherString.toLowerCase().equals("thunderstorm"))
			{
				weather = Weathers.THUNDER_STORM;
				player.sendMessage(Text.of(TextColors.GOLD, "Changing weather to ", TextColors.GRAY, "storm."));
			}
			else
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Input invalid: " + weatherString));
				return CommandResult.success();
			}

			if (duration.isPresent())
			{
				player.getWorld().setWeather(weather, duration.get());
			}
			else
			{
				player.getWorld().setWeather(weather);
			}
			return CommandResult.success();
		}
		else
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be a in-game player to do /weather!"));
			return CommandResult.success();
		}
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "weather" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec
				.builder()
				.description(Text.of("Weather Command"))
				.permission("essentialcmds.weather.use")
				.arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.string(Text.of("weather")))),
						GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.integer(Text.of("duration")))))
				.executor(this).build();
	}
}
