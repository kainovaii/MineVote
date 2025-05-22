package fr.kainovaii.minevote.gui.main;

import fr.kainovaii.minevote.MineVote;
import fr.kainovaii.minevote.config.ConfigManager;
import fr.kainovaii.minevote.utils.gui.InventoryAPI;
import org.bukkit.entity.Player;

public class MainGui extends InventoryAPI
{
    private final Player player;
    private final int page;
    private final ConfigManager configManager;

    public MainGui(Player player)
    {
        this(player, 0);
    }

    public MainGui(Player player, int page)
    {
        super(27, "§8MineVote");
        this.player = player;
        this.page = page;
        this.configManager = MineVote.getInstance().getConfigManager();
        setupMenu();
    }

    private void setupMenu()
    {
        GuiUtils.borderGui(this);
        setItem(4, GuiUtils.playerHead(player));
        setItem(0, GuiUtils.voteCompass(this));

        switch (page) {
            case 0 -> new IndexPage(this, player);
            case 1 -> new SitePage(this, player);
            case 2 -> new RankingPage(this, player);
        }
    }

    public Player getPlayer()
    {
        return player;
    }

    public int getPage() {
        return page;
    }

    public ConfigManager getConfig() {
        return configManager;
    }
}