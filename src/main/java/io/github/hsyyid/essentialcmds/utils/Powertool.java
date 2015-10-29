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
