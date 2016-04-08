/*
 * This file is part of EssentialCmds, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2015 - 2016 HassanS6000
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

import io.github.hsyyid.essentialcmds.internal.AsyncCommandExecutorBase;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.spongepowered.api.text.action.TextActions;
import javax.annotation.Nonnull;


public class BroadcastExecutor extends AsyncCommandExecutorBase
{

	@Override
	public void executeAsync(CommandSource src, CommandContext ctx) {
			String message = ctx.<String> getOne("message").get();
			Text msg = TextSerializers.formattingCode('&').deserialize(message);
			Text broadcast = Text.of(TextColors.DARK_GRAY, "[", TextColors.DARK_RED, "Broadcast", TextColors.DARK_GRAY, "]", TextColors.GREEN, " ");
			Text finalBroadcast = Text.builder().append(broadcast).append(msg).build();
			String mssge = Text.of(message).toPlain();
			
			if ((message.contains("https://")) || (message.contains("http://"))){
			
			List<String> extractedUrls = extractUrls(message);
				for (String url : extractedUrls){
			List<String> extractmb = extractmbefore(mssge);
				for (String preurl : extractmb) {
			List<String> extractma = extractmafter(mssge);
					for (String posturl : extractma) {
						try {
							String preurlline = preurl.replaceAll("(((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*).*?)(?=\\w).*","");
							String posturlline = posturl.replaceAll("((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)","");
							Text Postmessage= Text.builder(posturlline).build();
							Text Premessage = Text.builder(preurlline).build();
							Text linkmessage = Text.builder(url).onClick(TextActions.openUrl(new URL(url))).build();
							Text newmessage = Text.builder().append(Premessage).append(linkmessage).append(Postmessage).build();
							Text Finallinkmessage = Text.builder().append(broadcast).append(newmessage).build();
							MessageChannel.TO_ALL.send(Finallinkmessage);}
						catch (MalformedURLException e) {
							e.printStackTrace();}}}}}
    		else  {
    				MessageChannel.TO_ALL.send(finalBroadcast);
    				}
				}

	public static List<String> extractUrls(String text) {
					List<String> containedUrls = new ArrayList<String>();
					String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
					Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
					Matcher urlMatcher = pattern.matcher(text);					
			while (urlMatcher.find())
				{
				containedUrls.add(text.substring(urlMatcher.start(0),
				urlMatcher.end(0)));
			}
			return containedUrls;
	}

	public static List<String> extractmbefore(String pretext) {
			List<String> preurlmess = new ArrayList<String>();
			String preurlRegex = ".*((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)(.*)";
			Pattern prepattern = Pattern.compile(preurlRegex, Pattern.CASE_INSENSITIVE| Pattern.MULTILINE);
			Matcher preMessage = prepattern.matcher(pretext);
			while (preMessage.find()) {
				preurlmess.add(pretext.substring(preMessage.start(0),
				preMessage.end(0)));
			}
			return preurlmess;
	}

	public static List<String> extractmafter(String posttext) {
			List<String> posturlmess = new ArrayList<String>();
			String posturlRegex = "(((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]).*)";
			Pattern postpattern = Pattern.compile(posturlRegex, Pattern.CASE_INSENSITIVE| Pattern.MULTILINE);
			Matcher postMessage = postpattern.matcher(posttext);
			while (postMessage.find()) {
						posturlmess.add(posttext.substring(postMessage.start(0),
								postMessage.end(0)));
			}
			return posturlmess;
	}

	@Nonnull
	@Override
	public String[] getAliases() {
			return new String[] { "broadcast", "bc" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder().description(Text.of("Broadcast Command")).permission("essentialcmds.broadcast.use")
		.arguments(GenericArguments.remainingJoinedStrings(Text.of("message"))).executor(this).build();
	}	
}
