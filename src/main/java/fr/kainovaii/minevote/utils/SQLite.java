package fr.kainovaii.minevote.utils;

import fr.kainovaii.minevote.MineVote;
import org.javalite.activejdbc.Base;

import java.io.File;

public class SQLite
{
    private SQLite instance;
    private MineVote mineVote;

    public SQLite() {
        instance = this;
        this.mineVote =  MineVote.getInstance();
    }

    public void connectDatabase() {
        try {
            File dbFile = new File(mineVote.getDataFolder(), "data.db");
            mineVote.getDataFolder().mkdirs();
            String url = "jdbc:sqlite:" + dbFile.getAbsolutePath();
            Base.open("org.sqlite.JDBC", url, "", "");
            mineVote.getLogger().info("Connexion SQLite réussie !");
        } catch (Exception e) {
            mineVote.getLogger().severe("Erreur de connexion SQLite: " + e.getMessage());
        }
    }

    public void ensureTableExists() {
        Base.exec("""
            CREATE TABLE IF NOT EXISTS voters (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                uuid TEXT NOT NULL UNIQUE,
                name TEXT NOT NULL,
                voting INTEGER NOT NULL,
                bank DECIMAL NOT NULL
            )
        """);
        Base.exec("""
            CREATE TABLE IF NOT EXISTS transactions (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                amount DECIMAL NOT NULL UNIQUE,
                reason TEXT NOT NULL UNIQUE,
                createdAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
            )
        """);
        Base.exec("""
            CREATE TABLE IF NOT EXISTS rewards (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                commands TEXT NOT NULL
            )
        """);
    }

    public SQLite getInstance() {
        return instance;
    }
}

