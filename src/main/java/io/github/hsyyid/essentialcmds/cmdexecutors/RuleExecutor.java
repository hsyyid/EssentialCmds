package io.github.hsyyid.essentialcmds.cmdexecutors;

import io.github.hsyyid.essentialcmds.utils.Utils;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import java.util.ArrayList;

public class RuleExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		ArrayList<String> rules = Utils.getRules();
		
		for(String rule : rules)
		{
			src.sendMessage(Texts.of(TextColors.GRAY, (rules.indexOf(rule) + 1) + ". ", TextColors.GOLD, rule));
		}

		return CommandResult.success();
	}
}
