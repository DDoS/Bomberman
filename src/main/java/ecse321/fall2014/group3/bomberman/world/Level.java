package ecse321.fall2014.group3.bomberman.world;

import java.util.ArrayList;
import java.util.List;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.Interface;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy.Enemy;
import ecse321.fall2014.group3.bomberman.physics.entity.ui.ButtonEntity;
import ecse321.fall2014.group3.bomberman.physics.entity.ui.SliderEntity;
import ecse321.fall2014.group3.bomberman.physics.entity.ui.TextBoxEntity;
import ecse321.fall2014.group3.bomberman.physics.entity.ui.UIBoxEntity;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.*;

/**
 *
 */
public enum Level {
    MAIN_MENU(true, 0, null, null),
    LEVEL_SELECT(true, 0, null, null),
    LEADER_BOARD(true, 0, null, null),
    GAME_OVER(true, 0, null, null),
    LEVEL_1(false, 1, new int[] {6, 0, 0, 0, 0, 0, 0, 0}, FlameUpgrade.class),
    LEVEL_2(false, 2, new int[] {3, 3, 0, 0, 0, 0, 0, 0}, BombUpgrade.class),
    LEVEL_3(false, 3, new int[] {2, 2, 2, 0, 0, 0, 0, 0}, Detonator.class),
    LEVEL_4(false, 4, new int[] {1, 2, 2, 2, 0, 0, 0, 0}, SpeedUpgrade.class),
    LEVEL_5(false, 5, new int[] {0, 4, 3, 0, 0, 0, 0, 0}, BombUpgrade.class),
    LEVEL_6(false, 6, new int[] {0, 2, 3, 2, 0, 0, 0, 0}, BombUpgrade.class),
    LEVEL_7(false, 7, new int[] {0, 2, 3, 2, 0, 0, 0, 0}, FlameUpgrade.class),
    LEVEL_8(false, 8, new int[] {0, 1, 2, 4, 0, 0, 0, 0},Detonator.class),
    LEVEL_9(false, 9, new int[] {0, 1, 1, 4, 1, 0, 0, 0},BombPass.class),
    LEVEL_10(false, 10, new int[] {0, 1, 1, 1, 3, 1, 0, 0}, WallPass.class),
    LEVEL_11(false, 11, new int[] {0, 1, 2, 3, 1, 1, 0, 0}, BombUpgrade.class),
    LEVEL_12(false, 12, new int[] {0, 1, 1, 1, 4, 1, 0, 0}, BombUpgrade.class),
    LEVEL_13(false, 13, new int[] {0, 0, 3, 3, 3, 0, 0, 0},Detonator.class),
    LEVEL_14(false, 14, new int[] {0, 0, 0, 0, 0, 7, 1, 0}, BombPass.class),
    LEVEL_15(false, 15, new int[] {0, 0, 1, 3, 3, 0, 1, 0}, FlameUpgrade.class),
    LEVEL_16(false, 16, new int[] {0, 0, 0, 3, 4, 0, 1, 0},  WallPass.class),
    LEVEL_17(false, 17, new int[] {0, 0, 5, 0, 2, 0, 1, 0}, BombUpgrade.class),
    LEVEL_18(false, 18, new int[] {3, 3, 0, 0, 0, 0, 2, 0}, BombPass.class),
    LEVEL_19(false, 19, new int[] {1, 1, 3, 0, 0, 1, 2, 0}, BombUpgrade.class),
    LEVEL_20(false, 20, new int[] {0, 1, 1, 1, 2, 1, 2, 0}, Detonator.class),
    LEVEL_21(false, 21, new int[] {0, 0, 0, 0, 4, 3, 2, 0}, BombPass.class),
    LEVEL_22(false, 22, new int[] {0, 0, 4, 3, 1, 0, 1, 0}, Detonator.class),
    LEVEL_23(false, 23, new int[] {0, 0, 2, 2, 2, 2, 1, 0}, BombUpgrade.class),
    LEVEL_24(false, 24, new int[] {0, 0, 1, 1, 4, 2, 1, 0}, Detonator.class),
    LEVEL_25(false, 25, new int[] {0, 2, 1, 1, 2, 2, 1, 0}, BombPass.class),
    LEVEL_26(false, 26, new int[] {1, 1, 1, 1, 2, 1, 1, 0}, Mystery.class),
    LEVEL_27(false, 27, new int[] {1, 1, 0, 0, 5, 1, 1, 0}, FlameUpgrade.class),
    LEVEL_28(false, 28, new int[] {0, 1, 3, 3, 1, 0, 1, 0},BombUpgrade.class),
    LEVEL_29(false, 29, new int[] {0, 0, 0, 0, 2, 5, 2, 0}, Detonator.class),
    LEVEL_30(false, 30, new int[] {0, 0, 3, 2, 1, 2, 1, 0}, FlamePass.class),
    LEVEL_31(false, 31, new int[] {0, 2, 2, 2, 2, 2, 0, 0},  WallPass.class),
    LEVEL_32(false, 32, new int[] {0, 1, 1, 3, 4, 0, 1, 0}, BombUpgrade.class),
    LEVEL_33(false, 33, new int[] {0, 0, 2, 2, 3, 1, 2, 0}, Detonator.class),
    LEVEL_34(false, 34, new int[] {0, 0, 2, 3, 3, 0, 2, 0}, Mystery.class),
    LEVEL_35(false, 35, new int[] {0, 0, 2, 1, 3, 1, 2, 0}, BombPass.class),
    LEVEL_36(false, 36, new int[] {0, 0, 2, 2, 3, 0, 3, 0}, FlamePass.class),
    LEVEL_37(false, 37, new int[] {0, 0, 2, 1, 3, 1, 3, 0}, Detonator.class),
    LEVEL_38(false, 38, new int[] {0, 0, 2, 2, 3, 0, 3, 0}, FlameUpgrade.class),
    LEVEL_39(false, 39, new int[] {0, 0, 1, 1, 2, 2, 4, 0},  WallPass.class),
    LEVEL_40(false, 40, new int[] {0, 0, 1, 2, 3, 0, 4, 0}, Mystery.class),
    LEVEL_41(false, 41, new int[] {0, 0, 1, 1, 3, 1, 4, 0}, Detonator.class),
    LEVEL_42(false, 42, new int[] {0, 0, 0, 1, 3, 1, 5, 0},  WallPass.class),
    LEVEL_43(false, 43, new int[] {0, 0, 0, 1, 2, 1, 6, 0}, BombPass.class),
    LEVEL_44(false, 44, new int[] {0, 0, 0, 1, 2, 1, 6, 0}, Detonator.class),
    LEVEL_45(false, 45, new int[] {0, 0, 0, 0, 2, 2, 6, 0}, Mystery.class),
    LEVEL_46(false, 46, new int[] {0, 0, 0, 0, 2, 2, 6, 0},  WallPass.class),
    LEVEL_47(false, 47, new int[] {0, 0, 0, 0, 2, 2, 6, 0}, BombPass.class),
    LEVEL_48(false, 48, new int[] {0, 0, 0, 0, 2, 1, 6, 1}, Detonator.class),
    LEVEL_49(false, 49, new int[] {0, 0, 0, 0, 1, 2, 6, 1}, FlamePass.class),
    LEVEL_50(false, 50, new int[] {0, 0, 0, 0, 1, 2, 5, 2}, Mystery.class),
    BONUS_LEVEL_1(false, -1, new int[] {99, 0, 0, 0, 0, 0, 0}, null),
    BONUS_LEVEL_2(false, -2, new int[] {0, 99, 0, 0, 0, 0, 0}, null),
    BONUS_LEVEL_3(false, -3, new int[] {0, 0, 99, 0, 0, 0, 0}, null),
    BONUS_LEVEL_4(false, -4, new int[] {0, 0, 0, 99, 0, 0, 0}, null),
    BONUS_LEVEL_5(false, -5, new int[] {0, 0, 0, 0, 99, 0, 0}, null),
    BONUS_LEVEL_6(false, -6, new int[] {0, 0, 0, 99, 0, 0, 0}, null),
    BONUS_LEVEL_7(false, -7, new int[] {0, 0, 0, 0, 0, 99, 0}, null),
    BONUS_LEVEL_8(false, -8, new int[] {0, 0, 0, 0, 0, 0, 99}, null),
    BONUS_LEVEL_9(false, -9, new int[] {0, 0, 0, 0, 0, 0, 99}, null),
    BONUS_LEVEL_10(false, -10, new int[] {0, 0, 0, 0, 0, 0, 99}, null);
    private final boolean menu;
    private final int number;
    private int[] enemyForLevel;
    private Class<? extends PowerUP> powerUP;

