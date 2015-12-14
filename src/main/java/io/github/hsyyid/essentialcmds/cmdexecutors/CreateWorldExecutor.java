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
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.DimensionType;
import org.spongepowered.api.world.DimensionTypes;
import org.spongepowered.api.world.GeneratorType;
import org.spongepowered.api.world.GeneratorTypes;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.WorldBuilder;
import org.spongepowered.api.world.difficulty.Difficulties;
import org.spongepowered.api.world.difficulty.Difficulty;

import java.util.Optional;

public class CreateWorldExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		String name = ctx.<String> getOne("name").get();
		String environmentInput = ctx.<String> getOne("environment").get();
		String gamemodeInput = ctx.<String> getOne("gamemode").get();
		String difficultyInput = ctx.<String> getOne("difficulty").get();

		Difficulty difficulty;
		DimensionType dimension;
		GeneratorType generator;
		GameMode gamemode;

		switch (environmentInput.toLowerCase())
		{
			case "overworld":
				dimension = DimensionTypes.OVERWORLD;
				generator = GeneratorTypes.OVERWORLD;
				break;
			case "flat":
				dimension = DimensionTypes.OVERWORLD;
				generator = GeneratorTypes.FLAT;
				break;
			case "superflat":
				dimension = DimensionTypes.OVERWORLD;
				generator = GeneratorTypes.FLAT;
				break;
			case "nether":
				dimension = DimensionTypes.NETHER;
				generator = GeneratorTypes.NETHER;
				break;
			case "end":
				dimension = DimensionTypes.THE_END;
				generator = GeneratorTypes.THE_END;
				break;
			default:
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Environment specified not found."));
				return CommandResult.success();
		}

		switch (gamemodeInput.toLowerCase())
		{

			case "survival":
				gamemode = GameModes.SURVIVAL;
				break;
			case "creative":
				gamemode = GameModes.CREATIVE;
				break;
			case "adventure":
				gamemode = GameModes.ADVENTURE;
				break;
			case "spectator":
				gamemode = GameModes.SPECTATOR;
				break;
			default:
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Gamemode specified not found."));
				return CommandResult.success();
		}

		switch (difficultyInput.toLowerCase())
		{
			case "easy":
				difficulty = Difficulties.EASY;
				break;
			case "hard":
				difficulty = Difficulties.HARD;
				break;
			case "normal":
				difficulty = Difficulties.NORMAL;
				break;
			case "peaceful":
				difficulty = Difficulties.PEACEFUL;
				break;
			default:
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Difficulty specified not found."));
				return CommandResult.success();
		}

		src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Beginning creation of world."));
		
		Optional<World> world = EssentialCmds.getEssentialCmds().getGame().getRegistry().createBuilder(WorldBuilder.class)
			.name(name)
			.enabled(true)
			.loadsOnStartup(true)
			.keepsSpawnLoaded(true)
			.dimensionType(dimension)
			.generator(generator)
			.gameMode(gamemode)
			.build();

		if (world.isPresent())
		{
			world.get().getProperties().setDifficulty(difficulty);
			src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.GOLD, "World ", TextColors.GRAY, name, TextColors.GOLD, " has been created."));
		}
		else
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "The world could not be created."));
		}

		return CommandResult.success();
	}
}
