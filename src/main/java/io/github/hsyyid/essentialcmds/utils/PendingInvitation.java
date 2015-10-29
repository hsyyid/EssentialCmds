package io.github.hsyyid.essentialcmds.utils;

import org.spongepowered.api.entity.living.player.Player;

public class PendingInvitation
{
	public boolean isTPAHere = false;
	public Player sender;
	public Player recipient;

	public PendingInvitation(Player sender, Player recipient)
	{
		this.sender = sender;
		this.recipient = recipient;
	}

	public Player getSender()
	{
		return sender;
	}

	public Player getRecipient()
	{
		return recipient;
	}
}
