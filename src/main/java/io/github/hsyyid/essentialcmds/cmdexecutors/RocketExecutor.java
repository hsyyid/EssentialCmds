/*
 * This file is part of EssentialCmds, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2015 - 2015 HassanS6000
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.github.hsyyid.essentialcmds.cmdexecutors;

import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Optional;

public class RocketExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Player> target = ctx.<Player> getOne("target");
		Optional<String> targets = ctx.<String> getOne("targets");
		
		if (!target.isPresent() && !targets.isPresent())
		{
			if (src instanceof Player)
			{
				Player player = (Player) src;
				Vector3d velocity = null;

				if (ctx.hasAny("hard"))
				{
					velocity = new Vector3d(0, 4, 0);
				}
				else
				{
					velocity = new Vector3d(0, 2, 0);
				}

				player.offer(Keys.VELOCITY, velocity);
				player.sendMessage(Text.of(TextColors.YELLOW, "Rocketed!"));
			}
		}
		else if(targets.isPresent() && src.hasPermission("essentialcmds.rocket.others"))
		{
			String targ = targets.get();
			
			if(targ.equalsIgnoreCase("a") || targ.equalsIgnoreCase("@a"))
			{
				for(Player player : Sponge.getServer().getOnlinePlayers())
				{
					Vector3d velocity = null;

					if (ctx.hasAny("hard"))
					{
						velocity = new Vector3d(0, 4, 0);
					}
					else
					{
						velocity = new Vector3d(0, 2, 0);
					}

					player.offer(Keys.VELOCITY, velocity);
					player.sendMessage(Text.of(TextColors.YELLOW, "You've been rocketed by " + src.getName() + "."));
				}
			}
			else
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Invalid argument supplied!"));
			}
		}
		else if(target.isPresent() && src.hasPermission("essentialcmds.rocket.others"))
		{
			Player player = target.get();
			Vector3d velocity = null;

			if (ctx.hasAny("hard"))
			{
				velocity = new Vector3d(0, 4, 0);
			}
			else
			{
				velocity = new Vector3d(0, 2, 0);
			}

			player.offer(Keys.VELOCITY, velocity);
			player.sendMessage(Text.of(TextColors.YELLOW, "You've been rocketed by " + src.getName() + "."));
		}
		else
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You do not have permission to rocket other players!"));
		}

		return CommandResult.success();
	}
}
