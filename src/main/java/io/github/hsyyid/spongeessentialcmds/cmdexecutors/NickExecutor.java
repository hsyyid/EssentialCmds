package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import com.google.common.base.Optional;

public class NickExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		String nick = ctx.<String>getOne("nick").get();
		nick.replace("&", "\u00A7");
		
		if(src instanceof Player)
		{
			Player player = (Player) src;
			
			Optional<Player> p = ctx.<Player>getOne("player");
			Player targetPlayer = null;

			if(p != Optional.<Player>absent())
			{
				targetPlayer = p.get();
			}

			if(targetPlayer != null)
			{
				DisplayNameData data = targetPlayer.getOrCreate(DisplayNameData.class).get();
				Optional<Text> name = data.get(Keys.DISPLAY_NAME);

				if(name.isPresent())
				{
					Text newName = Texts.of(name.get().toString().replace(player.getName(), nick));
					data.set(Keys.DISPLAY_NAME, Texts.of(newName));
				}
				else
				{
					data.set(Keys.DISPLAY_NAME, Texts.of(nick));
				}

				targetPlayer.offer(data);
				player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Nick successfully set!"));

				return CommandResult.success();
			}
			else
			{
				DisplayNameData data = player.getOrCreate(DisplayNameData.class).get();
				Optional<Text> name = data.get(Keys.DISPLAY_NAME);

				if(name.isPresent())
				{
					Text newName = Texts.of(name.get().toString().replace(player.getName(), nick));
					data.set(Keys.DISPLAY_NAME, Texts.of(newName));
				}
				else
				{
					data.set(Keys.DISPLAY_NAME, Texts.of(nick));
				}
				
				player.offer(data);
				player.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Nick successfully set!"));

				return CommandResult.success();
			}
		}
		else
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be a in-game player to do /nick!"));
			return CommandResult.success();
		}
	}
}