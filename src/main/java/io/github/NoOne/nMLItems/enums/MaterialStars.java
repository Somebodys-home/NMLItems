package io.github.NoOne.nMLItems.enums;

public enum MaterialStars {
    HALF_STAR,
    ONE_STAR,
    ONE_AND_HALF_STAR,
    TWO_STARS,
    TWO_AND_HALF_STAR,
    THREE_STARS,
    THREE_AND_HALF_STAR,
    FOUR_STARS,
    FOUR_AND_HALF_STAR,
    FIVE_STARS;

    public static MaterialStars toMaterialStars(double stars) {
        if (stars == .5) {
            return HALF_STAR;
        } else if (stars == 1) {
            return ONE_STAR;
        } else if (stars == 1.5) {
            return ONE_AND_HALF_STAR;
        } else if (stars == 2) {
            return TWO_STARS;
        } else if (stars == 2.5) {
            return TWO_AND_HALF_STAR;
        } else if (stars == 3) {
            return THREE_STARS;
        } else if (stars == 3.5) {
            return THREE_AND_HALF_STAR;
        } else if (stars == 4) {
            return FOUR_STARS;
        } else if (stars == 4.5) {
            return FOUR_AND_HALF_STAR;
        } else if (stars == 5) {
            return FIVE_STARS;
        }

        return null;
    }

    public static String getMaterialStarsEmoji(double stars) {
        return switch (toMaterialStars(stars)) {
            case HALF_STAR -> "⯪";
            case ONE_STAR -> "★";
            case ONE_AND_HALF_STAR -> "★⯪";
            case TWO_STARS -> "★★";
            case TWO_AND_HALF_STAR -> "★★⯪";
            case THREE_STARS -> "★★★";
            case THREE_AND_HALF_STAR -> "★★★⯪";
            case FOUR_STARS -> "★★★★";
            case FOUR_AND_HALF_STAR -> "★★★★⯪";
            case FIVE_STARS -> "★★★★★";
        };
    }

    public static double getStarMultiplier(MaterialStars materialStars) {
        return switch (materialStars) {
            case HALF_STAR -> .75;
            case ONE_STAR -> 1;
            case ONE_AND_HALF_STAR -> 1.25;
            case TWO_STARS -> 1.5;
            case TWO_AND_HALF_STAR -> 1.75;
            case THREE_STARS -> 2;
            case THREE_AND_HALF_STAR -> 2.25;
            case FOUR_STARS -> 2.5;
            case FOUR_AND_HALF_STAR -> 2.75;
            case FIVE_STARS -> 3;
        };
    }
}
