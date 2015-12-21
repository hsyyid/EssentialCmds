package io.github.hsyyid.essentialcmds.cmdexecutors;

import io.github.hsyyid.essentialcmds.PluginInfo;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

public class EssentialCmdsExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
	{
		src.sendMessage(Texts.of(TextColors.GOLD, "[EssentialCmds]: ", TextColors.GRAY, "Version: ", TextColors.GREEN, Sponge.getPluginManager().getPlugin(PluginInfo.ID).get().getVersion()));
		return CommandResult.success();
	}
}
