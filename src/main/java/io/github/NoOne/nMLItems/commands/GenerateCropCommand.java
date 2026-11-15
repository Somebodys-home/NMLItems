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

import java.util.List;

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
        if (args.length == 1) {
            return List.of("wheat_seeds", "wheat_bundle", "sugar_cane", "jade_seeds", "jade_flower", "rhubarb_seeds", "rhubarb");
        } else if (args.length == 2) {
            return List.of("<level>");
        } else if (args.length == 3) {
            return List.of("<stars>");
        } else if (args.length == 4) {
            return List.of("<amount>");
        } else {
            return List.of();
        }
    }
}
