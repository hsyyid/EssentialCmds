package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class WhoisExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Player player = ctx.<Player>getOne("player").get();

		src.sendMessage(Texts.of(TextColors.GOLD, "Real Name: ", TextColors.GRAY, player.getName()));
		src.sendMessage(Texts.of(TextColors.GOLD, "UUID: ", TextColors.GRAY, player.getUniqueId().toString()));
		src.sendMessage(Texts.of(TextColors.GOLD, "Current World: ", TextColors.GRAY, player.getWorld().getName()));

		return CommandResult.success();
	}
}