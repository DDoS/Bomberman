package ecse321.fall2014.group3.bomberman.event;

import ecse321.fall2014.group3.bomberman.world.tile.ExitWay;
import ecse321.fall2014.group3.bomberman.world.tile.Tile;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.PowerUP;

/**
 *
 */
public class ExitWayOrPowerUPDestroyedEvent extends Event {
    private final boolean exitWay;

    public ExitWayOrPowerUPDestroyedEvent(Tile destroyed) {
        exitWay = destroyed instanceof ExitWay;
        if (!exitWay && !(destroyed instanceof PowerUP)) {
            throw new IllegalArgumentException("Expected either an exit way or power UP tile");
        }
    }

    public boolean isExitWay() {
        return exitWay;
    }

    public boolean isPowerUP() {
        return !isExitWay();
    }
}
