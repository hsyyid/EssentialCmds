package io.github.hsyyid.spongeessentialcmds.listeners;

import io.github.hsyyid.spongeessentialcmds.SpongeEssentialCmds;
import io.github.hsyyid.spongeessentialcmds.utils.AFK;
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
			
			if(SpongeEssentialCmds.frozenPlayers.contains(player.getUniqueId()))
			{
				player.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You cannot move while frozen."));
				event.setCancelled(true);
				return;
			}
			
			if (SpongeEssentialCmds.recentlyJoined.contains(player))
			{
				SpongeEssentialCmds.recentlyJoined.remove(player);
				AFK removeAFK = null;
				for (AFK a : SpongeEssentialCmds.movementList)
				{
					if (a.getPlayer() == a.getPlayer())
					{
						removeAFK = a;
						break;
					}
				}
				if (removeAFK != null)
				{
					SpongeEssentialCmds.movementList.remove(removeAFK);
				}
			}
			else
			{
				AFK afk = new AFK(player, System.currentTimeMillis());
				AFK removeAFK = null;
				for (AFK a : SpongeEssentialCmds.movementList)
				{
					if (a.getPlayer() == a.getPlayer())
					{
						removeAFK = a;
						break;
					}
				}

				if (removeAFK != null)
				{
					if (removeAFK.getAFK())
					{
						for (Player p : SpongeEssentialCmds.game.getServer().getOnlinePlayers())
						{
							p.sendMessage(Texts.of(TextColors.BLUE, player.getName(), TextColors.GOLD, " is no longer AFK."));
						}
						SpongeEssentialCmds.movementList.remove(removeAFK);
					}
					else if (!removeAFK.getAFK())
					{
						SpongeEssentialCmds.movementList.remove(removeAFK);
						SpongeEssentialCmds.movementList.add(afk);
					}
				}
				else
				{
					SpongeEssentialCmds.movementList.add(afk);
				}
			}
		}
	}

}
