package io.github.hsyyid.spongeessentialcmds.listeners;

import io.github.hsyyid.spongeessentialcmds.Main;
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
			if (Main.recentlyJoined.contains(player))
			{
				Main.recentlyJoined.remove(player);
				AFK removeAFK = null;
				for (AFK a : Main.movementList)
				{
					if (a.getPlayer() == a.getPlayer())
					{
						removeAFK = a;
						break;
					}
				}
				if (removeAFK != null)
				{
					Main.movementList.remove(removeAFK);
				}
			}
			else
			{
				AFK afk = new AFK(player, System.currentTimeMillis());
				AFK removeAFK = null;
				for (AFK a : Main.movementList)
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
						for (Player p : Main.game.getServer().getOnlinePlayers())
						{
							p.sendMessage(Texts.of(TextColors.BLUE, player.getName(), TextColors.GOLD, " is no longer AFK."));
						}
						Main.movementList.remove(removeAFK);
					}
					else if (!removeAFK.getAFK())
					{
						Main.movementList.remove(removeAFK);
						Main.movementList.add(afk);
					}
				}
				else
				{
					Main.movementList.add(afk);
				}
			}
		}
	}

}
