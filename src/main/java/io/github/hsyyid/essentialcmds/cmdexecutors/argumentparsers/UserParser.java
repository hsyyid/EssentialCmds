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
package io.github.hsyyid.essentialcmds.cmdexecutors.argumentparsers;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

/**
 * Parses an argument and tries to match it up against any user, online or offline.
 */
public class UserParser extends CommandElement
{
	public UserParser(@Nullable Text key)
	{
		super(key);
	}

	@Nullable
	@Override
	protected Object parseValue(CommandSource source, CommandArgs args) throws ArgumentParseException
	{
		String user = args.next();

		// Check for exact match from the GameProfile service first.
		UserStorageService uss = Sponge.getGame().getServiceManager().provide(UserStorageService.class).get();
		Optional<GameProfile> ogp = uss.getAll().stream().filter(f -> f.getName().isPresent() && f.getName().get()
			.equalsIgnoreCase(user)).findFirst();
		if (ogp.isPresent())
		{
			Optional<User> retUser = uss.get(ogp.get());
			if (retUser.isPresent())
			{
				return retUser.get();
			}
		}

		// No match. Check against all online players only.
		List<User> listUser = Sponge.getGame().getServer().getOnlinePlayers().stream()
			.filter(x -> x.getName().toLowerCase().startsWith(user.toLowerCase())).collect(Collectors.toList());
		if (listUser.isEmpty())
		{
			throw args.createError(Text.of(TextColors.RED, "Could not find user with the name " + user));
		}

		if (listUser.size() == 1)
		{
			return listUser.get(0);
		}

		return listUser;
	}

	@Override
	public List<String> complete(CommandSource src, CommandArgs args, CommandContext context)
	{
		String peek;
		try
		{
			peek = args.peek();
		}
		catch (ArgumentParseException e)
		{
			return Sponge.getGame().getServer().getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
		}

		return Sponge.getGame().getServer().getOnlinePlayers().stream().filter(x -> x.getName().toLowerCase().startsWith(peek))
			.map(Player::getName).collect(Collectors.toList());
	}
}
