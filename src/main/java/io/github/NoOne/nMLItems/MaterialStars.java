package io.github.NoOne.nMLItems;

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

    public static String getMaterialStarsEmoji(double stars) {
        if (stars == .5) {
            return "⯪";
        } else if (stars == 1) {
            return "★";
        } else if (stars == 1.5) {
            return "★⯪";
        } else if (stars == 2) {
            return "★★";
        } else if (stars == 2.5) {
            return "★★⯪";
        } else if (stars == 3) {
            return "★★★";
        } else if (stars == 3.5) {
            return "★★★⯪";
        } else if (stars == 4) {
            return "★★★★";
        } else if (stars == 4.5) {
            return "★★★★⯪";
        } else if (stars == 5) {
            return "★★★★★";
        }

        return "";
    }
}