    private Level(boolean menu, int number, int[] enemy, Class<? extends PowerUP> powerUp) {
        this.menu = menu;
        this.number = number;
        this.enemyForLevel = enemy;
        this.powerUP = powerUp;
    }

    public boolean isMenu() {
        return menu;
    }

    public int getNumber() {
        return number;
    }

    public boolean isBonus() {
        return number < 0;
    }

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

    public List<UIBoxEntity> buildUI(int maxLevel) {
        final List<UIBoxEntity> ui = new ArrayList<>();
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

    private static TextBoxEntity newText(int position, int size, String text) {
        return new TextBoxEntity(new Vector2f(3, Interface.VIEW_HEIGHT_TILE - (3 + position)), new Vector2f(size, size), text);
    }

    private static ButtonEntity newButton(int position, int size, String text, String action) {
        return new ButtonEntity(new Vector2f(3, Interface.VIEW_HEIGHT_TILE - (3 + position)), new Vector2f(size, size), text, action);
    }

    private static SliderEntity newSlider(int position, int size, String text, String action, int min, int max) {
        return new SliderEntity(new Vector2f(3, Interface.VIEW_HEIGHT_TILE - (3 + position)), new Vector2f(size, size), text, action, min, max);
    }

    public static Level fromNumber(int i) {
        if (i < 1 || i > 50) {
            return null;
        }
        return values()[LEVEL_1.ordinal() + i - 1];
    }

    public int[] getEnemyForLevel() {
        return enemyForLevel;
    }

    public Class<? extends PowerUP> getPowerUPForLevel() {
        return powerUP;
    }
}
