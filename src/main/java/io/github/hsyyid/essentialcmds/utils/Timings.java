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
