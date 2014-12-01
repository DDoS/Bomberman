/**
 * @author Phil Douyon
 */
package ecse321.fall2014.group3.bomberman.event;

import ecse321.fall2014.group3.bomberman.world.tile.powerup.PowerUP;

/**
 * The Class PowerUPCollectedEvent.
 */
public class PowerUPCollectedEvent extends Event {
    private final PowerUP powerUP;

    /**
     * Instantiates a new powerUP collected event.
     *
     * @param powerUP the power up
     */
    public PowerUPCollectedEvent(PowerUP powerUP) {
        this.powerUP = powerUP;
    }

    /**
     * Gets the power up.
     *
     * @return the power up
     */
    public PowerUP getPowerUP() {
        return powerUP;
    }
}
