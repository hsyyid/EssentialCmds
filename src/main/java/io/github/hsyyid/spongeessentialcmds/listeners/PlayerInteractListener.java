package io.github.hsyyid.spongeessentialcmds.listeners;

import io.github.hsyyid.spongeessentialcmds.SpongeEssentialCmds;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.service.command.CommandService;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;

public class PlayerInteractListener
{
	@Listener
	public void onPlayerInteractBlock(InteractBlockEvent event)
	{
		if (event.getCause().first(Player.class).isPresent())
		{
			Player player = event.getCause().first(Player.class).get();

			if(SpongeEssentialCmds.frozenPlayers.contains(player.getUniqueId()))
			{
				player.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You cannot interact while frozen."));
				event.setCancelled(true);
				return;
			}
			
			Location<World> location = event.getTargetBlock().getLocation().get();

			if (location.getTileEntity().isPresent() && location.getTileEntity().get() != null && location.getTileEntity().get().getType() != null)
			{
				TileEntity clickedEntity = location.getTileEntity().get();

				if (event.getTargetBlock().getState().getType().equals(BlockTypes.STANDING_SIGN)
					|| event.getTargetBlock().getState().getType().equals(BlockTypes.WALL_SIGN))
				{
					Optional<SignData> signData = clickedEntity.getOrCreate(SignData.class);

					if (signData.isPresent())
					{
						SignData data = signData.get();
						CommandService cmdService = SpongeEssentialCmds.game.getCommandDispatcher();
						String line0 = Texts.toPlain(data.getValue(Keys.SIGN_LINES).get().get(0));
						String line1 = Texts.toPlain(data.getValue(Keys.SIGN_LINES).get().get(1));
						String command = "warp " + line1;

						if (line0.equals("[Warp]"))
						{
							if (player.hasPermission("warps.use.sign"))
							{
								cmdService.process(player, command);
							}
							else
							{
								player.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED,
									"You do not have permission to use Warp Signs!"));
							}
						}
					}
				}
			}
		}
	}
}
