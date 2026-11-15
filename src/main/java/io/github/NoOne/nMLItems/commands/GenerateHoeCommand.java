package io.github.NoOne.nMLItems.commands;

import io.github.NoOne.nMLItems.enums.ItemRarity;
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

public class GenerateHoeCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            int level = Integer.parseInt(args[0]);
            String rarity = args[1];

            ItemStack hoe = Hoes.generateHoe(player, ItemRarity.fromString(rarity), level);

            player.getInventory().addItem(hoe);
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> completions = new ArrayList<>(){{
            switch (args.length) {
                case 1 -> add("<level>");
                case 2 -> addAll(List.of("common", "uncommon", "rare", "mythical"));
            }
        }};

        return completions.stream()
                .filter(s -> s.startsWith(args[args.length - 1].toLowerCase()))
                .collect(Collectors.toList());
    }
}

