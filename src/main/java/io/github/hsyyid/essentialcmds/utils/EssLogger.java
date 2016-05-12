package io.github.hsyyid.essentialcmds.utils;

import io.github.hsyyid.essentialcmds.PluginInfo;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

public class EssLogger {

	public static Text toText(String str){
    	return TextSerializers.FORMATTING_CODE.deserialize(str);
    }
	
	
	//info
	public void info(String string) {
		Sponge.getServer().getConsole().sendMessage(toText("["+PluginInfo.NAME+"]: "+string));
	}
	
	//error
	public void error(String string) {
		Sponge.getServer().getConsole().sendMessage(toText("["+PluginInfo.NAME+"]: &c"+string));
	}

}
