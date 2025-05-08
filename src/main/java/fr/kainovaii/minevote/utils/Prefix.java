package fr.kainovaii.minevote.utils;

import fr.kainovaii.minevote.MineVote;

public enum Prefix {
    BASE(MineVote.getInstance().getConfig().getString("prefix").replaceAll("&", "§"));
    private final String value;

    Prefix(String value) {
        this.value = value;
    }
    public String get() {
        return this.value;
    }
}