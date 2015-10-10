package io.github.hsyyid.spongeessentialcmds.utils;

public class Mail
{
	public String recipientName;
	public String senderName;
	public String message;

	public Mail(String recipientName, String senderName, String message)
	{
		this.recipientName = recipientName;
		this.senderName = senderName;
		this.message = message;
	}

	public void setRecipientName(String recipientName)
	{
		this.recipientName = recipientName;
	}

	public void setSenderName(String senderName)
	{
		this.senderName = senderName;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getRecipientName()
	{
		return recipientName;
	}

	public String getSenderName()
	{
		return senderName;
	}

	public String getMessage()
	{
		return message;
	}
}
