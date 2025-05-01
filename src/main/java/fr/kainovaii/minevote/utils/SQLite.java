package fr.kainovaii.minevote.utils;

import fr.kainovaii.minevote.MineVote;
import org.bukkit.plugin.Plugin;
import org.javalite.activejdbc.Base;

import java.io.File;

public class SQLite
{
    private SQLite instance;

    public SQLite() {
        instance = this;
    }

    public void connectDatabase() {
        try {
            File dbFile = new File(MineVote.getInstance().getDataFolder(), "data.db");
            MineVote.getInstance().getDataFolder().mkdirs();
            String url = "jdbc:sqlite:" + dbFile.getAbsolutePath();
            Base.open("org.sqlite.JDBC", url, "", "");
            MineVote.getInstance().getLogger().info("Connexion SQLite réussie !");
        } catch (Exception e) {
            MineVote.getInstance().getLogger().severe("Erreur de connexion SQLite: " + e.getMessage());
        }
    }

    public void ensureTableExists() {
        Base.exec("""
        CREATE TABLE IF NOT EXISTS votings (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            uuid TEXT NOT NULL UNIQUE,
            name TEXT NOT NULL,
            voting INTEGER NOT NULL
        )
    """);
    }

    public SQLite getInstance() {
        return instance;
    }
}

