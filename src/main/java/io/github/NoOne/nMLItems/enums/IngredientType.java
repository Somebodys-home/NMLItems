package io.github.NoOne.nMLItems.enums;

public enum IngredientType {
    FLOUR;

    public static IngredientType fromString(String string) {
        return switch (string) {
            case "flour" -> FLOUR;
            default -> null;
        };
    }

    public static String toString(IngredientType ingredientType) {
        return switch (ingredientType) {
            case FLOUR -> "flour";
        };
    }
}
