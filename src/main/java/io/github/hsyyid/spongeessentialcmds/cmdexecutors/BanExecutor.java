package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import java.util.Optional;

import io.github.hsyyid.spongeessentialcmds.Main;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class BanExecutor implements CommandExecutor
{
    public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
    {
        Game game = Main.game;
        Server server = game.getServer();
        Player player = ctx.<Player>getOne("player").get();
        Optional<String> reason = ctx.<String>getOne("reason");

        if(reason.isPresent())
        {
            game.getCommandDispatcher().process(server.getConsole(), "minecraft:ban " +  player.getName() + " " + reason.get());
        }
        else
        {
            game.getCommandDispatcher().process(server.getConsole(), "minecraft:ban " +  player.getName() + " " + "The BanHammer has Spoken!");
        }
        
        src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Player banned."));
        return CommandResult.success();
    }
}
