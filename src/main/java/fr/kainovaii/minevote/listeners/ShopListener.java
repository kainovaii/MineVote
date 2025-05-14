package fr.kainovaii.minevote.listeners;

import fr.kainovaii.minevote.events.PlayerBuyItemEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ShopListener implements Listener
{
    @EventHandler
    public void onPlayerBuy(PlayerBuyItemEvent event)
    {
        Player player = event.getPlayer();
        String itemId = event.getItemId();
        int itemPrice = event.getItemPrice();
    }
}
