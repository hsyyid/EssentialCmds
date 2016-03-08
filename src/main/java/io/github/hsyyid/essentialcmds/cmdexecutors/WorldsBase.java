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

import com.flowpowered.math.vector.Vector3i;
import com.google.common.collect.Lists;
import io.github.hsyyid.essentialcmds.EssentialCmds;
import io.github.hsyyid.essentialcmds.internal.AsyncCommandExecutorBase;
import io.github.hsyyid.essentialcmds.internal.CommandExecutorBase;
import io.github.hsyyid.essentialcmds.utils.Utils;
import org.spongepowered.api.CatalogTypes;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.world.DimensionType;
import org.spongepowered.api.world.DimensionTypes;
import org.spongepowered.api.world.GeneratorType;
import org.spongepowered.api.world.GeneratorTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.WorldCreationSettings;
import org.spongepowered.api.world.difficulty.Difficulties;
import org.spongepowered.api.world.difficulty.Difficulty;
import org.spongepowered.api.world.storage.WorldProperties;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

public class WorldsBase extends CommandExecutorBase
{
	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "world" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("World Command"))
			.permission("essentialcmds.world.use")
			.children(getChildrenList(new List(), new Teleport(), new Spawn(), new SetSpawn(), new Load(), new Create(), new Delete(), new SetDifficulty(), new SetGamemode()))
			.build();
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
	{
		return CommandResult.empty();
	}

	static class Create extends CommandExecutorBase
	{
		public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
		{
			String name = ctx.<String> getOne("name").get();
			String dimensionInput = ctx.<String> getOne("dimension").get();
			String generatorInput = ctx.<String> getOne("generator").get();
			String difficultyInput = ctx.<String> getOne("difficulty").get();
			GameMode gamemode = ctx.<GameMode> getOne("gamemode").get();
			Difficulty difficulty = null;
			DimensionType dimension = null;
			GeneratorType generator = null;

			if (Sponge.getRegistry().getType(DimensionType.class, dimensionInput).isPresent())
			{
				dimension = Sponge.getRegistry().getType(DimensionType.class, dimensionInput).get();
			}
			else
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Dimension type specified not found."));
				return CommandResult.success();
			}

			if (Sponge.getRegistry().getType(GeneratorType.class, generatorInput).isPresent())
			{
				generator = Sponge.getRegistry().getType(GeneratorType.class, generatorInput).get();
			}
			else
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Generator type specified not found."));
				return CommandResult.success();
			}

			if (Sponge.getRegistry().getType(Difficulty.class, difficultyInput).isPresent())
			{
				difficulty = Sponge.getRegistry().getType(Difficulty.class, difficultyInput).get();
			}
			else
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Difficulty specified not found."));
				return CommandResult.success();
			}

			src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Beginning creation of world."));

			WorldCreationSettings worldSettings = EssentialCmds.getEssentialCmds().getGame().getRegistry().createBuilder(WorldCreationSettings.Builder.class)
				.name(name)
				.enabled(true)
				.loadsOnStartup(true)
				.keepsSpawnLoaded(true)
				.dimension(dimension)
				.generator(generator)
				.gameMode(gamemode)
				.build();

			Optional<WorldProperties> worldProperties = Sponge.getGame().getServer().createWorldProperties(worldSettings);

			if (worldProperties.isPresent())
			{
				Optional<World> world = Sponge.getGame().getServer().loadWorld(worldProperties.get());

				if (world.isPresent())
				{
					world.get().getProperties().setDifficulty(difficulty);
					src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.GOLD, "World ", TextColors.GRAY, name, TextColors.GOLD, " has been created."));
				}
				else
				{
					src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "The world could not be created."));
				}
			}
			else
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "The world properties could not be created."));
			}

			return CommandResult.success();
		}

		@Nonnull
		@Override
		public String[] getAliases()
		{
			return new String[] { "create" };
		}

		@Nonnull
		@Override
		public CommandSpec getSpec()
		{
			return CommandSpec
				.builder()
				.description(Text.of("Create World Command"))
				.permission("essentialcmds.world.create")
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))), GenericArguments.onlyOne(GenericArguments.string(Text.of("dimension"))), GenericArguments.onlyOne(GenericArguments.string(Text.of("generator"))), GenericArguments.onlyOne(GenericArguments.catalogedElement(Text.of("gamemode"), CatalogTypes.GAME_MODE)), GenericArguments.onlyOne(GenericArguments.string(Text.of("difficulty"))))
				.executor(this).build();
		}
	}

	static class SetDifficulty extends CommandExecutorBase
	{
		public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
		{
			String difficultyInput = ctx.<String> getOne("difficulty").get();
			Optional<String> worldName = ctx.<String> getOne("world");

			if (worldName.isPresent())
			{
				if (Sponge.getServer().getWorld(worldName.get()).isPresent())
				{
					World world = Sponge.getServer().getWorld(worldName.get()).get();
					Optional<Difficulty> difficulty = Sponge.getRegistry().getType(Difficulty.class, difficultyInput);

					if (difficulty.isPresent())
					{
						world.getProperties().setDifficulty(difficulty.get());
						src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Set difficulty!"));
					}
					else
					{
						src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Difficulty not found!"));
					}
				}
				else
				{
					src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "World not found!"));
				}
			}
			else
			{
				if (src instanceof Player)
				{
					Player player = (Player) src;
					World world = player.getWorld();
					Optional<Difficulty> difficulty = Sponge.getRegistry().getType(Difficulty.class, difficultyInput);

					if (difficulty.isPresent())
					{
						world.getProperties().setDifficulty(difficulty.get());
						src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Set difficulty!"));
					}
					else
					{
						src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Difficulty not found!"));
					}
				}
				else
				{
					src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be an in-game player to use /world difficulty!"));
				}
			}

			return CommandResult.success();
		}

		@Nonnull
		@Override
		public String[] getAliases()
		{
			return new String[] { "difficulty" };
		}

		@Nonnull
		@Override
		public CommandSpec getSpec()
		{
			return CommandSpec.builder()
				.description(Text.of("Difficulty World Command"))
				.permission("essentialcmds.world.difficulty")
				.arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.string(Text.of("difficulty"))), GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("world"))))))
				.executor(this)
				.build();
		}
	}

	static class Delete extends CommandExecutorBase
	{
		public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
		{
			String worldName = ctx.<String> getOne("name").get();
			Game game = EssentialCmds.getEssentialCmds().getGame();

			World foundWorld = null;
			World altWorld = null;

			for (World world : game.getServer().getWorlds())
			{
				if (world.getName().equals(worldName))
				{
					foundWorld = world;
					break;
				}
			}

			for (World world : game.getServer().getWorlds())
			{
				if (!world.getName().equals(worldName))
				{
					altWorld = world;
					break;
				}
			}

			if (foundWorld != null)
			{
				for (Player player : game.getServer().getOnlinePlayers())
				{
					if (player.getWorld().getUniqueId().equals(foundWorld.getUniqueId()) && altWorld != null)
					{
						player.transferToWorld(altWorld.getName(), altWorld.getSpawnLocation().getPosition());

					}
				}

				foundWorld.getProperties().setEnabled(false);
				src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Disabled world. To fully delete the world, please delete it from your files."));
			}
			else
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "The world you specified was not found."));
			}

			return CommandResult.success();
		}

		@Nonnull
		@Override
		public String[] getAliases()
		{
			return new String[] { "delete", "del" };
		}

		@Nonnull
		@Override
		public CommandSpec getSpec()
		{
			return CommandSpec.builder().description(Text.of("Delete World Command")).permission("essentialcmds.world.delete")
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("name")))).executor(this).build();
		}
	}

	static class List extends AsyncCommandExecutorBase
	{

		@Override
		public void executeAsync(CommandSource src, CommandContext ctx)
		{
			ArrayList<String> worlds = EssentialCmds.getEssentialCmds().getGame().getServer().getWorlds().stream().filter(world -> world.getProperties().isEnabled()).map(World::getName).collect(Collectors.toCollection(ArrayList::new));

			PaginationService paginationService = Sponge.getServiceManager().provide(PaginationService.class).get();
			ArrayList<Text> worldText = Lists.newArrayList();

			for (String name : worlds)
			{
				Text item = Text.builder(name)
					.onClick(TextActions.runCommand("/tpworld " + name))
					.onHover(TextActions.showText(Text.of(TextColors.WHITE, "Teleport to world ", TextColors.GOLD, name)))
					.color(TextColors.DARK_AQUA)
					.style(TextStyles.UNDERLINE)
					.build();

				worldText.add(item);
			}

			PaginationList.Builder paginationBuilder = paginationService.builder().contents(worldText).title(Text.of(TextColors.GREEN, "Showing Worlds")).padding(Text.of("-"));
			paginationBuilder.sendTo(src);
		}

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
				.description(Text.of("List World Command"))
				.permission("essentialcmds.worlds.list")
				.executor(this)
				.build();
		}
	}

	static class Teleport extends CommandExecutorBase
	{

		@Nonnull
		@Override
		public String[] getAliases()
		{
			return new String[] { "tp", "teleport" };
		}

		@Nonnull
		@Override
		public CommandSpec getSpec()
		{
			return CommandSpec.builder()
				.description(Text.of("Teleport World Command"))
				.permission("essentialcmds.tpworld.use")
				.arguments(GenericArguments.seq(GenericArguments.string(Text.of("name")), GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))))))
				.executor(this)
				.build();
		}

		@Override
		public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
		{
			Optional<Player> optPlayer = ctx.<Player> getOne("player");
			String name = ctx.<String> getOne("name").get();

			if (!optPlayer.isPresent())
			{
				if (src instanceof Player)
				{
					Player player = (Player) src;
					Optional<World> optWorld = Sponge.getGame().getServer().getWorld(name);

					if (optWorld.isPresent())
					{
						Utils.setLastTeleportOrDeathLocation(player.getUniqueId(), player.getLocation());
						Location<World> spawnLocation = optWorld.get().getSpawnLocation();
						player.transferToWorld(name, spawnLocation.getPosition());
						src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported to world."));
					}
					else
					{
						src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "World specified does not exist!"));
					}
				}
				else
				{
					src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You cannot teleport, you are not a player!"));
				}
			}
			else
			{
				Player player = optPlayer.get();

				Optional<World> optWorld = Sponge.getGame().getServer().getWorld(name);

				if (optWorld.isPresent())
				{
					Utils.setLastTeleportOrDeathLocation(player.getUniqueId(), player.getLocation());
					Location<World> spawnLocation = optWorld.get().getSpawnLocation();
					player.transferToWorld(name, spawnLocation.getPosition());
					src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported player to world."));
					player.sendMessage(Text.of(TextColors.GOLD, src.getName(), TextColors.GRAY, " has teleported you to this world."));
				}
				else
				{
					src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "World specified does not exist!"));
				}
			}

			return CommandResult.success();
		}
	}

	static class Spawn extends CommandExecutorBase
	{

		@Nonnull
		@Override
		public String[] getAliases()
		{
			return new String[] { "spawn" };
		}

		@Nonnull
		@Override
		public CommandSpec getSpec()
		{
			return CommandSpec.builder()
				.description(Text.of("World Spawn Command"))
				.permission("essentialcmds.world.spawn.use")
				.executor(this)
				.build();
		}

		@Override
		public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
		{
			if (src instanceof Player)
			{
				Player player = (Player) src;
				Vector3i spawnPos = player.getWorld().getProperties().getSpawnPosition();
				player.setLocation(new Location<>(player.getWorld(), spawnPos.getX(), spawnPos.getY(), spawnPos.getZ()));
				src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported to world spawn."));
			}
			else
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You cannot teleport, you are not a player!"));
			}

			return CommandResult.success();
		}
	}

	static class SetSpawn extends CommandExecutorBase
	{

		@Nonnull
		@Override
		public String[] getAliases()
		{
			return new String[] { "setspawn" };
		}

		@Nonnull
		@Override
		public CommandSpec getSpec()
		{
			return CommandSpec.builder()
				.description(Text.of("Set World Spawn Command"))
				.permission("essentialcmds.world.spawn.set")
				.executor(new SetWorldSpawnExecutor())
				.build();
		}

		@Override
		public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
		{
			if (src instanceof Player)
			{
				Player player = (Player) src;
				player.getWorld().getProperties().setSpawnPosition(player.getLocation().getBlockPosition());
				src.sendMessage(Text.of(TextColors.GREEN, "Success: ", TextColors.YELLOW, "World spawn set."));
			}
			else if (src instanceof ConsoleSource || src instanceof CommandBlockSource)
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /world setspawn!"));
			}

			return CommandResult.success();
		}
	}

	static class Load extends CommandExecutorBase
	{

		@Nonnull
		@Override
		public String[] getAliases()
		{
			return new String[] { "load" };
		}

		@Nonnull
		@Override
		public CommandSpec getSpec()
		{
			return CommandSpec
				.builder()
				.description(Text.of("Load World Command"))
				.permission("essentialcmds.world.load")
				.arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))), GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("dimension")))), GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("generator")))), GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.catalogedElement(Text.of("gamemode"), CatalogTypes.GAME_MODE))), GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("difficulty"))))))
				.executor(this)
				.build();
		}

		@Override
		public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
		{
			String name = ctx.<String> getOne("name").get();
			Optional<String> dimensionInput = ctx.<String> getOne("dimension");
			Optional<String> generatorInput = ctx.<String> getOne("generator");
			Optional<String> difficultyInput = ctx.<String> getOne("difficulty");
			Optional<GameMode> gamemode = ctx.<GameMode> getOne("gamemode");
			Difficulty difficulty = Difficulties.NORMAL;
			DimensionType dimension = DimensionTypes.OVERWORLD;
			GeneratorType generator = GeneratorTypes.OVERWORLD;

			if (dimensionInput.isPresent() && Sponge.getRegistry().getType(DimensionType.class, dimensionInput.get()).isPresent())
			{
				dimension = Sponge.getRegistry().getType(DimensionType.class, dimensionInput.get()).get();
			}
			else if (dimensionInput.isPresent())
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Dimension type specified not found."));
			}

			if (generatorInput.isPresent() && Sponge.getRegistry().getType(GeneratorType.class, generatorInput.get()).isPresent())
			{
				generator = Sponge.getRegistry().getType(GeneratorType.class, generatorInput.get()).get();
			}
			else if (generatorInput.isPresent())
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Generator type specified not found."));
			}

			if (difficultyInput.isPresent() && Sponge.getRegistry().getType(Difficulty.class, difficultyInput.get()).isPresent())
			{
				difficulty = Sponge.getRegistry().getType(Difficulty.class, difficultyInput.get()).get();
			}
			else if (difficultyInput.isPresent())
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Difficulty specified not found."));
			}

			src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Beginning loading of world."));

			WorldCreationSettings worldSettings = EssentialCmds.getEssentialCmds().getGame().getRegistry().createBuilder(WorldCreationSettings.Builder.class)
				.name(name)
				.enabled(true)
				.loadsOnStartup(true)
				.keepsSpawnLoaded(true)
				.dimension(dimension)
				.generator(generator)
				.gameMode(gamemode.orElse(GameModes.SURVIVAL))
				.build();

			Optional<WorldProperties> worldProperties = Sponge.getGame().getServer().createWorldProperties(worldSettings);

			if (worldProperties.isPresent())
			{
				Optional<World> world = Sponge.getGame().getServer().loadWorld(worldProperties.get());

				if (world.isPresent())
				{
					world.get().getProperties().setDifficulty(difficulty);
					src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.GOLD, "World ", TextColors.GRAY, name, TextColors.GOLD, " has been loaded."));
				}
				else
				{
					src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "The world could not be created."));
				}
			}
			else
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "The world properties could not be created."));
			}

			return CommandResult.success();
		}
	}

	static class SetGamemode extends CommandExecutorBase
	{

		@Nonnull
		@Override
		public String[] getAliases()
		{
			return new String[] { "setgamemode" };
		}

		@Nonnull
		@Override
		public CommandSpec getSpec()
		{
			return CommandSpec
				.builder()
				.description(Text.of("SetGamemode World Command"))
				.permission("essentialcmds.world.setgamemode")
				.arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.catalogedElement(Text.of("gamemode"), CatalogTypes.GAME_MODE))), GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("world")))))
				.executor(this)
				.build();
		}

		@Override
		public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
		{
			GameMode gamemode = ctx.<GameMode> getOne("gamemode").get();
			Optional<String> worldName = ctx.<String> getOne("world");
			World world = null;

			if (worldName.isPresent())
			{
				world = Sponge.getServer().getWorld(worldName.get()).orElse(null);
			}
			else
			{
				if (src instanceof Player)
				{
					Player player = (Player) src;
					world = player.getWorld();
				}
				else
				{
					src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be an in-game player or specify the world name to set its gamemode!"));
				}
			}

			if (world != null)
			{
				world.getProperties().setGameMode(gamemode);
				src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Set gamemode of world."));
			}
			else
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "World not found!"));
			}

			return CommandResult.success();
		}
	}
}
