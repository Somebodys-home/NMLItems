package io.github.NoOne.nMLItems.commands;

import io.github.NoOne.nMLItems.ItemRarity;
import io.github.NoOne.nMLItems.itemDictionary.GardenModifiers;
import io.github.NoOne.nMLItems.itemDictionary.Hoes;
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

public class GenerateItemCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            String item = args[0];

            switch (item) {
                case "fertilizer" -> player.getInventory().addItem(GardenModifiers.fertilizer());
                case "watering_can" -> player.getInventory().addItem(GardenModifiers.wateringCan());
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(List.of("fertilizer", "watering_can")).stream()
                    .filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }

        return List.of();
    }
}

