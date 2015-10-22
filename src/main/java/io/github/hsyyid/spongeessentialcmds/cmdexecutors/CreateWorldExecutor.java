package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import org.spongepowered.api.world.difficulty.Difficulties;

import io.github.hsyyid.spongeessentialcmds.SpongeEssentialCmds;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;
import org.spongepowered.api.world.DimensionType;
import org.spongepowered.api.world.DimensionTypes;
import org.spongepowered.api.world.GeneratorType;
import org.spongepowered.api.world.GeneratorTypes;
import org.spongepowered.api.world.World;
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
			case "nether":
				dimension = DimensionTypes.NETHER;
				generator = GeneratorTypes.NETHER;
				break;
			case "end":
				dimension = DimensionTypes.END;
				generator = GeneratorTypes.END;
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

		Optional<World> world = SpongeEssentialCmds.game.getRegistry().createWorldBuilder()
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

		return CommandResult.success();
	}
}
