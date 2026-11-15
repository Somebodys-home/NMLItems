package io.github.NoOne.nMLItems.commands;

import io.github.NoOne.nMLItems.itemDictionary.Crops;
import io.github.NoOne.nMLItems.itemDictionary.Food;
import io.github.NoOne.nMLItems.itemDictionary.Ingredients;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GenerateFoodCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            String name = args[0];
            int level = Integer.parseInt(args[1]);
            double stars = Double.parseDouble(args[2]);
            int amount = Integer.parseInt(args[3]);
            ItemStack pieCrust = Ingredients.pieCrust(
                    List.of(
                            Crops.wheatBundle(level, stars, 1),
                            Crops.wheatBundle(level, stars, 1),
                            Crops.wheatBundle(level, stars, 1),
                            Ingredients.bottleOfWater(level, stars, 1)
                    ),
                    level, stars, amount
            );
            ItemStack bakedPieCrust = Ingredients.bakedPieCrust(pieCrust);
            ItemStack filledPieCrust = Ingredients.filledPieCrust(
                    List.of(
                            Crops.rhubarb(level, stars, 1, false),
                            Crops.rhubarb(level, stars, 1, false),
                            Ingredients.sugar(Crops.sugarCane(level, stars, 1, false), 1)
                    ),
                    bakedPieCrust
            );
            ItemStack rhubarbPie = Food.rhubarbPie(filledPieCrust, level, stars, amount);
            switch (name) {
                case "rhubarb_pie" -> player.getInventory().addItem(rhubarbPie);
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> completions = new ArrayList<>(){{
            switch (args.length) {
                case 1 -> addAll(List.of("rhubarb_pie"));
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
