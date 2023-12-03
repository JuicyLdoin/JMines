package net.ldoin.jmines.util.util;

import net.ldoin.jmines.util.VersionManager;
import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtil {

    public String makeColor(String str) {

        if (str == null)
            return null;

        Pattern unicode = Pattern.compile("\\\\u\\+[a-fA-F0-9]{4}");
        Matcher match = unicode.matcher(str);

        while (match.find()) {

            String code = str.substring(match.start(), match.end());
            str = str.replace(code, Character.toString((char) Integer.parseInt(code.replace("\\u+",""),16)));

            match = unicode.matcher(str);

        }

        if (VersionManager.isHigherThan(15))
            try {

                Pattern pattern = Pattern.compile("&#[a-fA-F0-9]{6}");
                match = pattern.matcher(str);

                while (match.find()) {

                    String color = str.substring(match.start(), match.end());

                    str = str.replace(color, ChatColor.class.getMethod("of", String.class).invoke(ChatColor.class, color.replace("&", "")) + "");
                    match = pattern.matcher(str);

                }
            } catch (Exception exception) {

                exception.printStackTrace();

            }

        return ChatColor.translateAlternateColorCodes('&', str);

    }
}