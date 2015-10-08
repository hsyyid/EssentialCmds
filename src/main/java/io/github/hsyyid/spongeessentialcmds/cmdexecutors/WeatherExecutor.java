package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import org.spongepowered.api.world.weather.Weathers;

import org.spongepowered.api.world.weather.Weather;
import java.util.Optional;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class WeatherExecutor implements CommandExecutor
{
    public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
    {
        String weatherString = ctx.<String>getOne("weather").get();
        Optional<Integer> duration = ctx.<Integer>getOne("duration");

        if(src instanceof Player)
        {
            Player player = (Player) src;

            Weather weather = null;

            if(weatherString.toLowerCase().equals("clear") || weatherString.toLowerCase().equals("sun"))
            {
                weather = Weathers.CLEAR;
                player.sendMessage(Texts.of(TextColors.GOLD, "Changing weather to ", TextColors.GRAY, "sunny."));
            }
            else if(weatherString.toLowerCase().equals("rain"))
            {
                weather = Weathers.RAIN;
                player.sendMessage(Texts.of(TextColors.GOLD, "Changing weather to ", TextColors.GRAY, "rain."));
            }
            else if(weatherString.toLowerCase().equals("storm") || weatherString.toLowerCase().equals("thunderstorm"))
            {
                weather = Weathers.THUNDER_STORM;
                player.sendMessage(Texts.of(TextColors.GOLD, "Changing weather to ", TextColors.GRAY, "storm."));
            }
            else
            {
                src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Input invalid: " + weatherString));
                return CommandResult.success();
            }

            if(duration.isPresent())
            {
                player.getWorld().forecast(weather, duration.get());
            }
            else
            {
                player.getWorld().forecast(weather);
            }
            return CommandResult.success();
        }
        else
        {
            src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be a in-game player to do /weather!"));
            return CommandResult.success();
        }
    }
}