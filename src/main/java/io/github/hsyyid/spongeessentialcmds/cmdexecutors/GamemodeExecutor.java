package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.entity.GameModeData;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.entity.player.gamemode.GameModes;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.source.CommandBlockSource;
import org.spongepowered.api.util.command.source.ConsoleSource;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class GamemodeExecutor implements CommandExecutor
{
    public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
    {
        String gamemode = ctx.<String>getOne("gamemode").get();
        if(src instanceof Player)
        {
            Player player = (Player) src;
            
            if(gamemode.equals("creative") || gamemode.equals("c") || gamemode.equals(1))
            {
                GameModeData data = player.getGameModeData().set(Keys.GAME_MODE, GameModes.CREATIVE);
                player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Set your gamemode to creative"));
                player.offer(data);
                return CommandResult.success();
            }
            else if(gamemode.equals("survival") || gamemode.equals("s") || gamemode.equals(0))
            {
                GameModeData data = player.getGameModeData().set(Keys.GAME_MODE, GameModes.SURVIVAL);
                player.offer(data);
                player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Set your gamemode to survival"));
                return CommandResult.success();
            }
            else if(gamemode.equals("adventure") || gamemode.equals("a") || gamemode.equals(2))
            {
                GameModeData data = player.getGameModeData().set(Keys.GAME_MODE, GameModes.ADVENTURE);
                player.offer(data);
                player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Set your gamemode to adventure"));
                return CommandResult.success();
            }
            else if(gamemode.equals("spectator") || gamemode.equals("spec") || gamemode.equals(3))
            {
                GameModeData data = player.getGameModeData().set(Keys.GAME_MODE, GameModes.SPECTATOR);
                player.offer(data);
                player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Set your gamemode to spectator"));
                return CommandResult.success();
            }
            else
            {
                player.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, gamemode + " does not appear to be a gamemode!"));
            }
        }
        else if(src instanceof ConsoleSource) {
            src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use /gamemode!"));
        }
        else if(src instanceof CommandBlockSource) {
            src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use /gamemode!"));
        }

        return CommandResult.success();
    }
}