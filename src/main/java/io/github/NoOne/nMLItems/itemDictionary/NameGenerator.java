package io.github.NoOne.nMLItems.itemDictionary;

import io.github.NoOne.nMLItems.enums.ItemRarity;
import io.github.NoOne.nMLItems.enums.ItemType;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static io.github.NoOne.nMLItems.enums.ItemRarity.COMMON;
import static io.github.NoOne.nMLItems.enums.ItemRarity.MYTHICAL;

public class NameGenerator {
    public static String generateItemName(ItemType type, ItemType type2, ItemRarity rarity) {
        ArrayList<String> possibleAdjectives = new ArrayList<>();
        ArrayList<String> possibleNames = new ArrayList<>();
        String[] nameSegments = null;

        switch (rarity) {
            case COMMON -> {
                nameSegments = new String[2];
                possibleAdjectives = new ArrayList<>(List.of("Garbage", "Awful", "Pitiful", "You Deserve This", "Disgusting", "Be Better",
                        "Babies' First", "Oh God That", "Rotten", "Poor", "Degrading", "Forgotten", "Racist", "Not Your Average", "Beguiled", "Exiled", "Rusty",
                        "Nobodies'", "Cheap", "Cringe"));
            }
            case UNCOMMON -> {
                nameSegments = new String[2];
                possibleAdjectives = new ArrayList<>(List.of("Pretty Alright", "Lifelong", "Based", "Neato Dorito", "Goofy Ass", "Nobodies'", "Knave's"));
            }
            case RARE -> {
                nameSegments = new String[3];
                possibleAdjectives = new ArrayList<>(List.of("Pretty Alright", "Solid", "Well-Made", "Lifelong", "Based", "Almost Mythical", "Neato Dorito",
                        "Goofy Ass", "Nobodies'"));
            }
            case MYTHICAL -> {
                nameSegments = new String[3];
                possibleAdjectives = new ArrayList<>(List.of("Amazing", "Godly", "King's", "Queen's", "Fabled", "Based", "Legendary", "Goofy Ass",
                        "Nobodies'", "Perfect", "Faceguy's", "Facelady's"));
            }
        }

        assert nameSegments != null;
        switch (type) {
            case SWORD -> {
                possibleNames = new ArrayList<>(List.of("Sword", "Seax", "Scimitar", "Bigger Knife", "Falchion", "Long Sword"));

                switch (rarity) {
                    case COMMON -> possibleNames.add("Bastard Sword");
                    case MYTHICAL -> possibleNames.add("Dragon Slayer");
                }
            }
            case DAGGER -> possibleNames = new ArrayList<>(List.of("Dagger", "Knife", "Cutlery", "Gillete Razor", "Beard Splitter", "Box Cutter"));
            case AXE -> possibleNames = new ArrayList<>(List.of("Axe", "Cleaver", "Battle Axe", "Tomahawk", "Chopper", "Box Cutter"));
            case HAMMER -> possibleNames = new ArrayList<>(List.of("Squeaky Toy", "Blunt", "Mallet", "Bonker", "Hammer", "Piko Piko", "Spike Ball"));
            case SPEAR -> {
                possibleNames = new ArrayList<>(List.of("Giant Arrow", "Javelin", "Military Fork", "Trident", "Spear", "Spork"));

                switch (rarity) {
                    case COMMON -> possibleNames.add("Overcompensator");
                }
            }
            case GLOVE -> possibleNames = new ArrayList<>(List.of("Jawbreaker", "TKO", "Rock 'Em", "Sock 'Em", "Failure", "Gloves"));
            case BOW -> possibleNames = new ArrayList<>(List.of("Bow", "Peashooter", "Fling Sling", "...Gun?", "Yeet Cannon", "Pew Pew Pew", "Sharpshooter"));
            case WAND -> possibleNames = new ArrayList<>(List.of("Wand", "Rabbit Maker", "Boom Stick"));
            case STAFF -> possibleNames = new ArrayList<>(List.of("Staff", "Walking Stick", "Cane"));
            case CATALYST -> possibleNames = new ArrayList<>(List.of("Catalyst", "Grimoire", "Reading Material", "Textbook", "Smut"));
            case SHIELD -> possibleNames = new ArrayList<>(List.of("Shield", "Buckler", "Kite", "Wall", "Aegis"));
            case QUIVER -> possibleNames = new ArrayList<>(List.of("Quiver", "Stick Bag", "Holster"));
            case HOE -> possibleNames = new ArrayList<>(List.of("Hoe"));
            case LIGHT -> {
                switch (type2) {
                    case HELMET -> possibleNames = new ArrayList<>(List.of("Cap"));
                    case CHESTPLATE -> possibleNames = new ArrayList<>(List.of("Shirt"));
                    case LEGGINGS -> possibleNames = new ArrayList<>(List.of("Pants", "GYATT"));
                    case BOOTS -> possibleNames = new ArrayList<>(List.of("Shoes"));
                }
            }
            case MEDIUM -> {
                switch (type2) {
                    case HELMET -> possibleNames = new ArrayList<>(List.of("Coif", "Aventail"));
                    case CHESTPLATE -> possibleNames = new ArrayList<>(List.of("Hauberk"));
                    case LEGGINGS -> possibleNames = new ArrayList<>(List.of("Chausses", "GYATT"));
                    case BOOTS -> possibleNames = new ArrayList<>(List.of("Paleos"));
                }
            }
            case HEAVY -> {
                switch (type2) {
                    case HELMET -> possibleNames = new ArrayList<>(List.of("Helmet"));
                    case CHESTPLATE -> possibleNames = new ArrayList<>(List.of("Chestplate"));
                    case LEGGINGS -> possibleNames = new ArrayList<>(List.of("Chausses", "GYATT"));
                    case BOOTS -> possibleNames = new ArrayList<>(List.of("Boots"));
                }
            }
        }

        switch (type) { // general adjectives/names
            case HELMET, CHESTPLATE, LEGGINGS, BOOTS -> {
                if (rarity == MYTHICAL) {
                    possibleNames.add("Drip");
                }
            }
        }
        // choosing adjectives
        for (int i = 0; i < nameSegments.length - 1; i++) {
            if (i > 0) { // make sure that belonging adjectives can only be the first adjective of the item
                possibleAdjectives.removeIf(s -> s.contains("'s"));
                possibleAdjectives.removeIf(s -> s.contains("s'"));
            }

            for (String s : nameSegments) { // dupe clause
                if (s != null) {
                    possibleAdjectives.remove(s);
                }
            }

            nameSegments[i] =  possibleAdjectives.get(ThreadLocalRandom.current().nextInt(possibleAdjectives.size()));
        }
        // choosing name
        nameSegments[nameSegments.length - 1] = possibleNames.get(ThreadLocalRandom.current().nextInt(possibleNames.size()));

        // finalizing name
        String name = "" + ItemRarity.getItemRarityColor(rarity);

        for (int i = 0; i < nameSegments.length; i++) {
            if (i == nameSegments.length - 1) {
                name += nameSegments[i];
            } else {
                name += nameSegments[i] + " ";
            }
        }

        return name;
    }
}
