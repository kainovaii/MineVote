package fr.kainovaii.minevote.domain.transaction;

import fr.kainovaii.minevote.MineVote;

public class TransactionRepository
{
    private Transaction transaction;

    public TransactionRepository() {
        transaction = new Transaction();
    }

    public void create(double amount, String reason)
    {
        transaction.set("amount", amount, "reason", reason).saveIt();
        MineVote.getInstance().getLogger().info("New transaction: " + amount);
    }
}
