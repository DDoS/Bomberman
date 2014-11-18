package ecse321.fall2014.group3.bomberman.physics.entity.mob;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.Direction;
import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;
import ecse321.fall2014.group3.bomberman.physics.CollisionBox;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.PowerUP;

public class Player extends Mob {
    private static final Vector2f SIZE = Vector2f.ONE.mul(0.75f);
    private static final CollisionBox COLLISION_BOX = new CollisionBox(Vector2f.ONE);
    private static final SpriteInfo STILL_SPRITE = new SpriteInfo("entity", 0, Vector2f.ONE);
    // This value is used to change the amount of travel the player needs to do in a direction
    // before he actually rotates to that direction. Higher value means more travel
    private static final float DIRECTION_CHANGE_THRESHOLD = 2 / 3f;
    // A dampened velocity that never quite gets to the maximum speed of the player (asymptotic)
    private volatile Vector2f directionVelocity = Vector2f.ZERO;
    private volatile int blastRadius = 1;
    private volatile int placementBombCount = 1;
    private volatile float speed = 3;
    private final Map<Class<? extends PowerUP>, Integer> powerUPs = new ConcurrentHashMap<>();
    private volatile SpriteInfo currentSprite = STILL_SPRITE;

    public Player(Vector2f position) {
        super(position, COLLISION_BOX);
    }

    @Override
    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
        // Only update the direction if we've been moving in that direction for a little while
        // This is a special case for player to improve rotation animations when going around corners
        directionVelocity = this.directionVelocity.add(velocity).div(2);
        if (directionVelocity.lengthSquared() / (speed * speed) >= DIRECTION_CHANGE_THRESHOLD) {
            direction = Direction.fromUnit(directionVelocity);
        }
    }

    public float getSpeed() {
        return speed;
    }

    public int getBlastRadius() {
        return blastRadius;
    }

    public int getPlacementBombCount() {
        return placementBombCount;
    }

    public Collection<Class<? extends PowerUP>> getPowerUPs() {
        return powerUPs.keySet();
    }

    public boolean hasPowerUP(Class<? extends PowerUP> powerUP) {
        return powerUPs.containsKey(powerUP);
    }

    public int getPowerUPLevel(Class<? extends PowerUP> powerUP) {
        return powerUPs.get(powerUP);
    }

    public void addPowerUP(PowerUP powerUP) {
        Integer level = powerUPs.get(powerUP.getClass());
        if (level == null || !powerUP.canBeUpgraded()) {
            level = 1;
        } else {
            level++;
        }
        powerUPs.put(powerUP.getClass(), level);
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return currentSprite;
    }

    @Override
    public Vector2f getModelSize() {
        return SIZE;
    }
}
