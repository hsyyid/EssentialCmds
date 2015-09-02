package io.github.hsyyid.spongeessentialcmds.events;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.AbstractEvent;
import org.spongepowered.api.event.Cancellable;

public class MailSendEvent extends AbstractEvent implements Cancellable
{
   private boolean cancelled = false;

   private Player sender;
   private String recipientName;
   private String message;

   public Player getSender()
   {
      return sender;
   }

   public String getRecipientName()
   {
      return recipientName;
   }

   public boolean isCancelled()
   {
      return cancelled;
   }

   public String getMessage()
   {
	   return message;
   }
   
   public void setCancelled(boolean cancel)
   {
      cancelled = cancel;
   }

   public MailSendEvent(Player sender, String recipientName, String message)
   {
      this.sender = sender;
      this.recipientName = recipientName;
      this.message = message;
   }
}