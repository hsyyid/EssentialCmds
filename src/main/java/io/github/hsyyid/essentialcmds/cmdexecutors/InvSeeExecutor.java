/*
 * This file is part of EssentialCmds, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2015 - 2015 HassanS6000
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.github.hsyyid.essentialcmds.cmdexecutors;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import io.github.hsyyid.essentialcmds.internal.CommandExecutorBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.server.MinecraftServer;

public class InvSeeExecutor extends CommandExecutorBase
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Player target = ctx.<Player> getOne("target").get();

		if (src instanceof Player)
		{
			Player player = (Player) src;
			EntityPlayerMP playerMP = MinecraftServer.getServer().getConfigurationManager().playerEntityList.stream().filter(p -> p.getUniqueID().equals(player.getUniqueId())).findAny().get();
			EntityPlayerMP targetMP = MinecraftServer.getServer().getConfigurationManager().playerEntityList.stream().filter(p -> p.getUniqueID().equals(target.getUniqueId())).findAny().get();
			InventoryPlayer inventory = targetMP.inventory;
			ContainerChest container = new ContainerChest(playerMP.inventory, inventory, playerMP);

			if (playerMP.openContainer != playerMP.inventoryContainer)
			{
				playerMP.closeScreen();
			}

			playerMP.getNextWindowId();

			playerMP.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(playerMP.currentWindowId, "minecraft:container", inventory.getDisplayName(), inventory.getSizeInventory()));
			playerMP.openContainer = container;

			playerMP.openContainer.windowId = playerMP.currentWindowId;
			playerMP.openContainer.onCraftGuiOpened(playerMP);
		}
		else
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be a player to see other inventories."));
		}

		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "invsee", "inventorysee", "invview" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder().description(Text.of("InvSee Command")).permission("essentialcmds.invsee.use").arguments(GenericArguments.onlyOne(GenericArguments.player(Text.of("target")))).executor(this).build();
	}
}
