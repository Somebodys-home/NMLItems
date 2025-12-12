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

    public static double getMaterialStarFromDouble(MaterialStars stars) {
        switch (stars) {
            case HALF_STAR: return .5;
            case ONE_STAR: return 1;
            case ONE_AND_HALF_STAR: return 1.5;
            case TWO_STARS: return 2;
            case TWO_AND_HALF_STAR: return 2.5;
            case THREE_STARS: return 3;
            case THREE_AND_HALF_STAR: return 3.5;
            case FOUR_STARS: return 4;
            case FOUR_AND_HALF_STAR: return 4.5;
            case FIVE_STARS: return 5;
        }

        return 0;
    }

    public static String getMaterialStarEmoji(MaterialStars stars) {
        String starString = "";

        switch (stars) {
            case HALF_STAR: starString = "⯪";
            case ONE_STAR: starString = "★";
            case ONE_AND_HALF_STAR: starString = "★⯪";
            case TWO_STARS: starString = "★★";
            case TWO_AND_HALF_STAR: starString = "★★⯪";
            case THREE_STARS: starString = "★★★";
            case THREE_AND_HALF_STAR: starString = "★★★⯪";
            case FOUR_STARS: starString = "★★★★";
            case FOUR_AND_HALF_STAR: starString = "★★★★⯪";
            case FIVE_STARS: starString = "★★★★★";
        }

        return starString;
    }

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


    public static double getMaterialStarDouble(MaterialStars stars) {
        double materialStars = 0;

        switch (stars) {
            case HALF_STAR: materialStars = .5;
            case ONE_STAR: materialStars = 1;
            case ONE_AND_HALF_STAR: materialStars = 1.5;
            case TWO_STARS: materialStars = 2;
            case TWO_AND_HALF_STAR: materialStars = 2.5;
            case THREE_STARS: materialStars = 3;
            case THREE_AND_HALF_STAR: materialStars = 3.5;
            case FOUR_STARS: materialStars = 4;
            case FOUR_AND_HALF_STAR: materialStars = 4.5;
            case FIVE_STARS: materialStars = 5;
        }

        return materialStars;
    }
}
