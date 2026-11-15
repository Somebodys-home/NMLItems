package io.github.NoOne.nMLItems.commands;

import io.github.NoOne.nMLItems.enums.ItemRarity;
import io.github.NoOne.nMLItems.enums.ItemType;
import io.github.NoOne.nMLItems.itemDictionary.Weapons;
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

public class GenerateWeaponCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            int level = Integer.parseInt(args[0]);
            String rarity = args[1];
            String type = args[2];
            ItemStack weapon = Weapons.generateWeapon(player, ItemType.fromString(type), ItemRarity.fromString(rarity), level);

            player.getInventory().addItem(weapon);
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
            return new ArrayList<>(){{
                for (ItemType itemType : ItemType.getAllWeaponTypes()) {
                    add(itemType.toString().toLowerCase());
                }
            }};
        } else {
            return List.of();
        }
    }
}