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

public class GenerateMaterialCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            String name = args[0];
            int level = Integer.parseInt(args[1]);
            double stars = Double.parseDouble(args[2]);
            int amount = Integer.parseInt(args[3]);

            switch (name) {
                case "wheat_seeds":
                    player.getInventory().addItem(Seeds.wheatSeeds(level, stars, amount));
                    break;
                case "wheat_bundle":
                    player.getInventory().addItem(Crops.wheatBundle(level, stars, amount, false));
                    break;
                case "sugar_cane":
                    player.getInventory().addItem(Crops.sugarCane(level, stars, amount, false));
                    break;
                case "jade_seeds":
                    player.getInventory().addItem(Seeds.jadeSeeds(level, stars, amount));
                    break;
                case "jade_flower_bush":
                    player.getInventory().addItem(Crops.jadeFlowerBundle(level, stars, amount, false));
                    break;
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(List.of("wheat_seeds", "wheat_bundle", "sugar_cane", "jade_seeds", "jade_flower_bush")).stream()
                    .filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        } else if (args.length == 2) {
            return new ArrayList<>(List.of("<level>")).stream()
                    .filter(s -> s.toLowerCase().startsWith(args[1].toLowerCase()))
                    .collect(Collectors.toList());
        } else if (args.length == 3) {
            return new ArrayList<>(List.of("<stars>")).stream()
                    .filter(s -> s.toLowerCase().startsWith(args[2].toLowerCase()))
                    .collect(Collectors.toList());
        } else if (args.length == 4) {
            return new ArrayList<>(List.of("<amount>")).stream()
                    .filter(s -> s.toLowerCase().startsWith(args[3].toLowerCase()))
                    .collect(Collectors.toList());
        }

        return List.of();
    }
}
