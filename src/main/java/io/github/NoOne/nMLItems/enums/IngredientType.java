package io.github.NoOne.nMLItems.enums;

public enum IngredientType {
    FLOUR;

    public static String getIngredientTypeString(IngredientType ingredientType) {
        return switch (ingredientType) {
            case FLOUR -> "flour";
        };
    }
}
