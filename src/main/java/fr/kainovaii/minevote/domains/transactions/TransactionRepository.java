package fr.kainovaii.minevote.domains.transactions;

import fr.kainovaii.minevote.MineVote;
import fr.kainovaii.minevote.domains.voter.Voter;

public class TransactionRepository
{
    private Transaction transaction;

    public TransactionRepository() {
        transaction = new Transaction();
    }
        public void create(double amount, String reason) {
        transaction.set("amount", amount, "reason", reason).saveIt();
        MineVote.getInstance().getLogger().info("New transaction: " + amount);
    }
}
