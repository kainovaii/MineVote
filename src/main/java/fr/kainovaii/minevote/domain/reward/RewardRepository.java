package fr.kainovaii.minevote.domain.reward;

import com.google.gson.Gson;
import org.bukkit.Bukkit;

import java.util.List;

public class RewardRepository {
    public static void create(String name, List<String> commands) {
        Gson gson = new Gson();
        String commandsJson = gson.toJson(commands);

        Reward reward = new Reward();
        reward.set("name", name, "commands", commandsJson);
        reward.saveIt();

        System.out.println("Its okay !");
    }

    public static List<String> getCommandsFrom(String name) {
        List<Reward> rewards = Reward.where("name = ?", name);
        Gson gson = new Gson();
        for (Reward reward : rewards) {
            String commandsJson = reward.getString("commands");
            List<String> commands = gson.fromJson(commandsJson, List.class);

            for (String cmd : commands) {
                String command = cmd.replace("{player}", name);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            }
            reward.delete();
        }
        return List.of();
    }
}