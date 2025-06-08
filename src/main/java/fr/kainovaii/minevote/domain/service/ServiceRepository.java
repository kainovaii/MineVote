package fr.kainovaii.minevote.domain.service;

import fr.kainovaii.minevote.MineVote;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

public class ServiceRepository
{
    public ServiceRepository() {
        Service service = new Service();
    }

    public void create(String uuid, String name)
    {
        Service service = new Service();
        service.set("uuid", uuid, "name", name, "voting", 0, "bank", 0);
        service.saveIt();
        MineVote.getInstance().getLogger().info("Inséré : " + name);
    }
}
