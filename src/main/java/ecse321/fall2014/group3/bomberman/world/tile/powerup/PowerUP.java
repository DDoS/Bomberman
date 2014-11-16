package ecse321.fall2014.group3.bomberman.world.tile.powerup;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.world.tile.CollidableTile;

public abstract class PowerUP extends CollidableTile {
    public PowerUP(Vector2f position) {
        super(position);
    }
}
