package fr.kainovaii.minevote.utils;

public enum Prefix {
    BASE("§7[§6MineVote§7] §f");
    private final String value;

    Prefix(String value) {
        this.value = value;
    }
    public String get() {
        return this.value;
    }
}