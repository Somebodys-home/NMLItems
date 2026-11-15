package io.github.NoOne.nMLItems.commands;

import io.github.NoOne.nMLItems.enums.ItemRarity;
import io.github.NoOne.nMLItems.enums.ItemType;
import io.github.NoOne.nMLItems.itemDictionary.Armor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GenerateArmorCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            int level = Integer.parseInt(args[0]);
            String rarity = args[1];
            String type = args[2];
            String piece = args[3];
            ItemStack armor = Armor.generateArmor(player, ItemRarity.fromString(rarity), ItemType.fromString(type), ItemType.fromString(piece), level);


            player.getInventory().addItem(armor);
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return List.of("<level>");
        } else if (args.length == 2) {
            return List.of("common", "uncommon", "rare", "mythical");
        } else if (args.length == 3) {
            return List.of("light", "medium", "heavy");
        } else if (args.length == 4) {
            return List.of("helmet", "chestplate", "leggings", "boots");
        }
        return List.of();
    }
}
