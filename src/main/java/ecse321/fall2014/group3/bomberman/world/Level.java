/**
 * @author Phil Douyon
 */
package ecse321.fall2014.group3.bomberman.world;

import java.util.ArrayList;
import java.util.List;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.Interface;
import ecse321.fall2014.group3.bomberman.physics.entity.ui.Button;
import ecse321.fall2014.group3.bomberman.physics.entity.ui.Slider;
import ecse321.fall2014.group3.bomberman.physics.entity.ui.TextBox;
import ecse321.fall2014.group3.bomberman.physics.entity.ui.UIBox;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.*;

/**
 *
 */
public enum Level {

    /** The main menu. */
    MAIN_MENU(true, 0, null, null),

    /** The level select menu. */
    LEVEL_SELECT(true, 0, null, null),

    /** The leader board menu. */
    LEADER_BOARD(true, 0, null, null),

    /** The game over menu. */
    GAME_OVER(true, 0, null, null),

    /** The LEVEL 1 map. */
    LEVEL_1(false, 1, new int[] {6, 0, 0, 0, 0, 0, 0, 0}, FlameUpgrade.class),

    /** The LEVEL 2 map. */
    LEVEL_2(false, 2, new int[] {3, 3, 0, 0, 0, 0, 0, 0}, BombUpgrade.class),

    /** The LEVEL 3 map. */
    LEVEL_3(false, 3, new int[] {2, 2, 2, 0, 0, 0, 0, 0}, Detonator.class),

    /** The LEVEL 4 map. */
    LEVEL_4(false, 4, new int[] {1, 2, 2, 2, 0, 0, 0, 0}, SpeedUpgrade.class),

    /** The LEVEL 5 map. */
    LEVEL_5(false, 5, new int[] {0, 4, 3, 0, 0, 0, 0, 0}, BombUpgrade.class),

    /** The LEVEL 6 map. */
    LEVEL_6(false, 6, new int[] {0, 2, 3, 2, 0, 0, 0, 0}, BombUpgrade.class),

    /** The LEVEL 7 map. */
    LEVEL_7(false, 7, new int[] {0, 2, 3, 2, 0, 0, 0, 0}, FlameUpgrade.class),

    /** The LEVEL 8 map. */
    LEVEL_8(false, 8, new int[] {0, 1, 2, 4, 0, 0, 0, 0}, Detonator.class),

    /** The LEVEL 9 map. */
    LEVEL_9(false, 9, new int[] {0, 1, 1, 4, 1, 0, 0, 0}, BombPass.class),

    /** The LEVEL 10 map. */
    LEVEL_10(false, 10, new int[] {0, 1, 1, 1, 3, 1, 0, 0}, WallPass.class),

    /** The LEVEL 11 map. */
    LEVEL_11(false, 11, new int[] {0, 1, 2, 3, 1, 1, 0, 0}, BombUpgrade.class),

    /** The LEVEL 12 map. */
    LEVEL_12(false, 12, new int[] {0, 1, 1, 1, 4, 1, 0, 0}, BombUpgrade.class),

    /** The LEVEL 13 map. */
    LEVEL_13(false, 13, new int[] {0, 0, 3, 3, 3, 0, 0, 0}, Detonator.class),

    /** The LEVEL 14 map. */
    LEVEL_14(false, 14, new int[] {0, 0, 0, 0, 0, 7, 1, 0}, BombPass.class),

    /** The LEVEL 15 map. */
    LEVEL_15(false, 15, new int[] {0, 0, 1, 3, 3, 0, 1, 0}, FlameUpgrade.class),

    /** The LEVEL 16 map. */
    LEVEL_16(false, 16, new int[] {0, 0, 0, 3, 4, 0, 1, 0}, WallPass.class),

    /** The LEVEL 17 map. */
    LEVEL_17(false, 17, new int[] {0, 0, 5, 0, 2, 0, 1, 0}, BombUpgrade.class),

    /** The LEVEL 18 map. */
    LEVEL_18(false, 18, new int[] {3, 3, 0, 0, 0, 0, 2, 0}, BombPass.class),

    /** The LEVEL 19 map. */
    LEVEL_19(false, 19, new int[] {1, 1, 3, 0, 0, 1, 2, 0}, BombUpgrade.class),

    /** The LEVEL 20 map. */
    LEVEL_20(false, 20, new int[] {0, 1, 1, 1, 2, 1, 2, 0}, Detonator.class),

    /** The LEVEL 21 map. */
    LEVEL_21(false, 21, new int[] {0, 0, 0, 0, 4, 3, 2, 0}, BombPass.class),

    /** The LEVEL 22 map. */
    LEVEL_22(false, 22, new int[] {0, 0, 4, 3, 1, 0, 1, 0}, Detonator.class),

    /** The LEVEL 23 map. */
    LEVEL_23(false, 23, new int[] {0, 0, 2, 2, 2, 2, 1, 0}, BombUpgrade.class),

    /** The LEVEL 24 map. */
    LEVEL_24(false, 24, new int[] {0, 0, 1, 1, 4, 2, 1, 0}, Detonator.class),

