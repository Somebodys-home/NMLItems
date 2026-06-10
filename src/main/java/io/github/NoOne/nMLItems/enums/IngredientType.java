package io.github.NoOne.nMLItems.enums;

public enum IngredientType {
    GRAIN,
    FLOUR,
    DOUGH,
    WATER;

    public static IngredientType fromString(String string) {
        return switch (string) {
            case "grain" -> GRAIN;
            case "flour" -> FLOUR;
            case "dough" -> DOUGH;
            case "water" -> WATER;
            default -> null;
        };
    }

    public static String toString(IngredientType ingredientType) {
        return switch (ingredientType) {
            case GRAIN -> "grain";
            case FLOUR -> "flour";
            case DOUGH -> "dough";
            case WATER -> "water";
        };
    }
}
