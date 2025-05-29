package fr.kainovaii.minevote.gui.main;

import fr.kainovaii.minevote.domain.voter.VoterRepository;
import fr.kainovaii.minevote.utils.gui.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

import java.util.ArrayList;
import java.util.List;

public class RankingPage
{
    public RankingPage(MainGui gui, Player player)
    {
        LazyList<Model> voters = VoterRepository.getVoters();
        List<Model> sortedVoters = new ArrayList<>(voters);
        sortedVoters.sort((a, b) -> {
            int voteA = a.getInteger("voting");
            int voteB = b.getInteger("voting");
            if (voteA != voteB) {
                return Integer.compare(voteB, voteA);
            }
            int bankA = a.getInteger("bank");
            int bankB = b.getInteger("bank");
            return Integer.compare(bankB, bankA);
        });

        int slot = 10;
        for (Model voter : sortedVoters) {
            if (slot > 16) break;

            String name = voter.getString("name");
            OfflinePlayer offline = Bukkit.getOfflinePlayer(name);

            gui.setItem(slot, new ItemBuilder(GuiUtils.playerHead(offline)).name("§6" + offline.getName()).build());
            slot++;
        }

        GuiUtils.arrowBack(player, gui, 0);
    }
}
