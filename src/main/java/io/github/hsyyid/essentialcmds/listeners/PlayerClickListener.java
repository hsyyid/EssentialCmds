package io.github.hsyyid.essentialcmds.listeners;

import io.github.hsyyid.essentialcmds.EssentialCmds;
import io.github.hsyyid.essentialcmds.utils.Powertool;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.action.InteractEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

public class PlayerClickListener
{
	@Listener
	public void onPlayerClick(InteractEvent event)
	{
		if (event.getCause().first(Player.class).isPresent())
		{
			Player player = event.getCause().first(Player.class).get();
			
			if(EssentialCmds.frozenPlayers.contains(player.getUniqueId()))
			{
				player.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You cannot click while frozen."));
				event.setCancelled(true);
				return;
			}
			
			Powertool foundTool = null;

			for (Powertool powertool : EssentialCmds.powertools)
			{
				if (powertool.getPlayer().equals(player))
				{
					if (player.getItemInHand().isPresent() && powertool.getItemID().equals(player.getItemInHand().get().getItem().getName()))
					{
						foundTool = powertool;
						break;
					}
				}
			}

			if (foundTool != null)
			{
				EssentialCmds.game.getCommandDispatcher().process(player, foundTool.getCommand());
			}
		}
	}
	
	@Listener
	public void onPlayerClickEntity(InteractEntityEvent event)
	{
		if (event.getCause().first(Player.class).isPresent())
		{
			Player player = event.getCause().first(Player.class).get();
			
			if(EssentialCmds.frozenPlayers.contains(player.getUniqueId()))
			{
				player.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You cannot interact with entities while frozen."));
				event.setCancelled(true);
				return;
			}
		}
	}
}
