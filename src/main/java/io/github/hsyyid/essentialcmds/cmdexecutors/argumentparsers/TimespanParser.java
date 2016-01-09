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

import com.google.common.collect.Lists;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses an argument and tries to get a timespan.
 *
 * This parser is teken from
 * https://github.com/dualspiral/Hammer/blob/master/HammerCore/src/main/java/uk/co/drnaylor/minecraft/hammer/core/commands/parsers/TimespanParser.java
 */
public class TimespanParser extends CommandElement {
    private final Pattern timeString = Pattern.compile("^((\\d+)w)?((\\d+)d)?((\\d+)h)?((\\d+)m)?((\\d+)s)?$");

    private final int secondsInMinute = 60;
    private final int secondsInHour = 60 * secondsInMinute;
    private final int secondsInDay = 24 * secondsInHour;
    private final int secondsInWeek = 7 * secondsInDay;

    public TimespanParser(@Nullable Text key) {
        super(key);
    }

    @Nullable
    @Override
    protected Object parseValue(CommandSource source, CommandArgs args) throws ArgumentParseException {
        if (!args.hasNext()) {
            throw args.createError(Text.of("No time was specified"));
        }

        String s = args.next();
        Matcher m = timeString.matcher(s);
        if (m.matches()) {
            long time = amount(m.group(2), secondsInWeek);
            time += amount(m.group(4), secondsInDay);
            time += amount(m.group(6), secondsInHour);
            time += amount(m.group(8), secondsInMinute);
            time += amount(m.group(10), 1);

            if (time > 0) {
                return time;
            }
        }

        throw args.createError(Text.of("Could not parse " + s + " - must use w, d, h, m and/or s in that order."));
    }

    private int amount(String g, int multipler) {
        if (g != null && g.length() > 0) {
            return multipler * Integer.parseInt(g);
        }

        return 0;
    }

    @Override
    public List<String> complete(CommandSource src, CommandArgs args, CommandContext context) {
        return Lists.newArrayList();
    }
}
