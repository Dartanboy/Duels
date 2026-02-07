package me.dartanman.duels.utils;

import me.dartanman.duels.Duels;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;

public class UpdateChecker {

    public static void updateCheck(Duels plugin, CommandSender sender, boolean sendGoodMessage) {
        try {
            URL url = new URL("https://api.spigotmc.org/legacy/update.php?resource=44820");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String str;
            if ((str = br.readLine()) != null) {
                if (plugin.getDescription().getVersion().equals(str)) {
                    if (sendGoodMessage) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                Objects.requireNonNull(plugin.getConfig().getString("Messages.Update-Check-Good"))
                                        .replace("<version>", plugin.getDescription().getVersion()))
                        );
                    }
                    br.close();
                    return;
                }

                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(plugin.getConfig().getString("Messages.Update-Check-Bad"))
                                .replace("<version>", plugin.getDescription().getVersion()))
                );
                br.close();
                return;
            }

            br.close();
        } catch (IOException var9) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(plugin.getConfig().getString("Messages.Update-Check-Failed")))
            );
            var9.printStackTrace(System.err);
        }
    }

}
