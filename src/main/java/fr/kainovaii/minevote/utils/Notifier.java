package fr.kainovaii.minevote.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Notifier
{

    public static void broadcast(String message)
    {
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(Prefix.BASE.get() + message);
        }
    }

    public static void playerMessage(Player player, String message)
    {
        player.sendMessage(Prefix.BASE.get() + message);
    }
}
