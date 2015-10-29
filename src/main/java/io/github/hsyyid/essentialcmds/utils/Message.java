package io.github.hsyyid.essentialcmds.utils;

import org.spongepowered.api.entity.living.player.Player;

public class Message
{
	private Player sender;
	private Player recipient;
	private String message;

	public Message(Player sender, Player recipient, String message)
	{
		this.sender = sender;
		this.recipient = recipient;
		this.message = message;
	}

	public Player getSender()
	{
		return sender;
	}

	public Player getRecipient()
	{
		return recipient;
	}

	public String getMessage()
	{
		return message;
	}
}
