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

import co.aikar.timings.Timing;
import io.github.hsyyid.essentialcmds.EssentialCmds;

public class Timings
{
	private final EssentialCmds plugin;
	private final Timing firstJoin, safeLogin, mail, saveInventory, loginMessage, afk, teleportPlayer, teleportCooldown;

	public Timings(EssentialCmds plugin)
	{
		this.plugin = plugin;
		this.firstJoin = timing("firstJoin");
		this.safeLogin = timing("safeLogin");
		this.mail = timing("getMail");
		this.saveInventory = timing("saveInventory");
		this.loginMessage = timing("loginMessage");
		this.teleportPlayer = timing("teleportPlayer");
		this.teleportCooldown = timing("teleportCooldown");
		this.afk = timing("afk");
	}

	private Timing timing(String key)
	{
		return co.aikar.timings.Timings.of(this.plugin, key);
	}

	public Timing firstJoin()
	{
		return firstJoin;
	}

	public Timing safeLogin()
	{
		return safeLogin;
	}

	public Timing getMail()
	{
		return mail;
	}

	public Timing saveInventory()
	{
		return saveInventory;
	}

	public Timing loginMessage()
	{
		return loginMessage;
	}

	public Timing afk()
	{
		return afk;
	}

	public Timing teleportPlayer()
	{
		return teleportPlayer;
	}

	public Timing teleportCooldown()
	{
		return teleportCooldown;
	}
}
