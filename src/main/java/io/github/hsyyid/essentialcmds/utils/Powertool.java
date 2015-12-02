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
package io.github.hsyyid.essentialcmds.utils;

import org.spongepowered.api.entity.living.player.Player;

public class Powertool
{
	private Player player;
	private String itemID;
	private String command;

	public Powertool(Player player, String itemID, String command)
	{
		this.player = player;
		this.itemID = itemID;
		this.command = command;
	}

	public void setPlayer(Player player)
	{
		this.player = player;
	}

	public Player getPlayer()
	{
		return player;
	}

	public void setItemID(String itemID)
	{
		this.itemID = itemID;
	}

	public String getItemID()
	{
		return this.itemID;
	}

	public void setCommand(String command)
	{
		this.command = command;
	}

	public String getCommand()
	{
		return this.command;
	}
}