    /** The LEVEL 25 map. */
    LEVEL_25(false, 25, new int[] {0, 2, 1, 1, 2, 2, 1, 0}, BombPass.class),

    /** The LEVEL 26 map. */
    LEVEL_26(false, 26, new int[] {1, 1, 1, 1, 2, 1, 1, 0}, Mystery.class),

    /** The LEVEL 27 map. */
    LEVEL_27(false, 27, new int[] {1, 1, 0, 0, 5, 1, 1, 0}, FlameUpgrade.class),

    /** The LEVEL 28 map. */
    LEVEL_28(false, 28, new int[] {0, 1, 3, 3, 1, 0, 1, 0}, BombUpgrade.class),

    /** The LEVEL 29 map. */
    LEVEL_29(false, 29, new int[] {0, 0, 0, 0, 2, 5, 2, 0}, Detonator.class),

    /** The LEVEL 30 map. */
    LEVEL_30(false, 30, new int[] {0, 0, 3, 2, 1, 2, 1, 0}, FlamePass.class),

    /** The LEVEL 31 map. */
    LEVEL_31(false, 31, new int[] {0, 2, 2, 2, 2, 2, 0, 0}, WallPass.class),

    /** The LEVEL 32 map. */
    LEVEL_32(false, 32, new int[] {0, 1, 1, 3, 4, 0, 1, 0}, BombUpgrade.class),

    /** The LEVEL 33 map. */
    LEVEL_33(false, 33, new int[] {0, 0, 2, 2, 3, 1, 2, 0}, Detonator.class),

    /** The LEVEL 34 map. */
    LEVEL_34(false, 34, new int[] {0, 0, 2, 3, 3, 0, 2, 0}, Mystery.class),

    /** The LEVEL 35 map. */
    LEVEL_35(false, 35, new int[] {0, 0, 2, 1, 3, 1, 2, 0}, BombPass.class),

    /** The LEVEL 36 map. */
    LEVEL_36(false, 36, new int[] {0, 0, 2, 2, 3, 0, 3, 0}, FlamePass.class),

    /** The LEVEL 37 map. */
    LEVEL_37(false, 37, new int[] {0, 0, 2, 1, 3, 1, 3, 0}, Detonator.class),

    /** The LEVEL 38 map. */
    LEVEL_38(false, 38, new int[] {0, 0, 2, 2, 3, 0, 3, 0}, FlameUpgrade.class),

    /** The LEVEL 39 map. */
    LEVEL_39(false, 39, new int[] {0, 0, 1, 1, 2, 2, 4, 0}, WallPass.class),

    /** The LEVEL 40 map. */
    LEVEL_40(false, 40, new int[] {0, 0, 1, 2, 3, 0, 4, 0}, Mystery.class),

    /** The LEVEL 41 map. */
    LEVEL_41(false, 41, new int[] {0, 0, 1, 1, 3, 1, 4, 0}, Detonator.class),

    /** The LEVEL 42 map. */
    LEVEL_42(false, 42, new int[] {0, 0, 0, 1, 3, 1, 5, 0}, WallPass.class),

    /** The LEVEL 43 map. */
    LEVEL_43(false, 43, new int[] {0, 0, 0, 1, 2, 1, 6, 0}, BombPass.class),

    /** The LEVEL 44 map. */
    LEVEL_44(false, 44, new int[] {0, 0, 0, 1, 2, 1, 6, 0}, Detonator.class),

    /** The LEVEL 45 map. */
    LEVEL_45(false, 45, new int[] {0, 0, 0, 0, 2, 2, 6, 0}, Mystery.class),

    /** The LEVEL 46 map. */
    LEVEL_46(false, 46, new int[] {0, 0, 0, 0, 2, 2, 6, 0}, WallPass.class),

    /** The LEVEL 47 map. */
    LEVEL_47(false, 47, new int[] {0, 0, 0, 0, 2, 2, 6, 0}, BombPass.class),

    /** The LEVEL 48 map. */
    LEVEL_48(false, 48, new int[] {0, 0, 0, 0, 2, 1, 6, 1}, Detonator.class),

    /** The LEVEL 49 map. */
    LEVEL_49(false, 49, new int[] {0, 0, 0, 0, 1, 2, 6, 1}, FlamePass.class),

    /** The LEVEL 50 map. */
    LEVEL_50(false, 50, new int[] {0, 0, 0, 0, 1, 2, 5, 2}, Mystery.class),

    /** The BONUS LEVEL 1 map. */
    BONUS_LEVEL_1(false, -1, new int[] {99, 0, 0, 0, 0, 0, 0}, null),

    /** The BONUS LEVEL 2 map. */
    BONUS_LEVEL_2(false, -2, new int[] {0, 99, 0, 0, 0, 0, 0}, null),

    /** The BONUS LEVEL 3 map. */
    BONUS_LEVEL_3(false, -3, new int[] {0, 0, 99, 0, 0, 0, 0}, null),

