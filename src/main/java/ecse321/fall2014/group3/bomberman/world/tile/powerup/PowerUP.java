package ecse321.fall2014.group3.bomberman.world.tile.powerup;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.world.tile.CollidableTile;

public abstract class PowerUP extends CollidableTile {
    private final boolean canUpgrade;

    protected PowerUP(Vector2f position, boolean canUpgrade) {
        super(position);
        this.canUpgrade = canUpgrade;
    }

    public boolean canBeUpgraded() {
        return canUpgrade;
    }
}
