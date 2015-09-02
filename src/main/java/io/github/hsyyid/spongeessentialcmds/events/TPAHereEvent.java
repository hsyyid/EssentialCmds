package io.github.hsyyid.spongeessentialcmds.events;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.AbstractEvent;
import org.spongepowered.api.event.Cancellable;

public class TPAHereEvent extends AbstractEvent implements Cancellable
{
   private boolean cancelled = false;

   private Player sender;
   private Player recipient;

   public Player getSender()
   {
      return sender;
   }

   public Player getRecipient()
   {
      return recipient;
   }

   public boolean isCancelled()
   {
      return cancelled;
   }

   public void setCancelled(boolean cancel)
   {
      cancelled = cancel;
   }

   public TPAHereEvent(Player sender, Player recipient)
   {
      this.sender = sender;
      this.recipient = recipient;
   }
}
