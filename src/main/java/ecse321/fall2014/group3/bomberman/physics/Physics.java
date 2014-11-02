package ecse321.fall2014.group3.bomberman.physics;

import com.flowpowered.commons.ticking.TickingElement;

import ecse321.fall2014.group3.bomberman.Game;

/**
 *
 */
public class Physics extends TickingElement {
    private final Game game;

    public Physics(Game game) {
        super("Physics", 60);
        this.game = game;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onTick(long dt) {

    }

    @Override
    public void onStop() {

    }
}
