package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import java.util.Optional;
import io.github.hsyyid.spongeessentialcmds.utils.Utils;
import org.spongepowered.api.data.manipulator.mutable.entity.HealthData;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class KillExecutor implements CommandExecutor
{
    public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
    {
        Optional<Player> p = ctx.<Player>getOne("player");
        
        if(p.isPresent())
        {
            HealthData data = p.get().getHealthData();
            Value<Double> newHealth = data.health().set(0d);
            data.set(newHealth);
            p.get().offer(data);
            Utils.setLastDeathLocation(p.get().getUniqueId(), p.get().getLocation());
            src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Killed player " + p.get().getName()));
            p.get().sendMessage(Texts.of(TextColors.RED, "You have been killed by " + src.getName()));
        }
        else
        {
            if(src instanceof Player)
            {
                Player player = (Player) src;
                HealthData data = player.getHealthData();
                Value<Double> newHealth = data.health().set(0d);
                data.set(newHealth);
                player.offer(data);
                Utils.setLastDeathLocation(player.getUniqueId(), player.getLocation());
                src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Killed yourself."));
            }
            else
            {
                src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You cannot kill yourself, you are not a player!"));
            }
        }

        return CommandResult.success();
    }
}