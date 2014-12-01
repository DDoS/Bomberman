/**
 * @author Phil Douyon
 */
package ecse321.fall2014.group3.bomberman.event;

import ecse321.fall2014.group3.bomberman.world.tile.ExitWay;
import ecse321.fall2014.group3.bomberman.world.tile.Tile;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.PowerUP;

/**
 * The Class ExitWayOrPowerUPDestroyedEvent.
 */
public class ExitWayOrPowerUPDestroyedEvent extends Event {
    private final boolean exitWay;

    /**
     * Instantiates a new exitWay or powerUP destroyed event.
     *
     * @param destroyed the destroyed tile
     */
    public ExitWayOrPowerUPDestroyedEvent(Tile destroyed) {
        exitWay = destroyed instanceof ExitWay;
        if (!exitWay && !(destroyed instanceof PowerUP)) {
            throw new IllegalArgumentException("Expected either an exit way or power UP tile");
        }
    }

    /**
     * Checks if is exitWay tile.
     *
     * @return true, if is exitWay tile
     */
    public boolean isExitWay() {
        return exitWay;
    }

    /**
     * Checks if is powerUP tile.
     *
     * @return true, if is powerUp tile
     */
    public boolean isPowerUP() {
        return !isExitWay();
    }
}
