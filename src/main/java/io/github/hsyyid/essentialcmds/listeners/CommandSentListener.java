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
package io.github.hsyyid.essentialcmds.listeners;

import io.github.hsyyid.essentialcmds.EssentialCmds;
import io.github.hsyyid.essentialcmds.utils.Utils;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.filter.cause.First;

public class CommandSentListener
{
	@Listener
	public void onCommandSent(SendCommandEvent event, @First CommandSource src)
	{
		if (src instanceof Player && Utils.isPlayerCommandLoggingEnabled())
		{
			EssentialCmds.getEssentialCmds().getLogger().info("[" + src.getName() + "]" + " executed command " + event.getCommand() + " " + event.getArguments());
		}
		else if (src instanceof ConsoleSource && Utils.isConsoleCommandLoggingEnabled())
		{
			EssentialCmds.getEssentialCmds().getLogger().info("[" + src.getName() + "]" + " executed command " + event.getCommand() + " " + event.getArguments());
		}
		else if (src instanceof CommandBlockSource && Utils.isCommandBlockCommandLoggingEnabled())
		{
			EssentialCmds.getEssentialCmds().getLogger().info("[" + src.getName() + "]" + " executed command " + event.getCommand() + " " + event.getArguments());
		}
		else if (Utils.isOtherCommandLoggingEnabled())
		{
			EssentialCmds.getEssentialCmds().getLogger().info("[" + src.getName() + "]" + " executed command " + event.getCommand() + " " + event.getArguments());
		}
	}
}
