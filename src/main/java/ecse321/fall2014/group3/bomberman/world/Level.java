package ecse321.fall2014.group3.bomberman.world;

import java.util.ArrayList;
import java.util.List;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.Interface;
import ecse321.fall2014.group3.bomberman.physics.entity.ui.ButtonEntity;
import ecse321.fall2014.group3.bomberman.physics.entity.ui.SliderEntity;
import ecse321.fall2014.group3.bomberman.physics.entity.ui.TextBoxEntity;
import ecse321.fall2014.group3.bomberman.physics.entity.ui.UIBoxEntity;

/**
 *
 */
public enum Level {
    MAIN_MENU(true, 0),
    LEVEL_SELECT(true, 0),
    LEADER_BOARD(true, 0),
    GAME_OVER(true, 0),
    LEVEL_1(false, 1),
    LEVEL_2(false, 2),
    LEVEL_3(false, 3),
    LEVEL_4(false, 4),
    LEVEL_5(false, 5),
    LEVEL_6(false, 6),
    LEVEL_7(false, 7),
    LEVEL_8(false, 8),
    LEVEL_9(false, 9),
    LEVEL_10(false, 10),
    LEVEL_11(false, 11),
    LEVEL_12(false, 12),
    LEVEL_13(false, 13),
    LEVEL_14(false, 14),
    LEVEL_15(false, 15),
    LEVEL_16(false, 16),
    LEVEL_17(false, 17),
    LEVEL_18(false, 18),
    LEVEL_19(false, 19),
    LEVEL_20(false, 20),
    LEVEL_21(false, 21),
    LEVEL_22(false, 22),
    LEVEL_23(false, 23),
    LEVEL_24(false, 24),
    LEVEL_25(false, 25),
    LEVEL_26(false, 26),
    LEVEL_27(false, 27),
    LEVEL_28(false, 28),
    LEVEL_29(false, 29),
    LEVEL_30(false, 30),
    LEVEL_31(false, 31),
    LEVEL_32(false, 32),
    LEVEL_33(false, 33),
    LEVEL_34(false, 34),
    LEVEL_35(false, 35),
    LEVEL_36(false, 36),
    LEVEL_37(false, 37),
    LEVEL_38(false, 38),
    LEVEL_39(false, 39),
    LEVEL_40(false, 40),
    LEVEL_41(false, 41),
    LEVEL_42(false, 42),
    LEVEL_43(false, 43),
    LEVEL_44(false, 44),
    LEVEL_45(false, 45),
    LEVEL_46(false, 46),
    LEVEL_47(false, 47),
    LEVEL_48(false, 48),
    LEVEL_49(false, 49),
    LEVEL_50(false, 50),
    BONUS_LEVEL_1(false, -1),
    BONUS_LEVEL_2(false, -2),
    BONUS_LEVEL_3(false, -3),
    BONUS_LEVEL_4(false, -4),
    BONUS_LEVEL_5(false, -5),
    BONUS_LEVEL_6(false, -6),
    BONUS_LEVEL_7(false, -7),
    BONUS_LEVEL_8(false, -8),
    BONUS_LEVEL_9(false, -9),
    BONUS_LEVEL_10(false, -10);
    private final boolean menu;
    private final int number;

    private Level(boolean menu, int number) {
        this.menu = menu;
        this.number = number;
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

    public List<UIBoxEntity> buildUI() {
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
                ui.add(newButton(2, 2, "Back", "menuload.main"));
                ui.add(newSlider(3, 2, "Level: %d", "levelload.number", 1, 50));
                break;
            case LEADER_BOARD:
                ui.add(newText(0, 4, "Leader board"));
                ui.add(newButton(2, 2, "Back", "menuload.main"));
                break;
            case GAME_OVER:
                ui.add(newText(0, 4, "Game Over"));
                ui.add(newButton(2, 2, "View leader board", "menuload.loaderboard"));
                ui.add(newButton(3, 2, "Quit", "menuload.main"));
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
}
