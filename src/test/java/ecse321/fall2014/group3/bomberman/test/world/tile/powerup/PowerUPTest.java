package ecse321.fall2014.group3.bomberman.test.world.tile.powerup;

import java.util.HashMap;
import java.util.Map;

import ecse321.fall2014.group3.bomberman.world.tile.powerup.BombPass;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.BombUpgrade;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.FlamePass;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.FlameUpgrade;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.PowerUP;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.WallPass;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class PowerUPTest {
    @Test
    public void testSerialize() {
        Map<Class<? extends PowerUP>, Integer> powerUPs = new HashMap<>();
        powerUPs.put(BombPass.class, 1);
        powerUPs.put(BombUpgrade.class, 4);
        powerUPs.put(FlamePass.class, 1);
        powerUPs.put(FlameUpgrade.class, 2);
        powerUPs.put(WallPass.class, 1);
        int serialize = PowerUP.serialize(powerUPs);
        Map<Class<? extends PowerUP>, Integer> recovered = new HashMap<>();
        PowerUP.deserialize(serialize, recovered);
        Assert.assertEquals(powerUPs, recovered);
    }
}
