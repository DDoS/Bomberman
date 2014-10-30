package ecse321.fall2014.group3.bomberman.input;

import com.flowpowered.commons.ticking.TickingElement;

import ecse321.fall2014.group3.bomberman.Game;

/**
 *
 */
public class Input extends TickingElement {
    private final Game game;

    public Input(Game game) {
        super("Input", 60);
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
