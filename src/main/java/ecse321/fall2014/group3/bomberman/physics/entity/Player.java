/*
 * Stuff to fix:
 * 
 * 1. What type is blastRadius?
 * 2. What is detonator variable?
 * 3. Powerup Class not implemented so powerup list is commented
 * 
 */
package ecse321.fall2014.group3.bomberman.physics.entity;

import com.flowpowered.math.vector.Vector2f;
import com.flowpowered.math.vector.Vector2i;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

public class Player extends Entity {
    private static final SpriteInfo STILL_SPRITE = new SpriteInfo("main", 16, Vector2i.ONE);
    //private ... blastRadius
    private int placementBombCount;
    private double speed = 0;
    //private List<Powerup> powerups;
    private SpriteInfo currentSprite = STILL_SPRITE;

    public Player(Vector2f position) {
        super(position, COLLISION_BOX);
    }

    public int getPlacementBombCount() {
        return placementBombCount;
    }

    public double getSpeed() {
        return speed;
    }

    /*
    public Set<Powerup> getPowerups() {
        // TODO: implement me
        return Collections.EMPTY_SET;
    }
    */
    public boolean isWallPass() {
        // TODO: implement me
        return false;
    }

    public boolean isBombPass() {
        // TODO: implement me
        return false;
    }

    public boolean isFlamePass() {
        // TODO: implement me
        return false;
    }

    public boolean isInvisibility() {
        // TODO: implement me
        return false;
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return currentSprite;
    }
}
