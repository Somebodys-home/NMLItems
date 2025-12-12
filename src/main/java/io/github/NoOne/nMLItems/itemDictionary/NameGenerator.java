package io.github.NoOne.nMLItems.itemDictionary;

import io.github.NoOne.nMLItems.ItemRarity;
import io.github.NoOne.nMLItems.ItemType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static io.github.NoOne.nMLItems.ItemRarity.COMMON;

public class NameGenerator {
    public static String generateItemName(ItemType type, ItemType type2, ItemRarity rarity) {
        String[] nameSegments = null;

        switch (rarity) {
            case COMMON -> {
                nameSegments = new String[2];
                List<String> badAdjectives = new ArrayList<>(List.of("Garbage", "Awful", "Pitiful", "You Deserve This", "Disgusting", "Be Better",
                        "Babies' First", "Oh God That", "Rotten", "Poor", "Degrading", "Forgotten", "Racist", "Not Your Average"));

                nameSegments[0] = badAdjectives.get(ThreadLocalRandom.current().nextInt(badAdjectives.size()));
            }
            case UNCOMMON -> {
                nameSegments = new String[2];
                List<String> goodAdjectives = new ArrayList<>(List.of("Pretty Alright", "Lifelong", "Based", "Neato Dorito", "Goofy Ahh", "Nobodies'", "Knave's"));
                int randomAdjective = ThreadLocalRandom.current().nextInt(goodAdjectives.size());

                nameSegments[0] = goodAdjectives.get(randomAdjective);
            }
            case RARE -> {
                nameSegments = new String[3];
                List<String> goodAdjectives = new ArrayList<>(List.of("Pretty Alright", "Solid", "Well-Made", "Lifelong", "Based", "W", "Almost Mythical", "Neato Dorito",
                        "Goofy Ahh", "Nobodies'"));
                int randomAdjective = ThreadLocalRandom.current().nextInt(goodAdjectives.size());

                nameSegments[0] = goodAdjectives.get(randomAdjective);
                goodAdjectives.remove(randomAdjective);
                goodAdjectives.remove("Based");
                goodAdjectives.remove("Nobodies'");
                nameSegments[1] = goodAdjectives.get(ThreadLocalRandom.current().nextInt(goodAdjectives.size()));
            }
            case MYTHICAL -> {
                nameSegments = new String[3];
                List<String> greatAdjectives = new ArrayList<>(List.of("Amazing", "Godly", "King's", "Queen's", "Fabled", "Based", "W", "Legendary", "Goofy Ahh",
                        "Nobodies'"));
                int randomAdjective = ThreadLocalRandom.current().nextInt(greatAdjectives.size());

                nameSegments[0] = greatAdjectives.get(randomAdjective);
                greatAdjectives.remove(randomAdjective);
                greatAdjectives.remove("Based");
                greatAdjectives.remove("King's");
                greatAdjectives.remove("Queen's");
                greatAdjectives.remove("Nobodies'");
                nameSegments[1] = greatAdjectives.get(ThreadLocalRandom.current().nextInt(greatAdjectives.size()));
            }
        }

        assert nameSegments != null;
        switch (type) {
            case SWORD -> {
                List<String> sword = new ArrayList<>(List.of("Sword", "Seax", "Scimitar", "Bigger Knife", "Falchion", "Long Sword"));

                if (rarity == COMMON) {
                    sword.add("Bastard Sword");
                }

                nameSegments[nameSegments.length - 1] = sword.get(ThreadLocalRandom.current().nextInt(sword.size()));
            }
            case DAGGER -> {
                List<String> dagger = new ArrayList<>(List.of("Dagger", "Knife", "Cutlery", "Gillete Razor", "Beard Splitter", "Box Cutter"));
                nameSegments[nameSegments.length - 1] = dagger.get(ThreadLocalRandom.current().nextInt(dagger.size()));
            }
            case AXE -> {
                List<String> axe = new ArrayList<>(List.of("Axe", "Cleaver", "Battle Axe", "Tomahawk", "Chopper", "Box Cutter"));
                nameSegments[nameSegments.length - 1] = axe.get(ThreadLocalRandom.current().nextInt(axe.size()));
            }
            case HAMMER -> {
                List<String> hammer = new ArrayList<>(List.of("Squeaky Toy", "Blunt", "Mallet", "Bonker", "Hammer", "Piko Piko", "Spike Ball"));
                nameSegments[nameSegments.length - 1] = hammer.get(ThreadLocalRandom.current().nextInt(hammer.size()));
            }
            case SPEAR -> {
                List<String> spear = new ArrayList<>(List.of("Giant Arrow", "Javelin", "Military Fork", "Overcompensator", "Trident", "Spear", "Spork"));
                nameSegments[nameSegments.length - 1] = spear.get(ThreadLocalRandom.current().nextInt(spear.size()));
            }
            case GLOVE -> {
                List<String> glove = new ArrayList<>(List.of("Jawbreaker", "TKO", "Rock 'Em", "Sock 'Em", "Failure", "Gloves"));
                nameSegments[nameSegments.length - 1] = glove.get(ThreadLocalRandom.current().nextInt(glove.size()));
            }
            case BOW -> {
                List<String> bow = new ArrayList<>(List.of("Bow", "Peashooter", "Fling Sling", "...Gun?", "Yeet Cannon", "Pew Pew Pew", "Sharpshooter"));
                nameSegments[nameSegments.length - 1] = bow.get(ThreadLocalRandom.current().nextInt(bow.size()));
            }
            case WAND -> {
                List<String> wand = new ArrayList<>(List.of("Wand", "Rabbit Maker", "Boom Stick"));
                nameSegments[nameSegments.length - 1] = wand.get(ThreadLocalRandom.current().nextInt(wand.size()));
            }
            case STAFF -> {
                List<String> staff = new ArrayList<>(List.of("Staff", "Walking Stick", "Cane"));
                nameSegments[nameSegments.length - 1] = staff.get(ThreadLocalRandom.current().nextInt(staff.size()));
            }
            case CATALYST -> {
                List<String> catalyst = new ArrayList<>(List.of("Catalyst", "Grimoire", "Reading Material", "Textbook"));
                nameSegments[nameSegments.length - 1] = catalyst.get(ThreadLocalRandom.current().nextInt(catalyst.size()));
            }
            case SHIELD -> {
                List<String> shield = new ArrayList<>(List.of("Shield", "Buckler", "Kite", "Wall", "Aegis"));
                nameSegments[nameSegments.length - 1] = shield.get(ThreadLocalRandom.current().nextInt(shield.size()));
            }
            case QUIVER -> {
                List<String> quiver = new ArrayList<>(List.of("Quiver", "Stick Bag", "Holster"));
                nameSegments[nameSegments.length - 1] = quiver.get(ThreadLocalRandom.current().nextInt(quiver.size()));
            }
            case HOE -> {
                List<String> hoe = new ArrayList<>(List.of("Hoes"));
                nameSegments[nameSegments.length - 1] = hoe.get(ThreadLocalRandom.current().nextInt(hoe.size()));
            }
            case LIGHT -> {
                switch (type2) {
                    case HELMET -> {
                        List<String> helmet = new ArrayList<>(List.of("Cap"));
                        nameSegments[nameSegments.length - 1] = helmet.get(ThreadLocalRandom.current().nextInt(helmet.size()));
                    }
                    case CHESTPLATE -> {
                        List<String> chestplate = new ArrayList<>(List.of("Shirt"));
                        nameSegments[nameSegments.length - 1] = chestplate.get(ThreadLocalRandom.current().nextInt(chestplate.size()));
                    }
                    case LEGGINGS -> {
                        List<String> leggings = new ArrayList<>(List.of("Pants", "GYATT"));
                        nameSegments[nameSegments.length - 1] = leggings.get(ThreadLocalRandom.current().nextInt(leggings.size()));
                    }
                    case BOOTS -> {
                        List<String> boots = new ArrayList<>(List.of("Shoes"));
                        nameSegments[nameSegments.length - 1] = boots.get(ThreadLocalRandom.current().nextInt(boots.size()));
                    }
                }
            }
            case MEDIUM -> {
                switch (type2) {
                    case HELMET ->{
                        List<String> helm = new ArrayList<>(List.of("Coif", "Aventail"));
                        nameSegments[nameSegments.length - 1] = helm.get(ThreadLocalRandom.current().nextInt(helm.size()));
                    }
                    case CHESTPLATE -> {
                        List<String> chestplate = new ArrayList<>(List.of("Hauberk"));
                        nameSegments[nameSegments.length - 1] = chestplate.get(ThreadLocalRandom.current().nextInt(chestplate.size()));
                    }
                    case LEGGINGS -> {
                        List<String> leggings = new ArrayList<>(List.of("Chausses", "GYATT"));
                        nameSegments[nameSegments.length - 1] = leggings.get(ThreadLocalRandom.current().nextInt(leggings.size()));
                    }
                    case BOOTS -> {
                        List<String> boots = new ArrayList<>(List.of("Paleos"));
                        nameSegments[nameSegments.length - 1] = boots.get(ThreadLocalRandom.current().nextInt(boots.size()));
                    }
                }
            }
            case HEAVY -> {
                switch (type2) {
                    case HELMET -> {
                        List<String> helmet = new ArrayList<>(List.of("Helmet"));
                        nameSegments[nameSegments.length - 1] = helmet.get(ThreadLocalRandom.current().nextInt(helmet.size()));
                    }
                    case CHESTPLATE -> {
                        List<String> chestplate = new ArrayList<>(List.of("Chestplate"));
                        nameSegments[nameSegments.length - 1] = chestplate.get(ThreadLocalRandom.current().nextInt(chestplate.size()));
                    }
                    case LEGGINGS -> {
                        List<String> leggings = new ArrayList<>(List.of("Chausses", "GYATT"));
                        nameSegments[nameSegments.length - 1] = leggings.get(ThreadLocalRandom.current().nextInt(leggings.size()));
                    }
                    case BOOTS -> {
                        List<String> boots = new ArrayList<>(List.of("Boots"));
                        nameSegments[nameSegments.length - 1] = boots.get(ThreadLocalRandom.current().nextInt(boots.size()));
                    }
                }
            }
        }

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
