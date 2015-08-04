package io.github.hsyyid.spongeessentialcmds.utils;

import org.spongepowered.api.entity.player.Player;

public class AFK
{
	public Player player;
	public long lastMovementTime;
	public boolean AFK = false;
	public boolean messaged = false;
	
	public AFK(Player player, long lastMovementTime)
	{
		this.player = player;
		this.lastMovementTime = lastMovementTime;
	}
	
	public Player getPlayer()
	{
		return this.player;
	}
	
	public boolean getAFK()
	{
		return AFK;
	}
	
	public void setAFK(boolean AFK)
	{
		this.AFK = AFK;
	}
	
	public boolean getMessaged()
	{
		return messaged;
	}
	
	public void setMessaged(boolean messaged)
	{
		this.messaged = messaged;
	}
	
	public long getLastMovementTime()
	{
		return lastMovementTime;
	}
}
