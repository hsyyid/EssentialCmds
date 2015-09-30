package io.github.hsyyid.spongeessentialcmds.cmdexecutors;

import com.google.common.base.Optional;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class HatExecutor implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if(src instanceof Player) {
			Player player = (Player) src;
			Optional<ItemStack> itemInHand = player.getItemInHand();
			if (itemInHand.isPresent()) {
				player.setHelmet(itemInHand.get());
				itemInHand.get().setQuantity(itemInHand.get().getQuantity() - 1);
				if(itemInHand.get().getQuantity() > 0)//Sponge derped up itemstack verification
					player.setItemInHand(itemInHand.get());
				else
					player.setItemInHand(null);
			} else {
				player.sendMessage(Texts.of("No item selected in hotbar."));
				return CommandResult.empty();
			}
		}
		return CommandResult.success();
	}

}
