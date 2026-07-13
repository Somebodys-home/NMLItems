package io.github.NoOne.nMLItems.commands;

import io.github.NoOne.nMLItems.itemDictionary.Crops;
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

public class GenerateIngredientCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            String name = args[0];
            int level = Integer.parseInt(args[1]);
            double stars = Double.parseDouble(args[2]);
            int amount = Integer.parseInt(args[3]);

            ItemStack pieCrust = Ingredients.pieCrust(
                    List.of(
                            Crops.wheatBundle(1, 1, 1),
                            Crops.wheatBundle(1, 1, 1),
                            Crops.wheatBundle(1, 1, 1),
                            Ingredients.bottleOfWater(1, 1, 1)
                    ),
                    level, stars, amount
            );
            ItemStack bakedPieCrust = Ingredients.bakedPieCrust(pieCrust);
            ItemStack filledPieCrust = Ingredients.filledPieCrust(
                    List.of(bakedPieCrust),
                    bakedPieCrust,
                    level, stars, amount
            );

            switch (name) {
                case "flour" -> player.getInventory().addItem(Ingredients.flour(Crops.wheatBundle(level, stars, 1), amount));
                case "pie_crust" -> player.getInventory().addItem(pieCrust);
                case "baked_pie_crust" -> player.getInventory().addItem(bakedPieCrust);
                case "filled_pie_crust" -> player.getInventory().addItem(filledPieCrust);
                case "water" -> player.getInventory().addItem(Ingredients.bottleOfWater(level, stars, amount));
                case "sugar" -> player.getInventory().addItem(Ingredients.sugar(Crops.sugarCane(level, stars, 1, false), amount));
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(List.of("flour", "pie_crust", "baked_pie_crust", "filled_pie_crust", "water", "sugar")).stream()
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

