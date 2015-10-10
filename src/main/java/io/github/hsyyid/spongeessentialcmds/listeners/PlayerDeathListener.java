package io.github.hsyyid.spongeessentialcmds.listeners;

import io.github.hsyyid.spongeessentialcmds.utils.Utils;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;

public class PlayerDeathListener
{
	@Listener
	public void onPlayerDeath(DestructEntityEvent event)
	{
		if (event.getTargetEntity() instanceof Player)
		{
			Player died = (Player) event.getTargetEntity();
			Utils.setLastDeathLocation(died.getUniqueId(), died.getLocation());
		}
	}
}