    /** The BONUS LEVEL 4 map. */
    BONUS_LEVEL_4(false, -4, new int[] {0, 0, 0, 99, 0, 0, 0}, null),

    /** The BONUS LEVEL 5 map. */
    BONUS_LEVEL_5(false, -5, new int[] {0, 0, 0, 0, 99, 0, 0}, null),

    /** The BONUS LEVEL 6 map. */
    BONUS_LEVEL_6(false, -6, new int[] {0, 0, 0, 99, 0, 0, 0}, null),

    /** The BONUS LEVEL 7 map. */
    BONUS_LEVEL_7(false, -7, new int[] {0, 0, 0, 0, 0, 99, 0}, null),

    /** The BONUS LEVEL 8 map. */
    BONUS_LEVEL_8(false, -8, new int[] {0, 0, 0, 0, 0, 0, 99}, null),

    /** The BONUS LEVEL 9 map. */
    BONUS_LEVEL_9(false, -9, new int[] {0, 0, 0, 0, 0, 0, 99}, null),

    /** The BONUS LEVEL 10 map. */
    BONUS_LEVEL_10(false, -10, new int[] {0, 0, 0, 0, 0, 0, 99}, null);
    private final boolean menu;
    private final int number;
    private int[] enemyForLevel;
    private Class<? extends PowerUP> powerUP;

    //the level constructor
    private Level(boolean menu, int number, int[] enemy, Class<? extends PowerUP> powerUp) {
        this.menu = menu;
        this.number = number;
        this.enemyForLevel = enemy;
        this.powerUP = powerUp;
    }

    /**
     * Checks if is menu.
     *
     * @return true, if is menu
     */
    public boolean isMenu() {
        return menu;
    }

    /**
     * Gets the level number.
     *
     * @return the level number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Checks if is bonus level.
     *
     * @return true, if is bonus level
     */
    public boolean isBonus() {
        return number < 0;
    }

    /**
     * Next.
     *
     * @return the next level
     */
    public Level next() {
        if (number == 0 || number >= 50 || number <= -10) {
            return null;
        }
        final int nextOrdinal;
        if (number < 0) {
            nextOrdinal = LEVEL_1.ordinal() - number * 5;
        } else if (number % 5 == 0) {
            nextOrdinal = BONUS_LEVEL_1.ordinal() + number / 5 - 1;
        } else {
            nextOrdinal = ordinal() + 1;
        }
        return values()[nextOrdinal];
    }

    /**
     * Builds the ui.
     *
     * @param maxLevel the max level
     * @return the ui list
     */
    public List<UIBox> buildUI(int maxLevel) {
        final List<UIBox> ui = new ArrayList<>();
        switch (this) {
            case MAIN_MENU:
                ui.add(newText(0, 4, "Bomberman"));
                ui.add(newButton(2, 2, "Continue", "levelload.restore"));
                ui.add(newButton(3, 2, "Select level", "menuload.levelselect"));
                ui.add(newButton(4, 2, "View leader board", "menuload.loaderboard"));
                break;
            case LEVEL_SELECT:
                ui.add(newText(0, 4, "Level select"));
                ui.add(newButton(2, 2, "Main menu", "menuload.main"));
                ui.add(newSlider(3, 2, "Level: %d", "levelload.number", 1, maxLevel));
                break;
            case LEADER_BOARD:
                ui.add(newText(0, 4, "Leader board"));
                ui.add(newButton(2, 2, "Main menu", "menuload.main"));
                break;
            case GAME_OVER:
                ui.add(newText(0, 4, "Game Over"));
                ui.add(newButton(2, 2, "Main menu", "menuload.main"));
                ui.add(newButton(3, 2, "View leader board", "menuload.loaderboard"));
                break;
        }
        return ui;
    }

    //gets the text box
    private static TextBox newText(int position, int size, String text) {
        return new TextBox(new Vector2f(3, Interface.VIEW_HEIGHT_TILE - (3 + position)), new Vector2f(size, size), text);
    }

    //gets the button
    private static Button newButton(int position, int size, String text, String action) {
        return new Button(new Vector2f(3, Interface.VIEW_HEIGHT_TILE - (3 + position)), new Vector2f(size, size), text, action);
    }

    //gets the slider
    private static Slider newSlider(int position, int size, String text, String action, int min, int max) {
        return new Slider(new Vector2f(3, Interface.VIEW_HEIGHT_TILE - (3 + position)), new Vector2f(size, size), text, action, min, max);
    }

    /**
     * From number.
     *
     * @param i the level
     * @return the level
     */
    public static Level fromNumber(int i) {
        if (i < 1 || i > 50) {
            return null;
        }
        return values()[LEVEL_1.ordinal() + i - 1];
    }

    /**
     * Gets the enemy for level.
     *
     * @return the enemy for the level
     */
    public int[] getEnemyForLevel() {
        return enemyForLevel;
    }

    /**
     * Gets the power up for level.
     *
     * @return the power up for the level
     */
    public Class<? extends PowerUP> getPowerUPForLevel() {
        return powerUP;
    }
}
