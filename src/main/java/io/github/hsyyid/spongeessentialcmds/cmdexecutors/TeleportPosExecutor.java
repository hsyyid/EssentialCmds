package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import com.google.common.base.Optional;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class TeleportPosExecutor implements CommandExecutor
{
    public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
    {
        Optional<Player> p = ctx.<Player>getOne("player");
        int x = ctx.<Integer>getOne("x").get();
        int y = ctx.<Integer>getOne("y").get();
        int z = ctx.<Integer>getOne("z").get();

        if(p.isPresent())
        {
            if(src.hasPermission("teleport.pos.others"))
            {
                p.get().setLocation(new Location<World>(p.get().getWorld(), x, y, z));
                src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported player " + p.get().getName() + " to " + x + "," + y + "," + z + "." ));
                p.get().sendMessage(Texts.of(TextColors.GOLD, "You have been teleported by " + src.getName()));
            }
        }
        else
        {
            if(src instanceof Player)
            {
                Player player = (Player) src;
                player.setLocation(new Location<World>(player.getWorld(), x, y, z));
                src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported to coords."));
            }
            else
            {
                src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You cannot teleport, you are not a player!"));
            }
        }

        return CommandResult.success();
    }
}