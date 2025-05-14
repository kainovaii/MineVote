package fr.kainovaii.minevote.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerBuyItemEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final String itemId;
    private final int itemPrice;
    private boolean cancelled = false;

    public PlayerBuyItemEvent(Player player, String itemId, int itemPrice) {
        this.player = player;
        this.itemId = itemId;
        this.itemPrice = itemPrice;
    }

    public Player getPlayer() {
        return player;
    }

    public String getItemId() {
        return itemId;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}