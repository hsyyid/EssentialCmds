package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import io.github.hsyyid.spongeessentialcmds.Main;

import com.google.common.base.Optional;
import io.github.hsyyid.spongeessentialcmds.utils.Powertool;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.source.CommandBlockSource;
import org.spongepowered.api.util.command.source.ConsoleSource;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class PowertoolExecutor implements CommandExecutor
{
    public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
    {
        if(src instanceof Player)
        {
            Optional<String> optionalCommand = ctx.<String>getOne("command");
            Player player = (Player) src;
            if(player.getItemInHand().isPresent())
            {
                if(optionalCommand.isPresent())
                {
                    String command = optionalCommand.get();
                    Powertool powertool = new Powertool(player, player.getItemInHand().get().getItem().getName(), command);
                    Main.powertools.add(powertool);
                    player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Successfully bound command ", TextColors.BLUE, command, TextColors.YELLOW, " to ", TextColors.RED, player.getItemInHand().get().getItem().getName(), TextColors.YELLOW, "!"));
                }
                else
                {
                    Powertool powertoolToRemove = null;
                    for(Powertool powertool : Main.powertools)
                    {
                        if(powertool.getPlayer().equals(player) && powertool.getItemID().equals(player.getItemInHand().get().getItem().getName()))
                        {
                            powertoolToRemove = powertool;
                            break;
                        }
                    }
                    
                    if(powertoolToRemove != null)
                    {
                        Main.powertools.remove(powertoolToRemove);
                        player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Removed command from this powertool!"));
                    }
                    else
                    {
                        player.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "There is no command assigned to this!"));
                    }
                }
            }
            else
            {
                player.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be holding something to use /powertool!"));
            }

        }
        else if(src instanceof ConsoleSource) {
            src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use /powertool!"));
        }
        else if(src instanceof CommandBlockSource) {
            src.sendMessage(Texts.of(TextColors.DARK_RED,"Error! ", TextColors.RED, "Must be an in-game player to use /powertool!"));
        }
        return CommandResult.success();
    }
}