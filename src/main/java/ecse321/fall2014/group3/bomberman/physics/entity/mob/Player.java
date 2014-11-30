package ecse321.fall2014.group3.bomberman.physics.entity.mob;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.Direction;
import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;
import ecse321.fall2014.group3.bomberman.physics.CollisionBox;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.BombUpgrade;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.FlameUpgrade;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.PowerUP;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.SpeedUpgrade;

public class Player extends Mob {
    private static final Vector2f SIZE = Vector2f.ONE;
    private static final CollisionBox COLLISION_BOX = new CollisionBox(SIZE);
    private static final SpriteInfo DOWN_SPRITE = new SpriteInfo("Sprite", 0, Vector2f.ONE);
    private static final SpriteInfo UP_SPRITE = new SpriteInfo("Sprite", 1, Vector2f.ONE);
    private static final SpriteInfo LEFT_SPRITE = new SpriteInfo("Sprite", 2, Vector2f.ONE);
    private static final SpriteInfo RIGHT_SPRITE = new SpriteInfo("Sprite", 3, Vector2f.ONE);
    // This value is used to change the amount of travel the player needs to do in a direction
    // before he actually rotates to that direction. Higher value means more travel
    private static final float DIRECTION_CHANGE_THRESHOLD = 2 / 3f;
    // The base speed of the player, which is also the bonus for each speed upgrade
    private static final float BASE_SPEED = 2;
    // A dampened velocity that never quite gets to the maximum speed of the player (asymptotic)
    private volatile Vector2f directionVelocity = Vector2f.ZERO;
    private final Map<Class<? extends PowerUP>, Integer> powerUPs = new ConcurrentHashMap<>();

    public Player(Vector2f position) {
        super(position, COLLISION_BOX);
    }

    @Override
    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
        // Only update the direction if we've been moving in that direction for a little while
        // This is a special case for player to improve rotation animations when going around corners
        directionVelocity = this.directionVelocity.add(velocity).div(2);
        final float speed = getSpeed();
        if (directionVelocity.lengthSquared() / (speed * speed) >= DIRECTION_CHANGE_THRESHOLD) {
            direction = Direction.fromUnit(directionVelocity);
        }
    }

    public float getSpeed() {
        return (1 + getPowerUPLevel(SpeedUpgrade.class)) * BASE_SPEED;
    }

    public int getBlastRadius() {
        return 1 + getPowerUPLevel(FlameUpgrade.class);
    }

    public int getBombPlacementCount() {
        return 1 + getPowerUPLevel(BombUpgrade.class);
    }

    public Collection<Class<? extends PowerUP>> getPowerUPs() {
        return powerUPs.keySet();
    }

    public boolean hasPowerUP(Class<? extends PowerUP> powerUP) {
        return powerUPs.containsKey(powerUP);
    }

    public int getPowerUPLevel(Class<? extends PowerUP> powerUP) {
        final Integer level = powerUPs.get(powerUP);
        if (level == null) {
            return 0;
        }
        return level;
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

    public void clearPowerUPs() {
        powerUPs.clear();
    }
    
    

    @Override
    public SpriteInfo getSpriteInfo() {
        switch (direction) {
            case UP:
                return UP_SPRITE;
            case DOWN:
               return DOWN_SPRITE;
            case LEFT:
               return LEFT_SPRITE;
            case RIGHT:
                return RIGHT_SPRITE;
            default:
                return DOWN_SPRITE;
        }
    }

    @Override
    public Vector2f getModelSize() {
        return SIZE;
    }
}
