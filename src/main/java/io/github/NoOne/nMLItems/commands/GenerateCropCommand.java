package io.github.NoOne.nMLItems.commands;

import io.github.NoOne.nMLItems.itemDictionary.Crops;
import io.github.NoOne.nMLItems.itemDictionary.Seeds;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GenerateCropCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            String name = args[0];
            int level = Integer.parseInt(args[1]);
            double stars = Double.parseDouble(args[2]);
            int amount = Integer.parseInt(args[3]);

            switch (name) {
                case "wheat_seeds" -> player.getInventory().addItem(Seeds.wheatSeeds(level, stars, amount, false));
                case "wheat_bundle" -> player.getInventory().addItem(Crops.wheatBundle(level, stars, amount));
                case "sugar_cane" -> player.getInventory().addItem(Crops.sugarCane(level, stars, amount, false));
                case "jade_seeds" -> player.getInventory().addItem(Seeds.jadeSeeds(level, stars, amount, false));
                case "jade_flower" -> player.getInventory().addItem(Crops.jadeFlower(level, stars, amount));
                case "rhubarb_seeds" -> player.getInventory().addItem(Seeds.rhubarbSeeds(level, stars, amount, false));
                case "rhubarb" -> player.getInventory().addItem(Crops.rhubarb(level, stars, amount, false));
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> completions = new ArrayList<>(){{
            switch (args.length) {
                case 1 -> addAll(List.of("wheat_seeds", "wheat_bundle", "sugar_cane", "jade_seeds", "jade_flower", "rhubarb_seeds", "rhubarb"));
                case 2 -> add("<level>");
                case 3 -> add("<stars>");
                case 4 -> add("<amount>");
            }
        }};

        return completions.stream()
                .filter(s -> s.startsWith(args[args.length - 1].toLowerCase()))
                .collect(Collectors.toList());
    }
}
