package io.github.hsyyid.essentialcmds.listeners;

import io.github.hsyyid.essentialcmds.utils.AFK;

import io.github.hsyyid.essentialcmds.EssentialCmds;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DisplaceEntityEvent;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

public class PlayerMoveListener
{
	@Listener
	public void onPlayerMove(DisplaceEntityEvent event)
	{
		if (event.getTargetEntity() instanceof Player)
		{
			Player player = (Player) event.getTargetEntity();
			
			if(EssentialCmds.frozenPlayers.contains(player.getUniqueId()))
			{
				player.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You cannot move while frozen."));
				event.setCancelled(true);
				return;
			}
			
			if (EssentialCmds.recentlyJoined.contains(player))
			{
				EssentialCmds.recentlyJoined.remove(player);
				AFK removeAFK = null;
				
				for (AFK a : EssentialCmds.movementList)
				{
					if (player.getUniqueId() == a.getPlayer().getUniqueId())
					{
						removeAFK = a;
						break;
					}
				}
				if (removeAFK != null)
				{
					EssentialCmds.movementList.remove(removeAFK);
				}
			}
			else
			{
				AFK afk = new AFK(player, System.currentTimeMillis());
				AFK removeAFK = null;
				
				for (AFK a : EssentialCmds.movementList)
				{
					if (player.getUniqueId() == a.getPlayer().getUniqueId())
					{
						removeAFK = a;
						break;
					}
				}

				if (removeAFK != null)
				{
					if (removeAFK.getAFK())
					{
						for (Player p : EssentialCmds.game.getServer().getOnlinePlayers())
						{
							p.sendMessage(Texts.of(TextColors.BLUE, player.getName(), TextColors.GOLD, " is no longer AFK."));
						}	
					}
					
					EssentialCmds.movementList.remove(removeAFK);
					EssentialCmds.movementList.add(afk);
				}
				else
				{
					EssentialCmds.movementList.add(afk);
				}
			}
		}
	}

}
