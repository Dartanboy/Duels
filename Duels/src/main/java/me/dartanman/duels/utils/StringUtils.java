package me.dartanman.duels.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.md_5.bungee.api.ChatColor;

public class StringUtils
{

    public static String color(String toColor)
    {
	// & codes
	toColor = ChatColor.translateAlternateColorCodes('&', toColor);

	// hex codes
	final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
	Matcher match = pattern.matcher(toColor);
	while (match.find())
	{
	    String color = toColor.substring(match.start(), match.end());
	    toColor = toColor.replace(color, ChatColor.of(color) + "");
	    match = pattern.matcher(toColor);
	}
	return toColor;
    }

}