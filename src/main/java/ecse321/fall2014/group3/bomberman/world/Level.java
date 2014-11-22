package ecse321.fall2014.group3.bomberman.world;

import java.util.HashSet;
import java.util.Set;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.Interface;
import ecse321.fall2014.group3.bomberman.physics.entity.ui.ButtonEntity;
import ecse321.fall2014.group3.bomberman.physics.entity.ui.TextBoxEntity;
import ecse321.fall2014.group3.bomberman.physics.entity.ui.UIBoxEntity;

/**
 *
 */
public enum Level {
    MAIN_MENU(true, -1),
    LEVEL_SELECT(true, -1),
    LEADER_BOARD(true, -1),
    LEVEL_START(true, -1),
    LEVEL_PAUSE(true, -1),
    LEVEL_END(true, -1),
    GAME_OVER(true, -1),
    LEVEL_1(false, 1),
    LEVEL_2(false, 2),
    LEVEL_3(false, 3),
    LEVEL_BONUS(false, 0);
    // TODO: add more LEVEL_n as needed
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

    public Set<UIBoxEntity> buildUI() {
        final Set<UIBoxEntity> ui = new HashSet<>();
        switch (this) {
            case MAIN_MENU:
                ui.add(newText(0, 4, "Bomberman"));
                ui.add(newButton(2, 2, "Continue"));
                ui.add(newButton(3, 2, "Select level"));
                ui.add(newButton(4, 2, "View leader board"));
                break;
            case LEVEL_SELECT:
                ui.add(newText(0, 4, "Level select"));
                break;
            case LEADER_BOARD:
                ui.add(newText(0, 4, "Leader board"));
                break;
            case LEVEL_START:
                ui.add(newText(0, 4, "Level start"));
                break;
            case LEVEL_PAUSE:
                ui.add(newText(0, 4, "Level paused"));
                ui.add(newButton(2, 2, "Continue"));
                ui.add(newButton(3, 2, "View leader board"));
                ui.add(newButton(4, 2, "Save"));
                ui.add(newButton(5, 2, "Quit"));
                break;
            case LEVEL_END:
                ui.add(newText(0, 4, "Level complete"));
                ui.add(newButton(2, 2, "Next level"));
                ui.add(newButton(3, 2, "View leader board"));
                ui.add(newButton(4, 2, "Save"));
                ui.add(newButton(5, 2, "Quit"));
                break;
            case GAME_OVER:
                ui.add(newText(0, 4, "Game Over"));
                ui.add(newButton(2, 2, "View leader board"));
                ui.add(newButton(3, 2, "Quit"));
                break;
        }
        return ui;
    }

    private static TextBoxEntity newText(int position, int size, String text) {
        return new TextBoxEntity(new Vector2f(3, Interface.VIEW_HEIGHT_TILE - (3 + position)), new Vector2f(size, size), text);
    }

    private static ButtonEntity newButton(int position, int size, String text) {
        return new ButtonEntity(new Vector2f(3, Interface.VIEW_HEIGHT_TILE - (3 + position)), new Vector2f(size, size), text);
    }
}
