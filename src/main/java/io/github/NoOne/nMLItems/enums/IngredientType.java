package io.github.NoOne.nMLItems.enums;

public enum IngredientType {
    FLOUR,
    PIE_CRUST,
    BAKED_PIE_CRUST,
    FILLED_PIE_CRUST,
    WATER,
    SUGAR,
    RHUBARB;

    public static IngredientType fromString(String string) {
        return switch (string) {
            case "flour" -> FLOUR;
            case "pie_crust" -> PIE_CRUST;
            case "baked_pie_crust" -> BAKED_PIE_CRUST;
            case "filled_pie_crust" -> FILLED_PIE_CRUST;
            case "water" -> WATER;
            case "sugar" -> SUGAR;
            case "rhubarb" -> RHUBARB;
            default -> null;
        };
    }

    public static String toString(IngredientType ingredientType) {
        return switch (ingredientType) {
            case FLOUR -> "flour";
            case PIE_CRUST -> "pie_crust";
            case BAKED_PIE_CRUST -> "baked_pie_crust";
            case FILLED_PIE_CRUST -> "filled_pie_crust";
            case WATER -> "water";
            case SUGAR -> "sugar";
            case RHUBARB -> "rhubarb";
        };
    }
}
