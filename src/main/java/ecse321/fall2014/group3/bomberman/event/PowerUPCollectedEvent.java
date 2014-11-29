package ecse321.fall2014.group3.bomberman.event;

import ecse321.fall2014.group3.bomberman.world.tile.powerup.PowerUP;

/**
 *
 */
public class PowerUPCollectedEvent extends Event {
    private final PowerUP powerUP;

    public PowerUPCollectedEvent(PowerUP powerUP) {
        this.powerUP = powerUP;
    }

    public PowerUP getPowerUP() {
        return powerUP;
    }
}
