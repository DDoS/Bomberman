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

public class Player extends Entity {
    //protected ... blastRadius
    protected int placementBombCount;
    protected double speed = 0;
    //protected List<Powerup> powerups;

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
}
