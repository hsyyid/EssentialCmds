package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import io.github.hsyyid.spongeessentialcmds.Main;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class PardonExecutor implements CommandExecutor
{
    public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
    {
        Game game = Main.game;
        Server server = game.getServer();
        String player = ctx.<String>getOne("player").get();

        game.getCommandDispatcher().process(server.getConsole(), "minecraft:pardon " +  player);
        src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Player unbanned."));
      
        return CommandResult.success();
    }
}
