package ecse321.fall2014.group3.bomberman.world;

import com.flowpowered.commons.ticking.TickingElement;

import ecse321.fall2014.group3.bomberman.Game;

/**
 *
 */
public class World extends TickingElement {
    private final Game game;

    public World(Game game) {
        super("World", 20);
        this.game = game;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onTick(long l) {

    }

    @Override
    public void onStop() {

    }
}
