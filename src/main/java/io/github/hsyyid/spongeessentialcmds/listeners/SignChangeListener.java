package io.github.hsyyid.spongeessentialcmds.listeners;

import io.github.hsyyid.spongeessentialcmds.utils.Utils;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.tileentity.ChangeSignEvent;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

public class SignChangeListener
{
	@Listener
	public void onSignChange(ChangeSignEvent event)
	{
		if (event.getCause().first(Player.class).isPresent())
		{
			Player player = event.getCause().first(Player.class).get();
			SignData signData = event.getText();

			if (signData.getValue(Keys.SIGN_LINES).isPresent())
			{
				String line0 = Texts.toPlain(signData.getValue(Keys.SIGN_LINES).get().get(0));
				String line1 = Texts.toPlain(signData.getValue(Keys.SIGN_LINES).get().get(1));
				String line2 = Texts.toPlain(signData.getValue(Keys.SIGN_LINES).get().get(2));
				String line3 = Texts.toPlain(signData.getValue(Keys.SIGN_LINES).get().get(3));

				if (line0.equals("[Warp]"))
				{
					if (Utils.getWarps().contains(line1))
					{
						signData = signData.set(signData.getValue(Keys.SIGN_LINES).get().set(0, Texts.of(TextColors.DARK_BLUE, "[Warp]")));
					}
					else
					{
						signData = signData.set(signData.getValue(Keys.SIGN_LINES).get().set(0, Texts.of(TextColors.DARK_RED, "[Warp]")));
					}
				}
				else if (player != null && player.hasPermission("color.sign.use"))
				{
					signData = signData.set(signData.getValue(Keys.SIGN_LINES).get().set(0, Texts.of(line0.replaceAll("&", "\u00A7"))));
					signData = signData.set(signData.getValue(Keys.SIGN_LINES).get().set(1, Texts.of(line1.replaceAll("&", "\u00A7"))));
					signData = signData.set(signData.getValue(Keys.SIGN_LINES).get().set(2, Texts.of(line2.replaceAll("&", "\u00A7"))));
					signData = signData.set(signData.getValue(Keys.SIGN_LINES).get().set(3, Texts.of(line3.replaceAll("&", "\u00A7"))));
				}
			}
		}
	}
}
